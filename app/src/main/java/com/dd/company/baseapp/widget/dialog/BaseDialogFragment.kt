package com.dd.company.baseapp.widget.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.dd.company.baseapp.pref.BindingReflex
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

abstract class BaseDialogFragment<VB : ViewBinding> : AppCompatDialogFragment(),
    View.OnClickListener, CoroutineScope by MainScope() {

    var isCancelTouch: Boolean = false

    @StyleRes
    protected open fun getDialogTheme(): Int = 0
    protected open fun getDialogStyle(): Int = 0

    private var _binding: VB? = null
    protected val binding get() = _binding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setStyle(
//            if (getDialogStyle() != 0) getDialogStyle() else STYLE_NO_TITLE,
//            if (getDialogTheme() != 0) getDialogTheme() else R.style.fly_uikit_CommonDialog
//        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = object : Dialog(requireActivity()) {
            override fun onTouchEvent(event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_UP) {
                    val dialogBounds = Rect()
                    window!!.decorView.getHitRect(dialogBounds)
                    if (!dialogBounds.contains(
                            event.x.toInt(),
                            event.y.toInt()
                        )
                    ) {
                        if (isCancelTouch) onDismissPopup()
                    }
                }
                return true
            }

            override fun dispatchTouchEvent(event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_UP) {
                    val dialogBounds = Rect()
                    window!!.decorView.getHitRect(dialogBounds)
                    if (!dialogBounds.contains(
                            event.x.toInt(),
                            event.y.toInt()
                        )
                    ) {
                        if (isCancelTouch) onDismissPopup()
                    }
                }
                return super.dispatchTouchEvent(event)
            }

            override fun onBackPressed() {
                if (isCancelTouch) onDismissPopup()
            }
        }
        if (isFullScreen()) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(android.R.color.transparent))
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        dialog.setCanceledOnTouchOutside(isCancelTouch)
        return dialog
    }

    private fun onDismissPopup() {
        dismissAllowingStateLoss()
    }

    open fun isFullScreen(): Boolean {
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (null == _binding) {
            _binding = BindingReflex.reflexViewBinding(javaClass, layoutInflater)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    abstract fun initView()

    abstract fun initData()

    override fun onClick(v: View?) {

    }

    open fun isShowing(): Boolean {
        return isAdded && dialog != null && dialog?.isShowing == true
    }

    open fun show(activity: FragmentActivity, tag: String? = null) {
        showAllowStateLoss(activity.supportFragmentManager, tag ?: this::javaClass.name)
    }

    open fun show(fragment: Fragment, tag: String? = null) {
        showAllowStateLoss(fragment.parentFragmentManager, tag ?: this::javaClass.name)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}


fun DialogFragment.showAllowStateLoss(manager: FragmentManager, tag: String) {
    try {
        val mDismissed = DialogFragment::class.java.getDeclaredField("mDismissed")
        mDismissed.isAccessible = true
        mDismissed.set(this, false)
        val mShownByMe = DialogFragment::class.java.getDeclaredField("mShownByMe")
        mShownByMe.isAccessible = true
        mShownByMe.set(this, true)
        manager.beginTransaction().add(this, tag).commitNowAllowingStateLoss()
    } catch (e: Exception) {
        try {
            manager.beginTransaction().add(this, tag).commitNowAllowingStateLoss()
        } catch (exception: Exception) {
            exception.printStackTrace()
            Log.i("DialogFragment", "showAllowStateLoss error")
        }
        Log.i("DialogFragment", "reflect showAllowStateLoss error")
        e.printStackTrace()
    }
}