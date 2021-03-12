package com.duzzi.kotlin.kotlin.learn.learn2


/**
 * object单例
 */
object DbConfig {
    val host: String = "127.0.0.1"
    val port: Int = 3306
    var username: String = "admin"
    var password: String = "admin"
    override fun toString(): String {
        return "Dbconfig{$host $port $username $password}"
    }
}