package com.duzzi.kotlin.kotlin.learn.learn4

class NullTest {
    val id: Long? = null
    val obj: Any? = null
}

fun main() {
    val array0 = arrayOf<String>()
    val array1 = arrayOf(1,2,3,4)
    val array2 = arrayOf<Int>(1,2,3,4)
    val array3 = intArrayOf(1,2,3,4)

    var list = listOf(1, 2, 3, 4, 5)
    var map = list.map { it * 10 }

    println(list)
    println(map)
}