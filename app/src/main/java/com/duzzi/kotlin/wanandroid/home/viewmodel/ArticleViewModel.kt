package com.duzzi.kotlin.wanandroid.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.duzzi.sdk.core.bean.base.BaseRsp
import com.duzzi.sdk.core.bean.base.BaseViewModel
import com.duzzi.sdk.core.bean.data.ArticleModel

open class ArticleViewModel : BaseViewModel() {
    open val articleLiveData = MutableLiveData<BaseRsp<ArticleModel>>()
}