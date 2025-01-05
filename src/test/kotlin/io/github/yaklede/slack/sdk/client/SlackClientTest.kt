package io.github.yaklede.slack.sdk.client

import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.Key
import com.natpryce.konfig.stringType
import io.github.yaklede.slack.sdk.request.test.ApiTestRequest
import io.github.yaklede.slack.sdk.request.test.apiTestApiRequest
import io.github.yaklede.slack.sdk.response.test.ApiTestResponse
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class SlackClientTest {
    private val config = ConfigurationProperties.fromResource("local.properties")

    private val client = SlackClient(
        token = config[Key("SLACK_API_TOKEN", stringType)],
        channel = config[Key("SLACK_API_CHANNEL", stringType)]
    )

    @Test
    fun apiTest() {
        //given
        val request = ApiTestRequest(
            foo = "bar",
            error = "error",
        )

        //when
        val data = client.apiTest(
            request = request
        )

        //then
        Assertions.assertThat(data).isInstanceOf(ApiTestResponse::class.java)
    }

    @Test
    fun apiTestWithBlock() {
        //given
        val request = apiTestApiRequest {
            foo = "bar"
            error = "error"
        }

        //when
        val data = client.apiTest(request)

        //then
        Assertions.assertThat(data).isInstanceOf(ApiTestResponse::class.java)
    }
}