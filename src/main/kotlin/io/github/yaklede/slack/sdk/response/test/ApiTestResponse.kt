package io.github.yaklede.slack.sdk.response.test

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.github.yaklede.slack.sdk.response.SlackResponse

@JsonIgnoreProperties(ignoreUnknown = true)
data class ApiTestResponse(
    val ok: Boolean,
    val args: Map<String, String>,
    val error: String? = null,
): SlackResponse {
    override fun isSuccess(): Boolean {
        return ok && error == null
    }
}