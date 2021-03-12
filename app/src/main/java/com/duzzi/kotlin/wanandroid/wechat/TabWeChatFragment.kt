package com.duzzi.kotlin.wanandroid.wechat

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.CollectionUtils
import com.duzzi.kotlin.wanandroid.base.toHtml
import com.duzzi.kotlin.wanandroid.databinding.FragmentTabProjectBinding
import com.duzzi.kotlin.wanandroid.databinding.LayoutActionBarBinding
import com.duzzi.ui.base.LifecycleFragment
import com.duzzi.ui.view.VP2Adapter

class TabWeChatFragment : LifecycleFragment<WeChatViewModel, FragmentTabProjectBinding>() {

    companion object {
        @JvmStatic
        fun newInstance() = TabWeChatFragment()
    }


    override fun addObserver() {
        viewModel.weChatCategoryLiveData.observe(this, Observer {
            val titles = mutableListOf<String>()
            val fragments = mutableListOf<Fragment>()
            val data = it.data
            if (CollectionUtils.isEmpty(data)) {
                return@Observer
            }
            data.forEach { item ->
                titles.add(item.name.toHtml())
                fragments.add(WeChatListFragment.newInstance(item.id))
            }
            binding.viewPager2.adapter = VP2Adapter(this, data, fragments)
            binding.tabLayout.setViewPager(binding.viewPager2, titles)
        })
    }

    override fun initView(view: View) {
        val includeBinding = LayoutActionBarBinding.bind(binding.root)
        includeBinding.fakeStatusBar.visibility = View.VISIBLE
        includeBinding.tvTitle.visibility = View.VISIBLE
    }


    override fun loadData() {
        viewModel.getWeChatCategory()
    }

}
