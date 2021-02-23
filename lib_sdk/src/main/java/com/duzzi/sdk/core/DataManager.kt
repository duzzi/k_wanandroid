package com.duzzi.sdk.core

import com.duzzi.sdk.core.bean.base.BaseRsp
import com.duzzi.sdk.core.bean.data.ArticleModel
import com.duzzi.sdk.core.bean.data.BannerItem
import com.duzzi.sdk.core.bean.data.Category
import com.duzzi.sdk.core.http.ApiService
import com.duzzi.sdk.core.http.HttpHelper
import io.reactivex.rxjava3.core.Observable

class DataManager private constructor() : ApiService {

    companion object {
        val instance: DataManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DataManager()
        }
    }


    private fun getApiService() = HttpHelper.getInstance().service

    override fun getBanner(): Observable<BaseRsp<List<BannerItem>>> {
        return getApiService().getBanner()
    }

    override fun getHomeArticles(pageIndex: Int): Observable<BaseRsp<ArticleModel>> {
        return getApiService().getHomeArticles(pageIndex)
    }

    override fun getProjectCategory(): Observable<BaseRsp<List<Category>>> {
        return getApiService().getProjectCategory()
    }

    override fun getProjectList(pageIndex: Int, cid: Int): Observable<BaseRsp<ArticleModel>> {
        return getApiService().getProjectList(pageIndex,cid)
    }

    override fun getWeChatCategory(): Observable<BaseRsp<List<Category>>> {
        return getApiService().getWeChatCategory()
    }

    override fun getWeChatList(cid: Int, pageIndex: Int): Observable<BaseRsp<ArticleModel>> {
        return getApiService().getWeChatList(cid,pageIndex)
    }

    override fun getQuestionAnswer(pageIndex: Int): Observable<BaseRsp<ArticleModel>> {
        return getApiService().getQuestionAnswer(pageIndex)
    }

}