package com.dd.company.baseapp.views.main

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dd.company.baseapp.base.fragment.BaseFragment
import com.dd.company.baseapp.databinding.FragmentBlankBinding


class SecondFragment : BaseFragment<FragmentBlankBinding>() {

    private val viewModel by activityViewModels<ViewModelTest>()

    override fun initView() {
    }

    override fun initListener() {
        binding.btnBack.setOnClickListener {
            viewModel.listData.value = viewModel.listData.value + 1
            findNavController().popBackStack()
        }
    }

    override fun initData() {
        binding.tvData.text = viewModel.listData.value.toString()
    }
}