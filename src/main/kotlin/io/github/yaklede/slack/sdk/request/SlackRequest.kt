package io.github.yaklede.slack.sdk.request

import com.fasterxml.jackson.annotation.JsonIgnore
import io.github.yaklede.slack.sdk.client.http.enums.HttpMethod
import io.github.yaklede.slack.sdk.client.http.enums.MediaType

interface SlackRequest {
    @get:JsonIgnore
    val endpoint: String
    @get:JsonIgnore
    val method: HttpMethod
    @get:JsonIgnore
    val contentType: MediaType
}