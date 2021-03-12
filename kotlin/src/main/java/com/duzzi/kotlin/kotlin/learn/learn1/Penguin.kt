package com.duzzi.kotlin.kotlin.learn.learn1

class Penguin(age: Int) : Bird(age = age) {

    override fun fly() {
        println("this is penguin fly")
    }

}