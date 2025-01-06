package io.github.yaklede.slack.sdk.client.http

import io.github.yaklede.slack.sdk.client.exception.SlackApiException
import io.github.yaklede.slack.sdk.client.http.exception.HttpException
import io.github.yaklede.slack.sdk.client.http.exception.ParseException
import io.github.yaklede.slack.sdk.request.SlackRequest
import io.github.yaklede.slack.sdk.response.SlackResponse
import kotlin.reflect.KClass

class SlackTemplate {
    private val httpTemplate = HttpTemplate()
    fun <T: SlackRequest, R: SlackResponse> execute(
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
}