package com.dd.company.baseapp.widget.dialog

import com.dd.company.baseapp.R
import com.dd.company.baseapp.databinding.TestBottomSheetBinding

class TestBottomSheet : BaseBottomSheetDialog<TestBottomSheetBinding>() {

    var clickTitle: (() -> Unit)? = null

    override fun initView() {
        initData()
    }

    override fun initData() {
//        when (arguments?.getString(LICENSE) ?: "A1") {
//            "A1" -> {
//                binding.textContentBottomSheet.text =
//                    getString(R.string.text_content_information_A1_license)
//            }
//            "A2" -> {
//                binding.textContentBottomSheet.text =
//                    getString(R.string.text_content_information_A2_license)
//            }
//        }
        initListener()
    }

    private fun initListener() {
        binding.textTitle.setOnClickListener {
            dismiss()
            clickTitle?.invoke()
        }
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
}
/** How to use:
 *
//        TestBottomSheet().also { dialog ->
//            dialog.show(supportFragmentManager,"")
//            dialog.clickTitle = {
//                dialog.dismiss()
//            }
//        }
 *
 * */