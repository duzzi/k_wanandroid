package com.duzzi.ui.loadsir

import com.duzzi.ui.R
import com.kingja.loadsir.callback.Callback

class LoadErrorCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_error
    }
}