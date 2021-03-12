package com.duzzi.sdk.core.bean.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.duzzi.sdk.core.load.LoadState

open class BaseViewModel : ViewModel() {

    val loadState by lazy { MutableLiveData<LoadState>() }


    open fun destroy() {

    }

}