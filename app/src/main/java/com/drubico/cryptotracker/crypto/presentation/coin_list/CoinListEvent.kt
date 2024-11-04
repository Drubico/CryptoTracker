package com.drubico.cryptotracker.crypto.presentation.coin_list

import com.drubico.cryptotracker.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError) : CoinListEvent
}