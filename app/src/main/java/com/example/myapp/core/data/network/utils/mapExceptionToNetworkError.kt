package com.example.myapp.core.data.network.utils

import com.example.myapp.core.data.network.model.HttpErrorCode
import com.example.myapp.core.domain.model.DataError

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.HttpResponse

fun mapExceptionToNetworkError(exception: Exception): DataError.Network {
    val clientException =
        exception as? ClientRequestException ?: return DataError.Network.UNKNOWN
    val exceptionResponse: HttpResponse = clientException.response

    return when (exceptionResponse.status.value) {
        HttpErrorCode.TIMEOUT.code -> DataError.Network.REQUEST_TIMEOUT
        else -> DataError.Network.UNKNOWN
    }
}