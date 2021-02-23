package com.duzzi.kotlin.wanandroid.project

import android.os.Bundle
import androidx.lifecycle.Observer
import com.duzzi.kotlin.wanandroid.CID
import com.duzzi.kotlin.wanandroid.databinding.FragmentArticleBinding
import com.duzzi.kotlin.wanandroid.home.ui.ArticleFragment

class ProjectListFragment : ArticleFragment<ProjectListViewModel, FragmentArticleBinding>() {

    private var cid: Int = -1

    companion object {
        fun newInstance(cid: Int): ProjectListFragment {
            val args = Bundle()
            args.putInt(CID, cid)
            val fragment = ProjectListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cid = arguments?.getInt(CID)!!
    }


    override fun loadMore() {
        viewModel.getProjectList(++pageIndex, cid)
    }

    override fun addObserver() {
        viewModel.projectListLiveData.observe(this, Observer {
            setData(it)
        })
    }

    override fun loadData() {
        pageIndex = 0
        viewModel.getProjectList(pageIndex, cid)
    }
}