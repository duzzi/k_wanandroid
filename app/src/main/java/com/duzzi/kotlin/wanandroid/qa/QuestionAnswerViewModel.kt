package com.duzzi.kotlin.wanandroid.qa

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.duzzi.kotlin.wanandroid.base.BaseObserver
import com.duzzi.kotlin.wanandroid.base.execute
import com.duzzi.kotlin.wanandroid.home.viewmodel.ArticleViewModel
import com.duzzi.sdk.core.DataManager
import com.duzzi.sdk.core.bean.base.BaseRsp
import com.duzzi.sdk.core.bean.data.ArticleModel

class QuestionAnswerViewModel : ArticleViewModel() {

    val liveData = MutableLiveData<BaseRsp<ArticleModel>>()

    fun getQuestionAnswer(pageIndex: Int){
        LogUtils.i()
        DataManager.instance.getQuestionAnswer(pageIndex).execute(BaseObserver(liveData))
    }

}