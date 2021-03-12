package com.duzzi.kotlin.kotlin.learn.learn1

/**
 * 接口，继承
 * 构造方法
 * 修饰符
 * 数据类，pair，triple
 */
fun main() {
    val bird = Bird(color = "yellow", age = 2)
    bird.fly()
    bird.fly1()
    bird.kind()

    val bird1 = Bird(2020)
    bird1.kind()
    //修饰符
    val engine = Engine()
    engine.test()
    Car()
    //数据类
    val brid1 = Bird1(age = 15)
    val brid1copy = brid1.copy(age = 10)
    println(brid1.toString())
    println(brid1copy.toString())
    println(brid1.component1())
    var (a, b, c) = "11,22,33".split(",")
    println(a)
    println(b)
    println(c)
    val bird11 = Bird1(66.0, 11, "red")
    val (a1, b1, c1) = bird11
    println("$a1 $b1 $c1")

    var pair = Pair<String, String>("aa", "bb")
    println("${pair.first} ${pair.second}")

    var triple = Triple<Int, Int, Int>(11, 22, 33)
    println("${triple.first} ${triple.second} ${triple.third}")

}

class Car {

    private var engine: Engine = Engine()
}

private class Engine() {
    /*protected*/ fun test() {
        print("test private")
    }
}

