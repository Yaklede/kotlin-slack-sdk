package io.github.yaklede.slack.sdk.client

import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.Key
import com.natpryce.konfig.stringType
import io.github.yaklede.slack.sdk.request.test.ApiTestRequest
import org.junit.jupiter.api.Test

class SlackClientTest {
    private val config = ConfigurationProperties.fromResource("local.properties")

    private val client = SlackClient(
        token = config[Key("SLACK_API_TOKEN", stringType)],
        channel = config[Key("SLACK_API_CHANNEL", stringType)]
    )

    @Test
    fun apiTest() {
        val data = client.apiTest(
            request = ApiTestRequest(
                foo = "bar",
                error = "error",
            )
        )
        println(data)
    }
}