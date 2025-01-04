package io.github.yaklede.slack.sdk.client

class HttpEntity<T>(
    val body: T?,
    private val headers: HttpHeaders
) {
    constructor(body: T) : this(body, HttpHeaders())
    constructor(headers: HttpHeaders) : this(null, headers)
    fun hasBody() = body != null
    fun getHeaders() = headers.headers
}