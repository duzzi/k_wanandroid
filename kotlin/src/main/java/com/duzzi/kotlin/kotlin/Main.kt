package com.duzzi.kotlin.kotlin

import kotlin.math.pow


fun main() {
    val test = Test()
    test.test()
    test.expand()
    test.varargTest(1, 2, 3, 4, 5)
    for (i in 10 downTo 1 step 2) {
        print("$i ")
    }

    println()
    for (i in 1 until 5) {
        print("$i ")
    }
    println(Int.MAX_VALUE)
    //在 Kotlin 中，三个等号 === 表示比较对象地址，两个 == 表示比较两个值大小。
    val a: Int = 100
    val b: Int? = a
    val c: Int? = a
    println(b == c)
    println(b === c)

    println(10.0.pow(3.0))

    println(foo(5))
}

//递归需要声明返回类型
fun foo(n: Int): Int = if (n <= 0) 1 else n * foo(n - 1)



fun Test.expand() {
    println("这个是Test的扩展函数")
}

class Test {

    fun test() {
        fun sum(int1: Int, int2: Int): Int {
            return int1 + int2
        }

        println("这是test自己的函数 ${sum(1,3)}")
    }

    /**
     * 可变参数
     */
    fun varargTest(vararg num: Int) {
        for (i in num) print("$i ")
        println()
    }
}
