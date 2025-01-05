package io.github.yaklede.slack.sdk.client.http

class HttpEntity<T: Any?>(
    val body: T?,
    private val headers: HttpHeaders
) {
    constructor(body: T) : this(body, HttpHeaders())
    constructor(headers: HttpHeaders) : this(null, headers)
    fun hasBody() = body != null
    fun getHeaders() = headers.headers
    fun getContentType() = headers.getHeader(HttpHeaders.CONTENT_TYPE)
}