package com.ddaps.tamoaqui.common.domain

import com.ddaps.tamoaqui.common.domain.models.Resource
import com.ddaps.tamoaqui.util.*
import retrofit2.HttpException
import java.net.SocketTimeoutException

open class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleThrowable(e: Throwable): Resource<T> {
        return when (e) {
            is HttpException -> Resource.error(getErrorMessage(e.code()), null)
            is SocketTimeoutException -> Resource.error(getErrorMessage(SOCKET_TIMEOUT_ERROR_CODE), null)
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            -1 -> TIMEOUT
            401 -> AUTHORIZATION_DENIED
            404 -> NOT_FOUND
            else -> INTERNET_FAILURE
        }
    }
}
