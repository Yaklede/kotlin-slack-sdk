package io.github.yaklede.slack.sdk.client

import io.github.yaklede.slack.sdk.client.exception.SlackApiException
import io.github.yaklede.slack.sdk.client.http.HttpEntity
import io.github.yaklede.slack.sdk.client.http.HttpHeaders
import io.github.yaklede.slack.sdk.client.http.HttpTemplate
import io.github.yaklede.slack.sdk.client.http.exception.HttpException
import io.github.yaklede.slack.sdk.client.http.exception.ParseException
import io.github.yaklede.slack.sdk.request.SlackRequest
import io.github.yaklede.slack.sdk.request.test.ApiTestRequest
import io.github.yaklede.slack.sdk.response.SlackResponse
import io.github.yaklede.slack.sdk.response.test.ApiTestResponse
import kotlin.reflect.KClass

class SlackClient(
    private val token: String,
    private val channel: String,
) {
    private val httpTemplate = HttpTemplate()

    fun apiTest(request: ApiTestRequest): ApiTestResponse? {
        val url = "$API/${request.endpoint}"
        val headers = createHttpHeaders(request)
        val entity = HttpEntity(request, headers)
        return doExecute(url, request, entity, ApiTestResponse::class)
    }

    private fun <T: SlackRequest, R: SlackResponse> doExecute(
        url: String,
        request: T,
        entity: HttpEntity<T>,
        responseType: KClass<R>
    ): R? {
        return try {
            httpTemplate.execute(
                url = url,
                method = request.method,
                entity = entity,
                responseType = responseType
            )
        } catch (e: HttpException) {
            throw SlackApiException(
                body = e.responseAsString ?: e.message
            )
        } catch (e: ParseException) {
            throw SlackApiException(
                body = e.body
            )
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("unknown slack client exception")
        }
    }


    private fun createHttpHeaders(request: SlackRequest): HttpHeaders {
        return HttpHeaders().apply {
            addBearerToken(token)
            setContentType(request.contentType)
        }
    }

    companion object {
        const val API = "https://slack.com/api"
    }
}