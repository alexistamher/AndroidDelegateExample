package com.spookybrain.delegateexample

import android.view.View

sealed class MainUiState(
    val loading: Int = View.GONE,
    val list: Int = View.GONE,
    val empty: Int = View.GONE
) {
    object Loading : MainUiState(loading = View.VISIBLE)
    object ShowList : MainUiState(list = View.VISIBLE)
    object EmptyList : MainUiState(empty = View.VISIBLE)
}