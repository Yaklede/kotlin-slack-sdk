package io.github.yaklede.slack.sdk.request

import com.fasterxml.jackson.annotation.JsonIgnore
import io.github.yaklede.slack.sdk.client.http.enums.HttpMethod
import io.github.yaklede.slack.sdk.client.http.enums.MediaType

sealed interface SlackRequest {
    @get:JsonIgnore
    val endpoint: String
    @get:JsonIgnore
    val method: HttpMethod
    @get:JsonIgnore
    val contentType: MediaType
}

interface BotTokenSlackRequest: SlackRequest
interface UserTokenSlackReqeust: SlackRequest