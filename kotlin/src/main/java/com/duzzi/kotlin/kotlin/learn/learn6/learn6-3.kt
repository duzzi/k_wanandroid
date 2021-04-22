package com.duzzi.kotlin.kotlin.learn.learn6

import kotlinx.coroutines.runBlocking
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

fun main() = runBlocking {
    val shop = Shop()
    val lock = ReentrantLock()
    lock.withLock {
        shop.buyGood3(2)
    }
    println()
}




class Shop {
    val goods = hashMapOf<Long, Int>()

    init {
        goods[1] = 111
        goods[2] = 222
    }

    @Synchronized
    fun buyGoods(id: Long) {
        val stock = goods.getValue(id)
        goods[id] = stock - 1
    }

    fun buyGoods2(id: Long) {
        synchronized(this) {
            val stock = goods.getValue(id)
            goods[id] = stock - 1
        }
    }

    fun buyGood3(id: Long) {
        val stock = goods.getValue(id)
        goods[id] = stock - 1
    }
}