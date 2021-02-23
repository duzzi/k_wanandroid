package com.duzzi.kotlin.wanandroid.qa

import android.view.View
import androidx.lifecycle.Observer
import com.duzzi.kotlin.wanandroid.databinding.FragmentArticleBinding
import com.duzzi.kotlin.wanandroid.home.ui.ArticleFragment

class TabQuestionAnswerFragment : ArticleFragment<QuestionAnswerViewModel, FragmentArticleBinding>() {

    companion object{
        fun newInstance() = TabQuestionAnswerFragment()
    }

    override fun initView(view: View) {
        super.initView(view)
        binding.fakeStatusBar.visibility = View.VISIBLE
        binding.tvTitle.visibility = View.VISIBLE
    }

    override fun loadMore() {
        viewModel.getQuestionAnswer(++pageIndex)
    }

    override fun addObserver() {
        viewModel.liveData.observe(this, Observer {
            setData(it)
        })
    }

    override fun loadData() {
        viewModel.getQuestionAnswer(pageIndex)
    }
}