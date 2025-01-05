package io.github.yaklede.slack.sdk.client.http.exception

import kotlin.reflect.KClass

class ParseException(
    val body: String,
    clazz: KClass<*>
): RuntimeException("can not parse response $body to ${clazz.simpleName}")