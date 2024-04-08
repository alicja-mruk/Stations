package com.example.myapp.core.presentation.utils

import com.example.myapp.R
import com.example.myapp.core.domain.model.DataError
import com.example.myapp.core.domain.model.Result

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(R.string.the_request_timed_out)
        DataError.Network.NO_INTERNET -> UiText.StringResource(R.string.no_internet)
        DataError.Network.SERVER_ERROR -> UiText.StringResource(R.string.server_error)
        DataError.Network.UNKNOWN -> UiText.StringResource(R.string.unknown_error)
        DataError.Local.DISK_FULL -> UiText.StringResource(R.string.error_disk_full)
        DataError.Database.EMPTY_DB -> UiText.StringResource(R.string.empty_database)
        DataError.Database.ID_NOT_FOUND -> UiText.StringResource(R.string.empty_database)
    }
}

fun Result.Error<*, DataError>.asErrorUiText(): UiText {
    return error.asUiText()
}