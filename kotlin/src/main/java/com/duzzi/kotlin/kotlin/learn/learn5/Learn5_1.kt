package com.duzzi.kotlin.kotlin.learn.learn5

fun main() {
    //list可重复
    listOf(1, 2, 2, 3, 3, 4, 4, 5, 5).forEach { print("$it ") }
    println()
    //set不可重复
    setOf(1, 2, 2, 3, 3, 4, 4, 5, 5).forEach { print("$it ") }
    println()
    val mapOf = mapOf(1 to 11, 2 to 22, 3 to 33)
    println(mapOf)
    mapOf.forEach { (key, value) -> print("$key:$value ") }
    //可变集合与只读集合
    //可变集合
    val mutableListOf = mutableListOf(1, 2, 3)
    mutableListOf[0] = 0
    println(mutableListOf)
    //只读集合
    var listOf = listOf(1, 2, 3)
//    listOf[0] = 0

    //惰性集合
    val list = listOf(1, 2, 3, 4, 5)
    //效率较低，每个方法都会产生一个新的集合
    println(list.filter { it > 2 }.map { it * 10 }.toList())
    //效率高。kotlin中的序列，每个方法都不会创建额外的集合
    println(list.asSequence().filter { it > 2 }.map { it * 10 }.toList())
    //惰性求值仅仅在需要的时候才会真正去求值
    list.asSequence().filter {
        print("$it ")
        it > 2
    }.map {

        print("#$it ")
        it * 10
    }
    //中间操作(filter map等等)，末端操作(toList)
}