package com.dd.company.baseapp.views.main

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dd.company.baseapp.R
import com.dd.company.baseapp.base.fragment.BaseFragment
import com.dd.company.baseapp.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val viewModel by activityViewModels<ViewModelTest>()

    private val navController by lazy {
        findNavController()
    }

    override fun initView() {
    }

    override fun initListener() {
        binding.btnFoward.setOnClickListener {
            viewModel.listData.value = viewModel.listData.value + 1
            navController.navigate(R.id.to_Second_Fragment)
        }
    }

    override fun initData() {
        binding.tvData.text = viewModel.listData.value.toString()
    }

    override fun onResume() {
        super.onResume()
        initData()
    }
}