package com.duzzi.kotlin.wanandroid.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.duzzi.kotlin.wanandroid.base.BaseObserver
import com.duzzi.kotlin.wanandroid.base.execute
import com.duzzi.sdk.core.DataManager
import com.duzzi.sdk.core.bean.base.BaseRsp
import com.duzzi.sdk.core.bean.data.BannerItem

class HomeViewModel : ArticleViewModel() {
    val bannerLiveData = MutableLiveData<BaseRsp<List<BannerItem>>>()

    fun getBanner() {
        DataManager.instance.getBanner().execute(BaseObserver(bannerLiveData))
    }

    fun getArticles(pageIndex: Int) {
        DataManager.instance.getHomeArticles(pageIndex).execute(BaseObserver(articleLiveData))
    }


}