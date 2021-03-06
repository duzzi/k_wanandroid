package com.duzzi.kotlin.wanandroid.project

import androidx.lifecycle.MutableLiveData
import com.duzzi.kotlin.wanandroid.base.BaseObserver
import com.duzzi.kotlin.wanandroid.base.execute
import com.duzzi.sdk.core.DataManager
import com.duzzi.sdk.core.bean.base.BaseRsp
import com.duzzi.sdk.core.bean.base.BaseViewModel
import com.duzzi.sdk.core.bean.data.Category

class ProjectViewModel : BaseViewModel() {
    val projectCategoryLiveData = MutableLiveData<BaseRsp<List<Category>>>()


    fun getProjectTree() {
        DataManager.instance.getProjectCategory().execute(BaseObserver(projectCategoryLiveData,loadState))
    }
}