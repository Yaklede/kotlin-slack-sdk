package io.github.yaklede.slack.sdk.client.http.exception

import io.github.yaklede.slack.sdk.config.mapper
import kotlin.reflect.KClass

// 공통 상위 예외 클래스
open class HttpException(override val message: String, val responseAsString: String?) : RuntimeException(message) {
    fun <T: Any> parseResponse(clazz: KClass<T>): T? {
        return runCatching {
            mapper.readValue(responseAsString, clazz.java)
        }.getOrNull()
    }
}

// 클라이언트 에러 예외 클래스
class HttpClientException(message: String, responseAsString: String) : HttpException(message, responseAsString)

// 서버 에러 예외 클래스
class HttpServerException(message: String, responseAsString: String) : HttpException(message, responseAsString)