package com.duzzi.kotlin.wanandroid.wechat

import androidx.lifecycle.MutableLiveData
import com.duzzi.kotlin.wanandroid.base.BaseObserver
import com.duzzi.kotlin.wanandroid.base.execute
import com.duzzi.sdk.core.DataManager
import com.duzzi.sdk.core.bean.base.BaseRsp
import com.duzzi.sdk.core.bean.base.BaseViewModel
import com.duzzi.sdk.core.bean.data.Category

class WeChatViewModel : BaseViewModel() {
    val weChatCategoryLiveData = MutableLiveData<BaseRsp<List<Category>>>()


    fun getWeChatCategory() {
        DataManager.instance.getWeChatCategory().execute(BaseObserver(weChatCategoryLiveData))
    }
}