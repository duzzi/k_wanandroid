package com.duzzi.kotlin.wanandroid.home.ui.adapter

import android.view.View
import android.widget.ImageView
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.duzzi.kotlin.wanandroid.DEFAULT_IMG
import com.duzzi.kotlin.wanandroid.R
import com.duzzi.kotlin.wanandroid.TITLE
import com.duzzi.kotlin.wanandroid.URL
import com.duzzi.kotlin.wanandroid.base.loadImage
import com.duzzi.kotlin.wanandroid.base.toHtml
import com.duzzi.sdk.core.bean.data.Article


class ArticleAdapter(data: MutableList<Article>?) :
    BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_article, data), LoadMoreModule {


    override fun convert(holder: BaseViewHolder, item: Article) {
        with(item) {
            holder.setText(R.id.tvTitle, title)
            holder.setText(R.id.tvAuthor, if (author.isEmpty()) shareUser else author)
                .setText(R.id.tvTitle, title.toHtml())
                .setText(R.id.tvTime, if (niceDate.isEmpty()) niceDate else niceShareDate)
                .setText(R.id.tvCategory, buildCategory(item))
            if (envelopePic.isNullOrEmpty() || envelopePic == DEFAULT_IMG) {
                holder.getView<ImageView>(R.id.ivCover).visibility = View.GONE
            } else {
                holder.getView<ImageView>(R.id.ivCover).visibility = View.VISIBLE
                holder.getView<ImageView>(R.id.ivCover).loadImage(context, envelopePic)
            }
            holder.getView<View>(R.id.ll_item).setOnClickListener {
                ARouter.getInstance().build("/web/WebActivity")
                    .withString(TITLE, title)
                    .withString(URL, link)
                    .navigation()
            }

        }
    }

    private fun buildCategory(it: Article): String {
        return when {
            it.superChapterName.isNullOrEmpty() && it.chapterName.isNullOrEmpty() -> ""
            it.superChapterName.isNullOrEmpty() -> it.chapterName ?: ""
            it.chapterName.isNullOrEmpty() -> it.superChapterName ?: ""
            else -> "${it.superChapterName} | ${it.chapterName}"
        }
    }
}