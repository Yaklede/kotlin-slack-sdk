package io.github.yaklede.slack.sdk.client

import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.Key
import com.natpryce.konfig.stringType
import io.github.yaklede.slack.sdk.request.apps.*
import io.github.yaklede.slack.sdk.request.test.ApiTestRequest
import io.github.yaklede.slack.sdk.request.test.apiTestApiRequest
import io.github.yaklede.slack.sdk.response.apps.AppsAuthExternalDeleteResponse
import io.github.yaklede.slack.sdk.response.apps.AppsAuthExternalGetResponse
import io.github.yaklede.slack.sdk.response.test.ApiTestResponse
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.util.UUID

class SlackClientTest {
    private val config = ConfigurationProperties.fromResource("local.properties")

    private val APP_ID = config[Key("SLACK_APP_ID", stringType)]
    private val CLIENT_ID = config[Key("SLACK_CLIENT_ID", stringType)]
    private val CLIENT_SECRET = config[Key("SLACK_CLIENT_SECRET", stringType)]
    private val SIGNING_SECRET = config[Key("SLACK_SIGNING_SECRET", stringType)]
    private val VERIFICATION_TOKEN = config[Key("SLACK_VERIFICATION_TOKEN", stringType)]
    private val BOT_TOKEN = config[Key("SLACK_BOT_API_TOKEN", stringType)]
    private val USER_TOKEN = config[Key("SLACK_USER_API_TOKEN", stringType)]
    private val CHANNEL = config[Key("SLACK_API_CHANNEL", stringType)]

    private val client = SlackClient(
        botToken = BOT_TOKEN,
        userToken = USER_TOKEN,
        channel = CHANNEL
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

    @Test
    @Disabled
    fun appsActivitiesListTest() {
        //given
        val request = AppsActivitiesListRequest(
            appId = APP_ID
        )

        //when
        val data = client.appsActivitiesList(request)

        //then
        Assertions.assertThat(data).isInstanceOf(ApiTestResponse::class.java)
    }

    @Test
    fun appsAuthExternalDeleteTest() {
        //given
        val request = AppsAuthExternalDelete(
            appId = APP_ID,
            externalTokenId = UUID.randomUUID().toString()
        )
        //when
        val data = client.appsAuthExternalDelete(request)

        //then
        Assertions.assertThat(data).isInstanceOf(AppsAuthExternalDeleteResponse::class.java)
    }

    @Test
    fun appsAuthExternalDeleteWithBlockTest() {
        //given
        val request = appsAuthExternalDelete {
            appId = APP_ID
            externalTokenId = UUID.randomUUID().toString()
        }
        //when
        val data = client.appsAuthExternalDelete(request)

        //then
        Assertions.assertThat(data).isInstanceOf(AppsAuthExternalDeleteResponse::class.java)
    }

    @Test
    fun appsAuthExternalGetTest() {
        //given
        val request = AppsAuthExternalGet(
            appId = APP_ID,
            externalTokenId = USER_TOKEN,
            forceRefresh = false
        )

        //when
        val data = client.appsAuthExternalGet(request)

        //then
        Assertions.assertThat(data).isInstanceOf(AppsAuthExternalGetResponse::class.java)
    }

    @Test
    fun appsAuthExternalGetWithBlockTest() {
        //given
        val request = appsAuthExternalGet {
            appId = APP_ID
            externalTokenId = USER_TOKEN
            forceRefresh = false
        }
        //when
        val data = client.appsAuthExternalGet(request)

        //then
        Assertions.assertThat(data).isInstanceOf(AppsAuthExternalGetResponse::class.java)
    }
}