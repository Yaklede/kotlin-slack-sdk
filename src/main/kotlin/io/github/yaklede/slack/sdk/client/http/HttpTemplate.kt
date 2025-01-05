package io.github.yaklede.slack.sdk.client.http

import com.fasterxml.jackson.core.type.TypeReference
import io.github.yaklede.slack.sdk.client.http.annotations.FormFormat
import io.github.yaklede.slack.sdk.client.http.annotations.FormProperty
import io.github.yaklede.slack.sdk.client.http.enums.HttpMethod
import io.github.yaklede.slack.sdk.client.http.enums.MediaType
import io.github.yaklede.slack.sdk.client.http.exception.HttpClientException
import io.github.yaklede.slack.sdk.client.http.exception.ParseException
import io.github.yaklede.slack.sdk.config.mapper
import io.github.yaklede.slack.sdk.request.SlackRequest
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

class HttpTemplate {

    fun <T : Any> execute(
        url: String,
        method: HttpMethod,
        entity: HttpEntity<*>,
        responseType: KClass<T>
    ): T {
        val client = HttpClient.newHttpClient()
        val request = createRequest(url, method, entity)
        val body = doExecute(client, request)
        return extractResponse(body, responseType)
    }

    fun <T : Any> execute(
        url: String,
        method: HttpMethod,
        entity: HttpEntity<*>,
        responseType: TypeReference<T>
    ): T {
        val client = HttpClient.newHttpClient()
        val request = createRequest(url, method, entity)
        val body = doExecute(client, request)
        return extractResponse(body, responseType)
    }

    private fun doExecute(client: HttpClient, request: HttpRequest.Builder): String {
        val response = client.send(request.build(), HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() in 400..499) {
            throw HttpClientException("Http 4xx error", response.body())
        }

        if (response.statusCode() in 500..599) {
            throw HttpClientException("Http 5xx error", response.body())
        }
        return response.body()
    }

    private fun createRequest(url: String, method: HttpMethod, entity: HttpEntity<*>): HttpRequest.Builder {
        if (entity.body != null && entity.body !is SlackRequest) {
            throw IllegalArgumentException("Entity body must be a SlackRequest")
        }

        return when (entity.getContentType()) {
            MediaType.APPLICATION_JSON.value -> {
                HttpRequest.newBuilder(URI.create(url))
                    .method(
                        method.name,
                        extractRequest(entity.body)
                    ).apply {
                        entity.getHeaders().entries.forEach { (key, value) ->
                            this.setHeader(key, value)
                        }
                    }
            }

            MediaType.APPLICATION_X_WWW_FORM_URLENCODED.value -> {
                val formBody = entity.body?.let { body ->
                    body::class.memberProperties
                        .filterNot { prop -> SlackRequest::class.memberProperties.any { it.name == prop.name } }
                        .joinToString("&") { prop ->
                            val formProperty = prop.findAnnotation<FormProperty>()

                            val key = formProperty?.name ?: prop.name
                            val value = prop.getter.call(body)?.toString() ?: ""

                            val formFormat = prop.findAnnotation<FormFormat>()
                            formFormat?.let {
                                val pattern = it.pattern
                                val formattedValue = when (val propValue = prop.getter.call(body)) {
                                    is LocalDate -> propValue.format(
                                        java.time.format.DateTimeFormatter.ofPattern(
                                            pattern
                                        )
                                    )

                                    is LocalDateTime -> propValue.format(
                                        java.time.format.DateTimeFormatter.ofPattern(
                                            pattern
                                        )
                                    )

                                    is LocalTime -> propValue.format(
                                        java.time.format.DateTimeFormatter.ofPattern(
                                            pattern
                                        )
                                    )

                                    else -> throw IllegalArgumentException("Unsupported format type for @FormFormat on ${prop.name}")
                                }
                                "$key=$formattedValue"
                            } ?: "$key=$value"
                        }
                } ?: ""

                HttpRequest.newBuilder(URI.create(url))
                    .method(
                        method.name,
                        HttpRequest.BodyPublishers.ofString(formBody)
                    ).apply {
                        entity.getHeaders().entries.forEach { (key, value) ->
                            this.setHeader(key, value)
                        }
                    }
            }

            MediaType.MULTIPART_FORM_DATA.value -> {
                val boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW"
                val formDataBody = entity.body?.let { body ->
                    body::class.memberProperties.joinToString("\r\n") { prop ->
                        "--$boundary\r\n" +
                                "Content-Disposition: form-data; name=\"${prop.name}\"\r\n\r\n" +
                                "${prop.getter.call(body)}"
                    } + "\r\n--$boundary--\r\n"
                } ?: ""

                HttpRequest.newBuilder(URI.create(url))
                    .method(
                        method.name,
                        HttpRequest.BodyPublishers.ofString(formDataBody)
                    ).apply {
                        entity.getHeaders().entries.forEach { (key, value) ->
                            this.setHeader(key, value)
                        }
                    }
            }

            else -> throw IllegalArgumentException("Unsupported media type: ${entity.getContentType()}")
        }
    }

    private fun <T : Any> extractRequest(body: T?): HttpRequest.BodyPublisher {
        return body?.let {
            HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(it))
        } ?: HttpRequest.BodyPublishers.noBody()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <R : Any> extractResponse(body: String, responseType: KClass<R>): R {
        return when (responseType) {
            String::class -> body as R
            else -> runCatching {
                mapper.readValue(body, responseType.java)
            }.getOrElse {
                throw ParseException(body = body, clazz = responseType)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <R : Any> extractResponse(body: String, responseType: TypeReference<R>): R {
        return when (responseType) {
            String::class -> body as R
            else -> runCatching {
                mapper.readValue(body, responseType)
            }.getOrElse {
                throw ParseException(body = body, clazz = responseType::class)
            }
        }
    }
}