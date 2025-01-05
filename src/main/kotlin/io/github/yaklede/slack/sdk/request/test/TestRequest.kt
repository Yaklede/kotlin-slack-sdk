package io.github.yaklede.slack.sdk.request.test

import io.github.yaklede.slack.sdk.client.http.enums.HttpMethod
import io.github.yaklede.slack.sdk.client.http.enums.MediaType
import io.github.yaklede.slack.sdk.request.SlackRequest

data class ApiTestRequest(
    val error: String,
    val foo: String,
): SlackRequest {
    override val contentType: MediaType = MediaType.APPLICATION_X_WWW_FORM_URLENCODED
    override val method: HttpMethod = HttpMethod.POST
    override val endpoint: String = "api.test"
}