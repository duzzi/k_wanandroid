package com.duzzi.ui.base

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ToastUtils
import com.duzzi.sdk.core.bean.base.BaseViewModel
import com.duzzi.sdk.core.load.LoadState
import com.duzzi.sdk.core.load.StateType
import com.duzzi.ui.loadsir.EmptyCallback
import com.duzzi.ui.loadsir.LoadErrorCallback
import com.duzzi.ui.loadsir.LoadingCallback
import com.kingja.loadsir.callback.SuccessCallback

abstract class LifecycleFragment<VM : BaseViewModel, VB : ViewBinding> : BaseFragment<VM, VB>() {

    private val observer by lazy {
        Observer<LoadState> {
            it.let {
                when (it.type) {
                    StateType.EMPTY -> showEmpty()
                    StateType.SUCCESS -> showSuccess()
                    StateType.LOADING -> showLoading()
                    StateType.ERROR -> showError(it.msg)
                    StateType.NETWORK_ERROR -> showError("网络异常")
                    StateType.TIP -> showTip(it.msg)
                }
            }
        }
    }

    override fun reload() {
        showLoading()
        loadData()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        viewModel.loadState.observe(this, observer)
    }

    private fun showLoading() {
        loadService.showCallback(LoadingCallback::class.java)
    }

    fun showSuccess(){
        loadService.showCallback(SuccessCallback::class.java)
    }

    private fun showEmpty(){
        loadService.showCallback(EmptyCallback::class.java)
    }

    private fun showError(msg : String?){
        if(!TextUtils.isEmpty(msg)){
            ToastUtils.showShort(msg)
        }
        loadService.showCallback(LoadErrorCallback::class.java)
    }

    open fun showTip(msg: String?){
        if(!TextUtils.isEmpty(msg)){
            ToastUtils.showShort(msg)
        }
        loadService.showCallback(SuccessCallback::class.java)
    }




}