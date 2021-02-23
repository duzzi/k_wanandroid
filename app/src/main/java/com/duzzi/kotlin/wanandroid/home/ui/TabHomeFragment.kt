package com.duzzi.kotlin.wanandroid.home.ui

import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.SizeUtils
import com.duzzi.kotlin.wanandroid.databinding.FragmentArticleBinding
import com.duzzi.kotlin.wanandroid.home.ui.adapter.HomeBannerAdapter
import com.duzzi.kotlin.wanandroid.home.viewmodel.HomeViewModel
import com.duzzi.sdk.core.bean.base.BaseRsp
import com.duzzi.sdk.core.bean.data.BannerItem
import com.youth.banner.Banner
import com.youth.banner.indicator.RectangleIndicator


class TabHomeFragment : ArticleFragment<HomeViewModel, FragmentArticleBinding>() {

    companion object {
        @JvmStatic
        fun newInstance() = TabHomeFragment()
    }


    override fun initView(view: View) {
        super.initView(view)
        banner = Banner(mContext)
        articleAdapter.addHeaderView(banner)
    }

    override fun loadMore() {
        viewModel.getArticles(++pageIndex)
    }

    override fun loadData() {
        pageIndex = 0
        viewModel.getBanner()
        viewModel.getArticles(pageIndex)
    }

    override fun addObserver() {
        viewModel.bannerLiveData.observe(this, Observer { addBanner(it) })
        viewModel.articleLiveData.observe(this, Observer { setData(it) })
    }

    private fun addBanner(it: BaseRsp<List<BannerItem>>) {
        banner.addBannerLifecycleObserver(this)//添加生命周期观察者
            .setAdapter(HomeBannerAdapter(it.data))
            .indicator = RectangleIndicator(mContext)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            SizeUtils.dp2px(200f)
        )
        layoutParams.bottomMargin = SizeUtils.dp2px(10f)
        banner.layoutParams = layoutParams
    }
}