package com.example.http.client

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import io.github.yaklede.slack.sdk.client.HttpEntity
import io.github.yaklede.slack.sdk.client.HttpHeaders
import io.github.yaklede.slack.sdk.client.HttpTemplate
import io.github.yaklede.slack.sdk.client.enums.HttpMethod
import io.github.yaklede.slack.sdk.client.enums.MediaType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class RestTemplateTest {

    private val uri = "https://api.upbit.com/v1/ticker?markets=KRW-BTC"

    private val marketDataTypeReference = object : TypeReference<List<MarketData>>() {}

    /**
     * template 테스트
     * upbit의 코인정보를 로드함
     */
    @Test
    fun httpTemplateTest() {
        val httpTemplate = HttpTemplate()
        val httpHeaders = HttpHeaders()
        httpHeaders.setContentType(MediaType.APPLICATION_JSON)
        httpHeaders.setAccept(MediaType.APPLICATION_JSON)

        val httpEntity = HttpEntity(null, httpHeaders)

        val result = httpTemplate.execute(
            url = uri,
            method = HttpMethod.GET,
            entity = httpEntity,
            responseType = marketDataTypeReference
        )

        Assertions.assertThat(result).isNotEmpty
        Assertions.assertThat(result.first()).isInstanceOf(MarketData::class.java)
    }
}
data class MarketData(
    @JsonProperty("market") val market: String,
    @JsonProperty("trade_date") val tradeDate: String,
    @JsonProperty("trade_time") val tradeTime: String,
    @JsonProperty("trade_date_kst") val tradeDateKst: String,
    @JsonProperty("trade_time_kst") val tradeTimeKst: String,
    @JsonProperty("trade_timestamp") val tradeTimestamp: Long,
    @JsonProperty("opening_price") val openingPrice: Double,
    @JsonProperty("high_price") val highPrice: Double,
    @JsonProperty("low_price") val lowPrice: Double,
    @JsonProperty("trade_price") val tradePrice: Double,
    @JsonProperty("prev_closing_price") val prevClosingPrice: Double,
    @JsonProperty("change") val change: String,
    @JsonProperty("change_price") val changePrice: Double,
    @JsonProperty("change_rate") val changeRate: Double,
    @JsonProperty("signed_change_price") val signedChangePrice: Double,
    @JsonProperty("signed_change_rate") val signedChangeRate: Double,
    @JsonProperty("trade_volume") val tradeVolume: Double,
    @JsonProperty("acc_trade_price") val accTradePrice: Double,
    @JsonProperty("acc_trade_price_24h") val accTradePrice24h: Double,
    @JsonProperty("acc_trade_volume") val accTradeVolume: Double,
    @JsonProperty("acc_trade_volume_24h") val accTradeVolume24h: Double,
    @JsonProperty("highest_52_week_price") val highest52WeekPrice: Double,
    @JsonProperty("highest_52_week_date") val highest52WeekDate: String,
    @JsonProperty("lowest_52_week_price") val lowest52WeekPrice: Double,
    @JsonProperty("lowest_52_week_date") val lowest52WeekDate: String,
    @JsonProperty("timestamp") val timestamp: Long
)
