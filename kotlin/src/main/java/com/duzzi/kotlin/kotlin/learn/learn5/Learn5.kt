package com.duzzi.kotlin.kotlin.learn.learn5

data class Student(var name: String, val sex: String) {
    override fun toString(): String {
        return "{$name $sex}"
    }
}

fun main() {
    //lambda表达式
    val student = Student(name = "Tom", sex = "male")
    student.let {
        it.name = "let"
        println(it.name)
    }

    student.apply {
        student.name = "apply"
        println(name)
    }

    with(student) {
        student.name = "with"
        println(name)
    }

    val list = listOf(1, 2, 3, 4, 5)
    list.forEach(::print)
    list.forEach { print("$it ") }
//    var map = list.map { it * 10 }
    var map = list.map { it -> it * 10 }
    println(map)
    var filter = list.filter { it % 2 == 0 }
    println(filter)
    var filterNot = list.filterNot { it % 2 == 0 }
    println(filterNot)
    var count = list.count { it % 3 == 0 }
    println(count)
    var sum = list.sum()
    println(sum)
    var sumBy = list.sumBy { it }
    println(sumBy)
    //100为初始值
    val fold = list.fold(100) { a, item -> a + item }
    println(fold)
    //与fold类似，但没有初始值
    val reduce = list.reduce { acc, i -> i + acc }
    println(reduce)


    val student1 = Student("Tom1", "male")
    val student2 = Student("Tom2", "female")
    val student3 = Student("Tom3", "male")
    val student4 = Student("Tom4", "female")
    val student5 = Student("Tom5", "male")

    val students = listOf(student1, student2, student3, student4, student5)

    var groupBy = students.groupBy { it.sex }
    println(groupBy)
}