package com.example.kolsatest.domain

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

val Throwable.isNetworkError: Boolean
    get() = when (this) {
        is ConnectException,
        is UnknownHostException,
        is SocketTimeoutException -> true

        else -> false
    }