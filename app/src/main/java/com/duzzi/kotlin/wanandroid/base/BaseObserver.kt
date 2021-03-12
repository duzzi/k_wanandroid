package com.duzzi.kotlin.wanandroid.base

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.duzzi.sdk.core.bean.base.BaseRsp
import com.duzzi.sdk.core.load.LoadState
import com.duzzi.sdk.core.load.StateType
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class BaseObserver<T : BaseRsp<*>>(
    private val liveData: MutableLiveData<T>,
    private val loadState: MutableLiveData<LoadState>
) : Observer<T> {
    private lateinit var disposable: Disposable
    override fun onSubscribe(d: Disposable?) {
        disposable = d!!
    }

    override fun onError(e: Throwable?) {
        LogUtils.w(e?.message)
        loadState.postValue(LoadState(StateType.ERROR,e?.message))
    }

    override fun onComplete() {

    }

    override fun onNext(rsp: T) {
        LogUtils.v(rsp.toString())
        if (rsp.errorCode == 0) {
            loadState.postValue(LoadState(StateType.SUCCESS))
            liveData.postValue(rsp)
        } else {
            loadState.postValue(LoadState(StateType.ERROR,rsp.errorMsg))
        }
    }


}