package com.duzzi.kotlin.wanandroid

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.blankj.utilcode.util.CollectionUtils

class MainAdapter(fragments: ArrayList<Fragment>, fm: FragmentManager, behavior: Int) :
    FragmentPagerAdapter(fm, behavior) {

    private val mFragments = fragments

    override fun getCount(): Int = CollectionUtils.size(mFragments)

    override fun getItem(position: Int): Fragment = mFragments[position]
}