package com.duzzi.kotlin.wanandroid.home.ui.adapter

import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.duzzi.sdk.core.bean.data.BannerItem
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.holder.BannerImageHolder

class HomeBannerAdapter(datas: List<BannerItem>) : BannerAdapter<BannerItem, BannerImageHolder>(datas) {
    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerImageHolder {
        val imageView = ImageView(parent!!.context)
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerImageHolder(imageView)
    }

    override fun onBindView(holder: BannerImageHolder?, data: BannerItem?, position: Int, size: Int) {
        Glide
            .with(holder!!.imageView)
            .load(data!!.imagePath)
            .centerCrop()
//            .placeholder(R.drawable.loading_spinner)
            .into(holder.imageView)
    }

}