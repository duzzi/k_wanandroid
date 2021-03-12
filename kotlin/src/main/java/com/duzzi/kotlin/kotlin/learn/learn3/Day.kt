package com.duzzi.kotlin.kotlin.learn.learn3

/**
 * 密封类
 */
sealed class Day {
    class MONDAY : Day()
    class SUNDAY : Day()
//    class Friday : Day()


}

fun main() {
    test(Day.MONDAY())
    testWhen(2)
}

fun testWhen(i: Int) {
    when{
        i in 1..10 ->{
            print("i in 1..10 ")
        }
    }
}

fun test(day: Day){
    //不需要写else
    when(day){
        is Day.MONDAY -> println(1111)
        is Day.SUNDAY -> println(2222)
    }
}