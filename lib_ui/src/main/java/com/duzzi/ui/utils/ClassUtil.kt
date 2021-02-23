package com.duzzi.ui.utils

import java.lang.reflect.ParameterizedType

object ClassUtil {

    fun <T> getClass(t: Any, genericIndex: Int): Class<T> {
        // 通过反射 获取父类泛型 (T) 对应 Class类
        var type = (t.javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[genericIndex]
        return type as Class<T>
    }
}