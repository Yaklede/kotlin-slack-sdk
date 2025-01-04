package io.github.yaklede.slack.sdk.client.http.enums

enum class MediaType(
    val value: String
) {
    APPLICATION_JSON("application/json"),
    APPLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded"),
    MULTIPART_FORM_DATA("multipart/form-data");
}