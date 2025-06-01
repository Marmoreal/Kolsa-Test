package com.example.kolsatest.presentation.commonview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.ViewFlipper
import com.example.kolsatest.R
import com.example.kolsatest.domain.isNetworkError
import com.example.kolsatest.presentation.BaseUiState

class StateViewFlipper @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ViewFlipper(context, attrs) {

    private var state = State.LOADING

    private var errorView: View
    private var loadingView: View

    private val errorTitle: TextView by lazy { errorView.findViewById(R.id.textViewErrorTitle) }
    private val buttonUpdate: Button by lazy { errorView.findViewById(R.id.buttonUpdate) }

    init {
        val layoutInflater = LayoutInflater.from(context)

        loadingView = layoutInflater.inflate(R.layout.view_flipper_state_loading, this, false)
        addView(loadingView)

        errorView = layoutInflater.inflate(R.layout.view_flipper_state_error, this, false)
        addView(errorView)
    }

    enum class State(val displayedChild: Int) {
        LOADING(0),
        ERROR(1),
        DATA(2),
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (isInEditMode) {
            changeState(State.DATA)
        }
    }

    fun setState(
        state: BaseUiState,
    ) {
        when {
            state.isLoading -> changeState(State.LOADING)
            state.error != null -> setStateError(state.error)
            else -> changeState(State.DATA)
        }
    }

    fun currentStateIsData() = state == State.DATA

    private fun changeState(newState: State) {
        if (state != newState || displayedChild != newState.displayedChild) {
            state = newState
            displayedChild = newState.displayedChild
        }
    }

    fun setStateError(error: Throwable?) {
        if (error == null) return
        changeState(State.ERROR)
        errorTitle.text = if (error.isNetworkError) {
            context.getString(R.string.state_view_flipper_error_state_network_title)
        } else context.getString(R.string.state_view_flipper_error_state_network_title)
    }

    fun setRetryMethod(retry: () -> Unit) {
        buttonUpdate.setOnClickListener { retry() }
    }
}
