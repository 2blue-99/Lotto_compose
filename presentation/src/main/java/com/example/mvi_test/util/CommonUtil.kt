package com.example.mvi_test.util

import kotlin.random.Random

object CommonUtil {
    fun makeLotto(): List<List<Int>>{
        val allList = mutableListOf<List<Int>>()
        repeat(5){
            val list = mutableListOf<Int>()
            repeat(7){
                val number = Random.nextInt(1, 46)
                list.add(number)
            }
            list.sort()
            allList.add(list)
        }
        return allList
    }

    fun String.stringToLong() = this.map { it.code }.sum().toLong()
}