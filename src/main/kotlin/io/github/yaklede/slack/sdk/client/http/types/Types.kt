package io.github.yaklede.slack.sdk.client.http.types

import java.net.http.HttpRequest
import java.net.http.HttpResponse

typealias RequestCallBack = (HttpRequest.Builder) -> Unit
typealias ResponseExtractor<T> = (HttpResponse<String>) -> T