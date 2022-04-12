package com.example.domain.providers

import com.example.domain.common.Result
import kotlinx.coroutines.*
import com.example.domain.entities.AbstractResult
import timber.log.Timber

abstract class AbstractCoroutineProvider {

    protected val scope = CoroutineScope(Dispatchers.IO)

    protected fun <RESULT_UI, RESULT_KTOR : AbstractResult<RESULT_UI>> execute(
        callback: (result: Result<RESULT_UI>) -> Unit,
        task: suspend () -> Result<RESULT_KTOR>
    ) {
        scope.launch {
            executeSuspend(callback, task())
        }
    }

    protected suspend fun <RESULT_UI, RESULT_KTOR : AbstractResult<RESULT_UI>> executeSuspend(
        callback: (result: Result<RESULT_UI>) -> Unit,
        result: Result<RESULT_KTOR>
    ) {
        withContext(Dispatchers.Main) {
            when (result) {
                is Result.Success -> callback(Result.Success(data = result.data.mapUi()))
                is Result.Fail -> callback(Result.Fail(error = result.error))
                is Result.Pending -> callback(Result.Pending())
            }

        }
    }

    open fun destroy() {
        scope.cancel()
    }
}