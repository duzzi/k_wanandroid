package com.duzzi.kotlin.wanandroid.project

import androidx.lifecycle.MutableLiveData
import com.duzzi.kotlin.wanandroid.base.BaseObserver
import com.duzzi.kotlin.wanandroid.base.execute
import com.duzzi.kotlin.wanandroid.home.viewmodel.ArticleViewModel
import com.duzzi.sdk.core.DataManager
import com.duzzi.sdk.core.bean.base.BaseRsp
import com.duzzi.sdk.core.bean.data.ArticleModel

class ProjectListViewModel : ArticleViewModel() {

    val projectListLiveData = MutableLiveData<BaseRsp<ArticleModel>>()


    fun getProjectList(index: Int, cid: Int) {
        DataManager.instance.getProjectList(index, cid).execute(BaseObserver(projectListLiveData))
    }
}