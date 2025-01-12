package io.github.yaklede.slack.sdk.request.apps

import io.github.yaklede.slack.sdk.client.http.enums.HttpMethod
import io.github.yaklede.slack.sdk.client.http.enums.MediaType
import io.github.yaklede.slack.sdk.request.SlackRequest
import io.github.yaklede.slack.sdk.request.UserTokenSlackReqeust

/**
 * @property appId 활동 로그 가져올 앱의 id
 * @property componentId 선택: 로그 이벤트의 컴포넌트 ID
 * @property componentType 선택: 로그 이벤트의 컴포넌트 유형
 * @property cursor 선택: 페이지네이션을 위한 커서
 * @property limit 선택: 반환할 항목의 최대 수 (1-1000)
 * @property logEventType 선택: 반환할 로그 이벤트의 유형
 * @property maxDateCreated 선택: 검색할 로그의 최신 타임스탬프 (마이크로초 단위)
 * @property minDateCreated 선택: 검색할 로그의 최소 타임스탬프 (마이크로초 단위)
 * @property minLogLevel 선택: 반환할 로그 이벤트의 최소 로그 레벨
 * @property sortDirection 선택: 데이터 정렬 방향 ('asc' 또는 'desc')
 * @property source 선택: 로그 이벤트의 소스 ('slack' 또는 'developer')
 * @property teamId 선택: 로그를 소유한 팀의 ID
 * @property traceId 선택: 로그 이벤트의 추적 ID
 */
@Deprecated("only workflows apps")
data class AppsActivitiesListRequest(
    val appId: String,
    val componentId: String? = null,
    val componentType: String? = null,
    val cursor: String? = null,
    val limit: Int? = null,
    val logEventType: String? = null,
    val maxDateCreated: Long? = null,
    val minDateCreated: Long? = null,
    val minLogLevel: String? = null,
    val sortDirection: String? = null,
    val source: String? = null,
    val teamId: String? = null,
    val traceId: String? = null
): UserTokenSlackReqeust {
    override val contentType: MediaType = MediaType.APPLICATION_X_WWW_FORM_URLENCODED
    override val endpoint: String = "apps.activities.list"
    override val method: HttpMethod = HttpMethod.GET
}

/**
 * @property appId 앱의 id
 * @property externalTokenId 외부 토큰의 id
 * @property providerKey 외부 토큰의 제공자 키
 * @see <a href="https://api.slack.com/methods/apps.auth.external.delete">apps.auth.external.delete</a>
 */
data class AppsAuthExternalDelete(
    val appId: String? = null,
    val externalTokenId: String? = null,
    val providerKey: String? = null
): BotTokenSlackRequest {
    override val contentType: MediaType = MediaType.APPLICATION_X_WWW_FORM_URLENCODED
    override val endpoint: String = "apps.auth.external.delete"
    override val method: HttpMethod = HttpMethod.POST
}

class AppsAuthExternalDeleteBuilder {
    var appId: String? = null
    var externalTokenId: String? = null
    var providerKey: String? = null

    fun build(): AppsAuthExternalDelete {
        return AppsAuthExternalDelete(appId, externalTokenId, providerKey)
    }
}

fun appsAuthExternalDelete(builder: AppsAuthExternalDeleteBuilder.() -> Unit): AppsAuthExternalDelete {
    return AppsAuthExternalDeleteBuilder().apply(builder).build()
}
