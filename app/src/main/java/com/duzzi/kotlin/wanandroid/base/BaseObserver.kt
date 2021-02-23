package com.duzzi.kotlin.wanandroid.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.duzzi.sdk.core.bean.base.BaseRsp
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class BaseObserver<T : BaseRsp<*>>(
    private val liveData: MutableLiveData<T>
) : Observer<T> {
    private lateinit var disposable: Disposable
    override fun onSubscribe(d: Disposable?) {
        disposable = d!!
    }

    override fun onError(e: Throwable?) {

    }

    override fun onComplete() {

    }

    override fun onNext(rsp: T) {
        if (rsp.errorCode == 0) {
            liveData.postValue(rsp)
        } else {
            Log.i("TAG", "onNext: " + rsp.errorMsg)
        }
    }


}