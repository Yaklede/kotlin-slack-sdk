package io.github.yaklede.slack.sdk.client

import io.github.yaklede.slack.sdk.client.http.HttpEntity
import io.github.yaklede.slack.sdk.client.http.HttpHeaders
import io.github.yaklede.slack.sdk.client.http.SlackTemplate
import io.github.yaklede.slack.sdk.request.SlackRequest
import io.github.yaklede.slack.sdk.request.test.ApiTestRequest
import io.github.yaklede.slack.sdk.response.test.ApiTestResponse

class SlackClient(
    private val token: String,
    private val channel: String,
) {
    private val slackTemplate = SlackTemplate()

    fun apiTest(request: ApiTestRequest): ApiTestResponse? {
        val url = "$API/${request.endpoint}"
        val headers = createHttpHeaders(request)
        val entity = HttpEntity(request, headers)
        return slackTemplate.execute(url, request, entity, ApiTestResponse::class)
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