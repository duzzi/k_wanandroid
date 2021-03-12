package com.duzzi.kotlin.kotlin.learn.learn1

fun getAgeByBirth(birth: Int): Int {
    return birth-2000
}

//主构造方法
open class Bird(private val color: String = "red", val weight: Double = 100.0, age: Int) : Flyer1, Flyer {
    //    val color: String = "red"
//    val weight: Double = 100.0
    var age: Int


    //从构造方法
    constructor(birth:Int):this(age = getAgeByBirth(birth)){

    }



    //by lazy用于val的初始化
    //lateinit 用于 var的初始化
    val sex by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        if ("red" == color) "male" else "female"
    }

    lateinit var sex1:String

    override fun kind() {
        if ("red" == color) sex1 = "male" else sex1 = "female"
        println(sex1)
    }

    override val name1: String
        get() = "name1"

    override fun fly() {
        super<Flyer>.fly()
    }

    override fun toString(): String {
        return "Bird: $color ${weight}g ${age}岁"
    }

    init {
        //init方法块属于构造方法的一部分
        //构造方法的参数可以在init方法块中调用
        this.age = age
        println("这是第一个init " + toString())
    }

    //可以使用多个init 进行业务分级
    init {
        this.age = age * 2
        println("这是第二个init " + toString())
    }


}