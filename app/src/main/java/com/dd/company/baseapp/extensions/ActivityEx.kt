package com.dd.company.baseapp.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow

fun <T> AppCompatActivity.collectFlow(targetFlow: Flow<T>, collectBlock: ((T) -> Unit)) {
    lifecycleScope.launchWhenStarted {
        targetFlow.collect {
            collectBlock(it)
        }
    }
}