package com.duzzi.ui.view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.duzzi.sdk.core.bean.data.Category

class VP2Adapter(fragment: Fragment, private val items: List<Category>, private val fragments: MutableList<Fragment>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}