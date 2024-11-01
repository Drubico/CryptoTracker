package com.drubico.cryptotracker.crypto.presentation.coin_list

import androidx.compose.runtime.Immutable
import com.drubico.cryptotracker.crypto.presentation.models.CoinUi

@Immutable
data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<CoinUi> = emptyList(),
    val selectedCoin: CoinUi? = null,
)
// la clase nunca cambia , si cambia es otra clase