package com.drubico.cryptotracker.crypto.data

import com.drubico.cryptotracker.core.data.networking.CoinDataSource
import com.drubico.cryptotracker.core.data.networking.constructUrl
import com.drubico.cryptotracker.core.data.networking.safeCall
import com.drubico.cryptotracker.core.domain.util.NetworkError
import com.drubico.cryptotracker.core.domain.util.Result
import com.drubico.cryptotracker.core.domain.util.map
import com.drubico.cryptotracker.crypto.data.dto.CoinResponseDto
import com.drubico.cryptotracker.crypto.data.mappers.toCoin
import com.drubico.cryptotracker.crypto.domain.Coin
import io.ktor.client.HttpClient
import io.ktor.client.request.get

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
}