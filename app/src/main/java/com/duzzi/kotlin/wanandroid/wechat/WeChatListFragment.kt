package com.duzzi.kotlin.wanandroid.wechat

import android.os.Bundle
import androidx.lifecycle.Observer
import com.duzzi.kotlin.wanandroid.CID
import com.duzzi.kotlin.wanandroid.databinding.FragmentArticleBinding
import com.duzzi.kotlin.wanandroid.home.ui.ArticleFragment

class WeChatListFragment : ArticleFragment<WeChatListViewModel, FragmentArticleBinding>() {

    private var cid: Int = -1

    companion object {
        fun newInstance(cid: Int): WeChatListFragment {
            val args = Bundle()
            args.putInt(CID, cid)
            val fragment = WeChatListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cid = arguments?.getInt(CID)!!
    }


    override fun loadMore() {
        viewModel.getWeChatList(cid,++pageIndex)
    }

    override fun addObserver() {
        viewModel.weChatListLiveData.observe(this, Observer {
            setData(it)
        })
    }

    override fun loadData() {
        pageIndex = 0
        viewModel.getWeChatList( cid,pageIndex)
    }
}