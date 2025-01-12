package io.github.yaklede.slack.sdk.client.http.enums

enum class MediaType(
    val value: String
) {
    APPLICATION_JSON("application/json"),
    APPLICATION_JSON_UTF8("application/json; charset=utf-8"),
    APPLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded"),
    MULTIPART_FORM_DATA("multipart/form-data");
}