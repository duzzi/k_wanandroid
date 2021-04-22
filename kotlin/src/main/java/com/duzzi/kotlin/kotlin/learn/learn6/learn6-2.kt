package com.duzzi.kotlin.kotlin.learn.learn6

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

//fun main() = runBlocking{
//    val measureTimeMillis = measureTimeMillis {
//        //两个协程同步执行
//        val one = searchOne()
//        val two = searchTwo()
//        println("search $one and $two")
//    }
//
//    println(measureTimeMillis)
//}

fun main() = runBlocking{
    val measureTimeMillis = measureTimeMillis {
        //两个子协程并行执行
        val one = async { searchOne() }
        val two = async { searchTwo() }
        println("search ${one.await()} and ${two.await()}")
    }

    println(measureTimeMillis)
}

suspend fun searchOne(): String {
    delay(1000)
    return "one"
}

suspend fun searchTwo(): String {
    delay(1000)
    return "two"
}
