package com.dd.company.baseapp.widget.dialog

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.dd.company.baseapp.pref.BindingReflex
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

abstract class BaseBottomSheetDialog<VB : ViewBinding> : BottomSheetDialogFragment(),
    View.OnClickListener, CoroutineScope by MainScope() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        if (isExpanded()) {
            dialog.setOnShowListener {
                Handler(Looper.getMainLooper()).post {
                    val bottomSheet =
                        (dialog as? BottomSheetDialog)?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as? FrameLayout
                    bottomSheet?.layoutParams?.height =
                        (resources.displayMetrics.heightPixels * expandedOffset()).toInt()
                    bottomSheet?.let {
                        BottomSheetBehavior.from(it).let { behavior ->
                            behavior.peekHeight =
                                (resources.displayMetrics.heightPixels * expandedOffset()).toInt()
                            behavior.state = BottomSheetBehavior.STATE_EXPANDED
                            behavior.isDraggable = isDraggable()
                        }
                    }
                }
            }
        }
        return dialog
    }

    open fun expandedOffset(): Float {
        return 0.85f
    }

    open fun isDraggable(): Boolean {
        return true
    }

    open fun isExpanded(): Boolean {
        return false
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (null == _binding) {
            _binding = BindingReflex.reflexViewBinding(javaClass, layoutInflater)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    protected abstract fun initView()

    protected abstract fun initData()

    override fun onClick(v: View?) {

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}