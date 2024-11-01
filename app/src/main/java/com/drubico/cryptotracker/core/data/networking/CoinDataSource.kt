package com.drubico.cryptotracker.core.data.networking

import com.drubico.cryptotracker.core.domain.util.NetworkError
import com.drubico.cryptotracker.crypto.domain.Coin
import com.drubico.cryptotracker.core.domain.util.Result

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}