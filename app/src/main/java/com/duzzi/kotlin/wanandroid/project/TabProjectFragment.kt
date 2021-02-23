package com.duzzi.kotlin.wanandroid.project

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.CollectionUtils
import com.duzzi.kotlin.wanandroid.base.toHtml
import com.duzzi.kotlin.wanandroid.databinding.FragmentTabProjectBinding
import com.duzzi.ui.base.BaseFragment
import com.duzzi.ui.view.VP2Adapter

class TabProjectFragment : BaseFragment<ProjectViewModel, FragmentTabProjectBinding>() {

    companion object {
        @JvmStatic
        fun newInstance() = TabProjectFragment()
    }


    override fun addObserver() {
        viewModel.projectCategoryLiveData.observe(this, Observer {
            val titles = mutableListOf<String>()
            val fragments = mutableListOf<Fragment>()
            val data = it.data
            if (CollectionUtils.isEmpty(data)) {
                return@Observer
            }
            data.forEach { item ->
                titles.add(item.name.toHtml())
                fragments.add(ProjectListFragment.newInstance(item.id))
            }
            binding.viewPager2.offscreenPageLimit = data.size
            binding.viewPager2.adapter = VP2Adapter(this, data, fragments)
            binding.tabLayout.setViewPager(binding.viewPager2, titles)
        })
    }

    override fun initView(view: View) {


    }

    override fun loadData() {
        viewModel.getProjectTree()
    }

}
