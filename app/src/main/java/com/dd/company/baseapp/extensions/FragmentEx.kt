package com.dd.company.baseapp.extensions

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow

fun Fragment.visibilityFlow(targetFlow: Flow<Boolean>, vararg view: View) {
    collectFlow(targetFlow) { loading ->
        view.forEach { it.isVisible = loading }
    }
}

fun <T> Fragment.collectFlow(targetFlow: Flow<T>, collectBlock: ((T) -> Unit)) {
    lifecycleScope.launchWhenStarted {
        targetFlow.collect {
            collectBlock(it)
        }
    }
}
var lastClickNavigate = 0L
fun NavController.safeNavigate(id: Int, bundle: Bundle? = null) {
    val time = System.currentTimeMillis()
    if (time - lastClickNavigate >= 800) {
        lastClickNavigate = time
        if (bundle != null) {
            navigate(id, bundle)
        } else {
            navigate(id)
        }
    }
}