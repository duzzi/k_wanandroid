package com.duzzi.kotlin.kotlin.learn.learn6

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking{
    val launch = launch {
//        delay(1000)
//        println("world")
        search()
    }
    println("hello ")
//    repeat(100) {
//        println(it)
//    }
//    launch.join()
}

suspend fun search(){
    delay(2000)
    println("world")
}