package io.github.yaklede.slack.sdk.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

val mapper = jacksonObjectMapper().apply {
    registerKotlinModule()
}