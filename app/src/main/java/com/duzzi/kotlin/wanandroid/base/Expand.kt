package com.duzzi.kotlin.wanandroid.base

import android.content.Context
import android.text.Html
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.duzzi.kotlin.wanandroid.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.schedulers.Schedulers


fun <T> Observable<T>.execute(observer: Observer<T>) {
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer)
}

fun String.toHtml(): String {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT).toString()
    } else {
        Html.fromHtml(this).toString()
    }
}

fun ImageView.loadImage(context: Context,
                        path: String,
                        placeholder: Int = R.drawable.default_project_img,
                        error: Int = R.drawable.default_project_img,
                        width: Int = 150,
                        height : Int = 200) {

    val options = RequestOptions()
        .placeholder(placeholder)
        .error(error)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)

    if(width != 0 && height != 0) options.override(width,height)

    Glide.with(context).load(path).apply(options).into(this)
}