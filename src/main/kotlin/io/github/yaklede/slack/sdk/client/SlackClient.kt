package io.github.yaklede.slack.sdk.client

import io.github.yaklede.slack.sdk.client.http.SlackTemplate
import io.github.yaklede.slack.sdk.request.test.ApiTestRequest
import io.github.yaklede.slack.sdk.response.test.ApiTestResponse

class SlackClient(
    botToken: String,
    userToken: String,
    private val channel: String,
) {
    private val slackTemplate = SlackTemplate(
        botToken = botToken,
        userToken = userToken,
    )

    fun apiTest(request: ApiTestRequest): ApiTestResponse? {
        return slackTemplate.execute(request, ApiTestResponse::class)
    }

    }
}