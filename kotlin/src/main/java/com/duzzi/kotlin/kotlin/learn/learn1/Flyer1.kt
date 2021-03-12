package com.duzzi.kotlin.kotlin.learn.learn1

interface Flyer1 {

    //kotlin接口中定义属性需要通过get实现
    val name: String
        get() = "gird"
    //
    val name1:String

    fun fly1(){
        println("this is kotlin method")
    }


}