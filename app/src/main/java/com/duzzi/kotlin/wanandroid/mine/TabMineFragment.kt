package com.duzzi.kotlin.wanandroid.mine

import android.view.View
import com.duzzi.kotlin.wanandroid.databinding.FragmentTabMineBinding
import com.duzzi.sdk.core.bean.base.BaseViewModel
import com.duzzi.ui.base.LifecycleFragment

class TabMineFragment : LifecycleFragment<BaseViewModel, FragmentTabMineBinding>() {

    companion object {
        fun newInstance() = TabMineFragment()
    }

    override fun addObserver() {
    }

    override fun initView(view: View) {

    }

    override fun loadData() {
        showSuccess()
    }
}