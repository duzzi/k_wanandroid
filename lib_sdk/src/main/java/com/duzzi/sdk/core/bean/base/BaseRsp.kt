package com.duzzi.sdk.core.bean.base

open class BaseRsp<T>(
    val `data`: T,
    val errorCode: Int,
    val errorMsg: String


) {
    override fun toString(): String {
        return "BaseRsp(`data`=$`data`, errorCode=$errorCode, errorMsg='$errorMsg')"
    }
}