package io.github.yaklede.slack.sdk.client.http

import io.github.yaklede.slack.sdk.client.exception.SlackApiException
import io.github.yaklede.slack.sdk.client.http.exception.HttpException
import io.github.yaklede.slack.sdk.client.http.exception.ParseException
import io.github.yaklede.slack.sdk.request.BotTokenSlackRequest
import io.github.yaklede.slack.sdk.request.SlackRequest
import io.github.yaklede.slack.sdk.request.UserTokenSlackReqeust
import io.github.yaklede.slack.sdk.request.apps.AppsActivitiesListRequest
import io.github.yaklede.slack.sdk.response.SlackResponse
import kotlin.reflect.KClass

class SlackTemplate(
    private val botToken: String,
    private val userToken: String,
) {
    private val httpTemplate = HttpTemplate()

    fun <T : SlackRequest, R : SlackResponse> execute(request: T, responseType: KClass<R>): R? {
        val url = "$API/${request.endpoint}"
        val headers = createHttpHeaders(request)
        val entity = HttpEntity(request, headers)
        return doExecute(url, request, entity, responseType)
    }

    private fun createHttpHeaders(request: SlackRequest): HttpHeaders {
        return HttpHeaders().apply {
            val token = when(request) {
                is BotTokenSlackRequest -> botToken
                is UserTokenSlackReqeust -> userToken
            }
            addBearerToken(token)
            setContentType(request.contentType)
        }
    }

    private fun <T : SlackRequest, R : SlackResponse> doExecute(
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

    companion object {
        const val API = "https://slack.com/api"
    }
}