package com.dd.company.baseapp.widget.toolbar

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.dd.company.baseapp.R
import com.dd.company.baseapp.databinding.LayoutToolbarBinding
import com.dd.company.baseapp.extensions.viewBinding

class ToolbarCustom : RelativeLayout {
    private val binding by viewBinding(LayoutToolbarBinding::bind)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.layout_toolbar, this)
        initViews(attrs)
    }

    private fun initViews(attrs: AttributeSet?) {
        attrs?.let {
//            context.obtainStyledAttributes(it, R.styleable.ToolbarCustom).apply {
//                try {
//                    initUI(this)
//                    initListener()
//                } finally {
//                    recycle()
//                }
//            }
        }
    }

    private fun initListener() {
//        binding.imgBack.setOnClickListener {
//            (context as MainActivity).onBackPressed()
//        }
//        binding.imgSearch.setOnClickListener {
//            (context as MainActivity).setCurrentFragment(SearchFragment.newInstance("Tìm kiếm",""))
//            (context as MainActivity).mainViewModel.stackFragment.add(KEY_HOME_FRAGMENT)
//        }
    }

    private fun initUI(typedArray: TypedArray) {
//        binding.imgBack.visibility = if (typedArray.getBoolean(
//                R.styleable.ToolbarCustom_is_show_back_button,
//                false
//            )
//        ) View.VISIBLE else View.GONE
//        binding.imgSearch.visibility = if (typedArray.getBoolean(
//                R.styleable.ToolbarCustom_is_show_search_button,
//                false
//            )
//        ) View.VISIBLE else View.GONE
//        binding.tvTitleToolbar.text = typedArray.getString(R.styleable.ToolbarCustom_title)
    }

    fun setTitle(str: String) {
        binding.tvTitleToolbar.text = str
    }
}