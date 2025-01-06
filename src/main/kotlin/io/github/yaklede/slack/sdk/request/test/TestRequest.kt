package io.github.yaklede.slack.sdk.request.test

import io.github.yaklede.slack.sdk.client.http.enums.HttpMethod
import io.github.yaklede.slack.sdk.client.http.enums.MediaType
import io.github.yaklede.slack.sdk.request.BotTokenSlackRequest

data class ApiTestRequest(
    val error: String,
    val foo: String,
): BotTokenSlackRequest {
    override val contentType: MediaType = MediaType.APPLICATION_X_WWW_FORM_URLENCODED
    override val method: HttpMethod = HttpMethod.POST
    override val endpoint: String = "api.test"
}

class ApiTestRequestBuilder {
    lateinit var error: String
    lateinit var foo: String

    fun build(): ApiTestRequest {
        return ApiTestRequest(
            error = error,
            foo = foo,
        )
    }
}

fun apiTestApiRequest(apiTestRequestBuilderBlock: ApiTestRequestBuilder.() -> Unit): ApiTestRequest {
    return ApiTestRequestBuilder().apply(apiTestRequestBuilderBlock).build()
}