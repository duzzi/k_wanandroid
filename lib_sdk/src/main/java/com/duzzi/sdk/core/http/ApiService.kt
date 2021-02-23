package com.duzzi.sdk.core.http

import com.duzzi.sdk.core.bean.base.BaseRsp
import com.duzzi.sdk.core.bean.data.ArticleModel
import com.duzzi.sdk.core.bean.data.BannerItem
import com.duzzi.sdk.core.bean.data.Category
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("banner/json")
    fun getBanner(): Observable<BaseRsp<List<BannerItem>>>

    @GET("article/list/{pageIndex}/json")
    fun getHomeArticles(@Path("pageIndex") pageIndex: Int): Observable<BaseRsp<ArticleModel>>

    @GET("project/tree/json")
    fun getProjectCategory(): Observable<BaseRsp<List<Category>>>

    @GET("project/list/{pageIndex}/json")
    fun getProjectList(@Path("pageIndex") pageIndex: Int, @Query("cid") cid: Int): Observable<BaseRsp<ArticleModel>>

    @GET("wxarticle/chapters/json ")
    fun getWeChatCategory(): Observable<BaseRsp<List<Category>>>

    @GET("wxarticle/list/{cid}/{pageIndex}/json")
    fun getWeChatList(@Path("cid") cid: Int, @Path("pageIndex") pageIndex: Int): Observable<BaseRsp<ArticleModel>>

    @GET("wenda/list/{pageIndex}/json")
    fun getQuestionAnswer(@Path("pageIndex") pageIndex: Int): Observable<BaseRsp<ArticleModel>>


}

