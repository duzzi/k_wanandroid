package com.duzzi.kotlin.wanandroid.home.ui.adapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.duzzi.kotlin.wanandroid.DEFAULT_IMG
import com.duzzi.kotlin.wanandroid.R
import com.duzzi.kotlin.wanandroid.base.loadImage
import com.duzzi.kotlin.wanandroid.base.toHtml
import com.duzzi.sdk.core.bean.data.Article

class ArticleAdapter(data: MutableList<Article>?) :
    BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_article, data), LoadMoreModule {


    override fun convert(holder: BaseViewHolder, item: Article) {
        holder.setText(R.id.tvTitle, item.title)

        holder.run {
            setText(R.id.tvAuthor, if (item.author.isEmpty()) item.shareUser else item.author)
                .setText(R.id.tvTitle, item.title.toHtml())
                .setText(R.id.tvTime, if (item.niceDate.isEmpty()) item.niceDate else item.niceShareDate)
                .setText(R.id.tvCategory, buildCategory(item))
            if (item.envelopePic.isNullOrEmpty()|| item.envelopePic == DEFAULT_IMG) {
                getView<ImageView>(R.id.ivCover).visibility = View.GONE
            } else {
                getView<ImageView>(R.id.ivCover).visibility = View.VISIBLE
                getView<ImageView>(R.id.ivCover).loadImage(context, item.envelopePic)
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