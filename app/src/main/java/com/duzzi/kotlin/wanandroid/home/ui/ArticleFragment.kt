package com.duzzi.kotlin.wanandroid.home.ui

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.duzzi.kotlin.wanandroid.databinding.FragmentArticleBinding
import com.duzzi.kotlin.wanandroid.home.ui.adapter.ArticleAdapter
import com.duzzi.kotlin.wanandroid.home.ui.adapter.HomeBannerAdapter
import com.duzzi.kotlin.wanandroid.home.viewmodel.ArticleViewModel
import com.duzzi.sdk.core.bean.base.BaseRsp
import com.duzzi.sdk.core.bean.data.ArticleModel
import com.duzzi.sdk.core.bean.data.BannerItem
import com.duzzi.ui.base.BaseFragment
import com.youth.banner.Banner

abstract class ArticleFragment<VM : ArticleViewModel, VB> : BaseFragment<VM, FragmentArticleBinding>() {

    lateinit var articleAdapter: ArticleAdapter
    lateinit var banner: Banner<BannerItem, HomeBannerAdapter>
    var pageIndex = 0

    override fun initView(view: View) {
        articleAdapter = ArticleAdapter(null)
        articleAdapter.animationEnable = true

        binding.rv.layoutManager = LinearLayoutManager(mContext)
        binding.rv.adapter = articleAdapter

        binding.refreshLayout.setOnRefreshListener {
            pageIndex = 0
            loadData()
        }
        binding.refreshLayout.setOnLoadMoreListener { loadMore() }
    }


    fun setData(it: BaseRsp<ArticleModel>) {
        if (binding.refreshLayout.isRefreshing) {
            articleAdapter.setNewInstance(it.data.datas)
            binding.refreshLayout.finishRefresh()
        } else {
            articleAdapter.addData(it.data.datas)
            if (articleAdapter.data.size < it.data.total) {
                binding.refreshLayout.finishLoadMore(0)
            } else {
                binding.refreshLayout.finishLoadMoreWithNoMoreData()
            }
        }
    }


    abstract fun loadMore()
}