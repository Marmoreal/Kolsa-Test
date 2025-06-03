package com.example.kolsatest.presentation.extension

import com.example.kolsatest.domain.LoadableResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * метод используется во viewModel
 * onEach подписывается на каждый элемент потока
 * launchIn(viewModelScope) запускает поток в ViewModel (и отменяет при уничтожении)
 */
inline fun <T> Flow<LoadableResult<T>>.collectResult(
    scope: CoroutineScope,
    crossinline onLoading: () -> Unit = {},
    crossinline onSuccess: (T) -> Unit,
    crossinline onError: (Throwable) -> Unit = {}
) {
    onEach { result ->
        when (result) {
            is LoadableResult.Loading -> onLoading()
            is LoadableResult.Success -> onSuccess(result.value)
            is LoadableResult.Failure -> onError(result.error)
        }
    }.launchIn(scope)
}
