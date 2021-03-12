package com.duzzi.kotlin.kotlin.learn.learn1

class Prize(val name: String, val count: Int, val type: Int) {
    companion object {
        val TYPE_REDPACK = 0
        val TYPE_COUPON = 1

        fun isRedPack(prize: Prize): Boolean {
            return prize.type == TYPE_REDPACK
        }

        fun newInstance(): Prize = Prize("default", 100, TYPE_REDPACK)
        fun newCouponPrize(): Prize = Prize("coupon", 111, TYPE_COUPON)

    }

    override fun toString(): String {
        return name
    }
}