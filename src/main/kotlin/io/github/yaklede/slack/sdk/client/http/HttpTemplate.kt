package io.github.yaklede.slack.sdk.client.http

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.github.yaklede.slack.sdk.client.http.enums.HttpMethod
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.reflect.KClass

class HttpTemplate {
    private val mapper = ObjectMapper().apply {
        registerKotlinModule()
    }

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
        return response.body()
    }

    private fun createRequest(url: String, method: HttpMethod, entity: HttpEntity<*>): HttpRequest.Builder {
        return HttpRequest.newBuilder(URI.create(url))
            .method(
                method.name,
                extractRequest(entity.body)
            ).apply {
                entity.getHeaders().entries.forEach { (key, value) ->
                    this.setHeader(key, value)
                }
            }
    }

    private fun <T : Any> extractRequest(body: T?): HttpRequest.BodyPublisher {
        return body?.let {
            HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(it))
        } ?: HttpRequest.BodyPublishers.noBody()
    }

    @Suppress("UNCHECKED_CAST")
    private fun<R: Any> extractResponse(body: String, responseType: KClass<R>): R {
        return when (responseType) {
            String::class -> body as R
            else -> mapper.readValue(body, responseType.java)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun<R: Any> extractResponse(body: String, responseType: TypeReference<R>): R {
        return when (responseType) {
            String::class -> body as R
            else -> mapper.readValue(body, responseType)
        }
    }
}