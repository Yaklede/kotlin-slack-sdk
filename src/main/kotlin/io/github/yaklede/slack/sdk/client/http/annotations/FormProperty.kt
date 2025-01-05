package io.github.yaklede.slack.sdk.client.http.annotations

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class FormProperty(
    val name: String
)