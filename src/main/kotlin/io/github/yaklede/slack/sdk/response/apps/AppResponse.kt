package io.github.yaklede.slack.sdk.response.apps

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.github.yaklede.slack.sdk.response.SlackResponse

interface AppsActivityPayload

@Deprecated("only workflows apps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class FunctionExecutionStartedPayload(
    val functionName: String,
    val functionType: String,
) : AppsActivityPayload

@Deprecated("only workflows apps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class FunctionExecutionResult(
    val functionName: String,
    val error: String?
) : AppsActivityPayload

@Deprecated("only workflows apps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class FunctionExecutionOutput(
    val log: String
) : AppsActivityPayload

@Deprecated("only workflows apps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class FunctionDeployment(
    val action: String,
    val teamId: String,
    val userId: String,
    val bundleSizeKb: Long
) : AppsActivityPayload

@Deprecated("only workflows apps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class WorkflowBotInvited(
    val channelId: String,
    val botUserId: String
) : AppsActivityPayload

@Deprecated("only workflows apps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class WorkflowExecutionStarted(
    val workflowName: String,
    val actor: String
) : AppsActivityPayload

@Deprecated("only workflows apps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class WorkflowExecutionResult(
    val workflowName: String,
    val exeOutcome: String,
) : AppsActivityPayload

@Deprecated("only workflows apps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class WorkflowStepStarted(
    val functionId: String,
    val totalSteps: Long,
    val currentStep: Long,
    val functionName: String,
    val functionExecutionId: String,
) : AppsActivityPayload

@Deprecated("only workflows apps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class WorkflowPublished(
    val workflowName: String,
) : AppsActivityPayload

@Deprecated("only workflows apps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class WorkflowUnpublished(
    val workflowName: String,
) : AppsActivityPayload

@Deprecated("only workflows apps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class WorkflowStepExecutionResult(
    val inputs: Any,
    val functionId: String,
    val execOutcome: String,
    val functionName: String,
    val functionExecutionId: String,
) : AppsActivityPayload

@Deprecated("only workflows apps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class WorkflowCreatedFromTemplate(
    val templateId: String,
    val dataCreated: Long,
) : AppsActivityPayload

@Deprecated("only workflows apps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class TriggerExecuted(
    val trigger: Trigger,
    val functionName: String
) : AppsActivityPayload {
    @Deprecated("only workflows apps")
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Trigger(
        val id: String,
        val type: String,
        val config: Config,
        val tripInformation: TripInformation?
    )

    @Deprecated("only workflows apps")
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Config(
        val name: String,
        val description: String?
    )

    @Deprecated("only workflows apps")
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class TripInformation(
        val userId: String?,
        val channelId: String?
    )
}

@Deprecated("only workflows apps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class ExternalAuthStarted(
    val code: String, // Possible values: 'app_not_found', 'app_not_installed', 'provider_not_found', 'external_auth_started'
    val teamId: String,
    val userId: String,
    val providerKey: String,
    val appId: String
)

@Deprecated("only workflows apps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class ExternalAuthResult(
    val code: String, // Possible values: 'oauth2_callback_error', 'oauth2_exchange_success'
    val teamId: String,
    val userId: String,
    val providerKey: String,
    val appId: String
)

@Deprecated("only workflows apps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class ExternalAuthTokenFetchResult(
    val code: String, // Possible values: 'no_collaborator_found', 'external_token_found', 'token_not_found', etc.
    val teamId: String,
    val userId: String,
    val providerKey: String,
    val appId: String
)

@Deprecated("only workflows apps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class ExternalAuthMissingFunction(
    val code: String, // Possible value: 'function_not_found'
    val teamId: String,
    val functionId: String,
    val appId: String
)

@Deprecated("only workflows apps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class ExternalAuthMissingSelectedAuth(
    val code: String, // Possible values: 'missing_oauth_token_or_selected_auth' or other errors
    val teamId: String,
    val userId: String,
    val providerKey: String,
    val appId: String
)

@Deprecated("only workflows apps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class AppsActivitiesListResponse(
    val ok: Boolean,
    val activities: List<Activity>,
    val responseMetadata: AppsActivitiesListResponseMetadata,
) : SlackResponse {
    override fun isSuccess(): Boolean {
        return ok
    }
}

@Deprecated("only workflows apps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class AppsActivitiesListResponseMetadata(
    val nextCursor: String,
)

@Deprecated("only workflows apps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class Activity(
    val level: String,
    val eventType: String,
    val source: String,
    val componentType: String,
    val componentId: String,
    val payload: AppsActivityPayload,
    val created: Long,
    val traceId: String,
)