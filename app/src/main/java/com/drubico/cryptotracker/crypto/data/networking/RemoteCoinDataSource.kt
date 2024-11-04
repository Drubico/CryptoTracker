package com.drubico.cryptotracker.crypto.data.networking

import com.drubico.cryptotracker.core.data.networking.constructUrl
import com.drubico.cryptotracker.core.data.networking.safeCall
import com.drubico.cryptotracker.core.domain.util.NetworkError
import com.drubico.cryptotracker.core.domain.util.Result
import com.drubico.cryptotracker.core.domain.util.map
import com.drubico.cryptotracker.crypto.data.mappers.toCoin
import com.drubico.cryptotracker.crypto.data.mappers.toCoinPrice
import com.drubico.cryptotracker.crypto.data.networking.dto.CoinHistoryDto
import com.drubico.cryptotracker.crypto.data.networking.dto.CoinResponseDto
import com.drubico.cryptotracker.crypto.domain.Coin
import com.drubico.cryptotracker.crypto.domain.CoinDataSource
import com.drubico.cryptotracker.crypto.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

class RemoteCoinDataSource(
    private val httpsClient: HttpClient,
) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinResponseDto> {
            httpsClient.get(
                urlString = constructUrl("/assets"),
            )
        }.map { response ->
            response.data.map { coinDto ->
                coinDto.toCoin()
            }
        }
    }

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start.withZoneSameInstant(
            ZoneId.of("UTC")
        ).toInstant().toEpochMilli()
        val endMillis = end.withZoneSameInstant(
            ZoneId.of("UTC")
        ).toInstant().toEpochMilli()
        return safeCall<CoinHistoryDto> {
            httpsClient.get(
                urlString = constructUrl("/assets/$coinId/history"),
            ) {
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            response.data.map {
                it.toCoinPrice()
            }
        }
    }
}