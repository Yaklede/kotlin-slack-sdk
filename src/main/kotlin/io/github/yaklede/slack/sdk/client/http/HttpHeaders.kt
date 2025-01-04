package io.github.yaklede.slack.sdk.client.http

import io.github.yaklede.slack.sdk.client.http.enums.MediaType


class HttpHeaders {
    val headers: MutableMap<String, String> = mutableMapOf()

    companion object {
        const val ACCEPT = "Accept"
        const val CONTENT_TYPE = "Content-Type"
    }

    fun addHeader(key: String, value: String) {
        headers[key] = value
    }

    fun getHeader(key: String): String? {
        return headers[key]
    }

    fun removeHeader(key: String) {
        headers.remove(key)
    }

    fun addBearerToken(token: String) {
        addHeader(ACCEPT, token)
    }

    fun setContentType(contentType: String) {
        addHeader(CONTENT_TYPE, contentType)
    }

    fun setContentType(contentType: MediaType) {
        addHeader(CONTENT_TYPE, contentType.value)
    }

    fun setAccept(accept: String) {
        addHeader(ACCEPT, accept)
    }

    fun setAccept(accept: MediaType) {
        addHeader(ACCEPT, accept.value)
    }
}