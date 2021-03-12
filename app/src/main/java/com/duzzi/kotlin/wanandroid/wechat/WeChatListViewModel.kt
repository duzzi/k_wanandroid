package com.duzzi.kotlin.wanandroid.wechat

import androidx.lifecycle.MutableLiveData
import com.duzzi.kotlin.wanandroid.base.BaseObserver
import com.duzzi.kotlin.wanandroid.base.execute
import com.duzzi.kotlin.wanandroid.home.viewmodel.ArticleViewModel
import com.duzzi.sdk.core.DataManager
import com.duzzi.sdk.core.bean.base.BaseRsp
import com.duzzi.sdk.core.bean.data.ArticleModel

class WeChatListViewModel : ArticleViewModel() {

    val weChatListLiveData = MutableLiveData<BaseRsp<ArticleModel>>()


    fun getWeChatList(cid: Int, index: Int) {
        DataManager.instance.getWeChatList(cid, index).execute(BaseObserver(weChatListLiveData,loadState))
    }
}