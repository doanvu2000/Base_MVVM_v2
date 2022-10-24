package com.dd.company.baseapp.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding
import com.dd.company.baseapp.pref.BindingReflex

abstract class BaseFragment<VB : ViewBinding> : Fragment(),
    View.OnClickListener, DefaultLifecycleObserver {

    private var isLoaded = false
    private var lastClickTime = 0L
    private val interval by lazy { 500L }
    private var _binding: VB? = null
    protected val binding get() = _binding!!


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (null == _binding) {
            _binding = BindingReflex.reflexViewBinding(javaClass, layoutInflater)
            initView()
            initListener()
            initData()
        }
        return binding.root.apply {
            isFocusable = true
            isClickable = true
        }
    }

//    override fun onResume() {
//        super<Fragment>.onResume()
//        if (!isLoaded && !isHidden) {
//            lifecycleScope.launchWhenResumed {
//                initData()
//                if (BuildConfig.DEBUG) {
//                    println("SCREEN_APP ${this@BaseFragment::class.java.name}")
//                }
//            }
//            isLoaded = true
//        }
//    }

    open fun idFragmentContainer(): Int = 0

    abstract fun initView()

    abstract fun initListener()

    abstract fun initData()

    override fun onDestroy() {
        isLoaded = false
        super<Fragment>.onDestroy()
    }

    override fun onDetach() {
        _binding = null
        lifecycle.removeObserver(this)
        super.onDetach()
    }

    override fun onClick(v: View) {
        val nowTime = System.currentTimeMillis()
        if (nowTime - lastClickTime > interval) {
            onSingleClick(v)
            lastClickTime = nowTime
        }
    }

    open fun onSingleClick(v: View) {

    }

    open fun onBackPressed() {
        if (!childFragmentManager.popBackStackImmediate()) {
            requireActivity().onBackPressed()
        }
    }

    private fun hasBackStack(): Boolean {
        return childFragmentManager.backStackEntryCount > 1
    }

    protected fun <T> MutableLiveData<T>.observe(function: (T) -> Unit) {
        this.observe(this@BaseFragment) {
            function.invoke(it)
        }
    }
}