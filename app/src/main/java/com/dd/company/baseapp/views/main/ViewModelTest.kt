package com.dd.company.baseapp.views.main

import com.dd.company.baseapp.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ViewModelTest : BaseViewModel() {
    var listData = MutableStateFlow(0)
}