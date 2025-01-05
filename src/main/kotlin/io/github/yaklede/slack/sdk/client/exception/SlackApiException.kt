package io.github.yaklede.slack.sdk.client.exception

class SlackApiException(
    body: String?,
): RuntimeException(body)