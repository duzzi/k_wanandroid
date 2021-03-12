package com.duzzi.kotlin.kotlin.learn.learn2

import com.duzzi.kotlin.kotlin.learn.learn1.Prize
import java.util.*

/**
 * object
 */
fun main() {
    //伴生对象
    val prize = Prize(name = "红包", count = 10, type = Prize.TYPE_REDPACK)
    println(Prize.isRedPack(prize))

    val newInstance = Prize.newInstance()
    val newCouponPrize = Prize.newCouponPrize()
    println("${newInstance.toString()} ${newCouponPrize.toString()}")
    DbConfig.username = "lalala"
    println(DbConfig.toString())
    //匿名内部类

    val list = Arrays.asList("red", "green", "blue")
    Collections.sort(list, object : Comparator<String> {
        override fun compare(p0: String?, p1: String?): Int {
            return when {
                p0 == null -> -1
                p1 == null -> 1
                else -> p0.compareTo(p1)
            }
        }
    })
//    list.sortWith(Comparator { p0, p1 ->
//        when {
//            p0 == null -> -1
//            p1 == null -> 1
//            else -> p0.compareTo(p1)
//        }
//    })
        println(list)

    }