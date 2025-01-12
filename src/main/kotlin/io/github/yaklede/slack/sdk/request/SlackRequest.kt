package io.github.yaklede.slack.sdk.request

import com.fasterxml.jackson.annotation.JsonIgnore
import io.github.yaklede.slack.sdk.client.http.enums.HttpMethod
import io.github.yaklede.slack.sdk.client.http.enums.MediaType
import io.github.yaklede.slack.sdk.request.enums.NamingType

sealed interface SlackRequest {
    @get:JsonIgnore
    val endpoint: String
    @get:JsonIgnore
    val method: HttpMethod
    @get:JsonIgnore
    val contentType: MediaType
    @get:JsonIgnore
    val naming: NamingType
        get() = NamingType.SNAKE_CASE
}

interface BotTokenSlackRequest: SlackRequest
interface UserTokenSlackReqeust: SlackRequest