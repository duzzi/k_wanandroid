package com.duzzi.kotlin.kotlin.test

import kotlin.math.pow

/**
 * 1. 两数之和
给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。
你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
你可以按任意顺序返回答案。

示例 1：
输入：nums = [2,7,11,15], target = 9
输出：[0,1]
解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
示例 2：
输入：nums = [3,2,4], target = 6
输出：[1,2]
示例 3：
输入：nums = [3,3], target = 6
输出：[0,1]

提示：

2 <= nums.length <= 103
-109 <= nums[i] <= 109
-109 <= target <= 109
只会存在一个有效答案
 */
fun main() {
    val nums = IntArray(500) { it * 2 }
//    val nums = intArrayOf(2, 7, 11, 100)
    println(nums.contentToString())


    val startTime1 = System.currentTimeMillis()
    println("${twoSum1(nums, 102).contentToString()} ${System.currentTimeMillis() - startTime1}ms")
    val startTime2 = System.currentTimeMillis()
    println("${twoSum2(nums, 102).contentToString()} ${System.currentTimeMillis() - startTime2}ms")


}

fun twoSum1(nums: IntArray, target: Int): IntArray {
    val array = intArrayOf(-1, -1)
    val max = 10.0.pow(9)
    val min = -max

    if (target < min || target > max) return array
    for (num in nums) if (target < min || target > max) return array
    if (nums.size < 2 || nums.size > 10.0.pow(3.0)) return array
    for (i in nums.indices) {
        for (j in i + 1 until nums.size) {
            if (nums[i] + nums[j] == target) {
                return intArrayOf(i, j)
            }
        }
    }
    return intArrayOf(-1, -1)
}

fun twoSum2(nums: IntArray, target: Int): IntArray {
    val array = intArrayOf(-1, -1)
    val max = 10.0.pow(9).toLong()
    val min = -max
    if (target !in min..max) return array
    for (num in nums) if (target !in min..max) return array
    if (nums.size < 2 || nums.size > 10.0.pow(3.0)) return array


    val map = HashMap<Int, Int>()
    for (i in 0..(nums.size - 2)) {
        val key = target - nums[i]
        if (map.containsKey(key)) {
            return intArrayOf(map[key]!!, i)
        }
        map[nums[i]] = i
    }
    return intArrayOf(-1, -1)
}
