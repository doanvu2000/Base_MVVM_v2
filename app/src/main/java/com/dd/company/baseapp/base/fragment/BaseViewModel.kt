package com.dd.company.baseapp.base.fragment

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import com.dd.company.baseapp.base.ActivityManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), DefaultLifecycleObserver, CoroutineScope {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope: CoroutineScope =
        CoroutineScope(viewModelJob + Dispatchers.Main.immediate)

    override val coroutineContext: CoroutineContext
        get() = viewModelJob + Dispatchers.Main

    private val handler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
    }

    protected val context by lazy {
        ActivityManager.getTopActivity()
    }

    protected fun getString(id: Int): String {
        return context?.getString(id) ?: ""
    }

    protected fun launchHandler(
        block: suspend CoroutineScope.() -> Unit,
    ) {
        viewModelScope.launch(handler) {
            block()
        }
    }

    fun <T> Flow<T>.subscribe(
        onLoading: Boolean = true,
        keepLoading: Boolean = false,
        onNext: (T) -> Unit
    ) {
        this@subscribe.onStart {
        }.onEach {
            if (it != null) {
                withContext(Dispatchers.Main) {
                    onNext.invoke(it)
                }
            }
            if (!keepLoading && onLoading) {
            }
        }.catch {
            Throwable(it).also { throwable ->
                throwable.printStackTrace()
            }
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        viewModelScope.cancel()
    }
}