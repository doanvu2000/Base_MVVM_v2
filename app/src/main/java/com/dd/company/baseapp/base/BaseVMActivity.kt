package com.dd.company.baseapp.base

import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.dd.company.baseapp.base.fragment.BaseViewModel
import com.dd.company.baseapp.utils.getGenericSuperclass

abstract class BaseVMActivity<VB : ViewBinding, VM : BaseViewModel> : BaseActivity<VB>() {

    open val viewModel: VM by lazy {
        ViewModelProvider(this)[getGenericSuperclass(1)]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        lifecycle.addObserver(viewModel)
    }

    override fun onDestroy() {
        lifecycle.removeObserver(viewModel)
        super.onDestroy()
    }

}