package com.duzzi.kotlin.wanandroid.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.viewpager.widget.ViewPager
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.duzzi.kotlin.wanandroid.R
import com.duzzi.kotlin.wanandroid.databinding.ActivityMainBinding
import com.duzzi.kotlin.wanandroid.home.ui.TabHomeFragment
import com.duzzi.kotlin.wanandroid.mine.TabMineFragment
import com.duzzi.kotlin.wanandroid.project.TabProjectFragment
import com.duzzi.kotlin.wanandroid.qa.TabQuestionAnswerFragment
import com.duzzi.kotlin.wanandroid.wechat.TabWeChatFragment
import com.duzzi.ui.base.BaseActivity
import com.duzzi.ui.utils.StatusBarUtil

class MainActivity : BaseActivity(), BottomNavigationBar.OnTabSelectedListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initViewPager()
    }

    override fun setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null)
        StatusBarUtil.setLightMode(this)
    }


    private fun initViewPager() {
        binding.bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED)
            .addItem(BottomNavigationItem(R.drawable.ic_home_select, "首页"))
            .addItem(BottomNavigationItem(R.drawable.ic_project_select, "项目"))
            .addItem(BottomNavigationItem(R.drawable.ic_hierarchy_select, "公众号"))
            .addItem(BottomNavigationItem(R.drawable.ic_nav_select, "问答"))
            .addItem(BottomNavigationItem(R.drawable.ic_article, "我的"))
            .setBarBackgroundColor(R.color.white)
            .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
            .setActiveColor(R.color.colorPrimary)
            .setInActiveColor(R.color.secondaryText)
            .initialise()
        binding.bottomNavigationBar.setTabSelectedListener(this)
        val list: ArrayList<Fragment> = ArrayList(5)
        list.add(TabHomeFragment.newInstance())
        list.add(TabProjectFragment.newInstance())
        list.add(TabWeChatFragment.newInstance())
        list.add(TabQuestionAnswerFragment.newInstance())
        list.add(TabMineFragment.newInstance())
        binding.mainViewPager.offscreenPageLimit = 5
        binding.mainViewPager.adapter = FragmentAdapter(list, supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        binding.mainViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                binding.bottomNavigationBar.selectTab(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    override fun onTabSelected(position: Int) {
        binding.mainViewPager.currentItem = position
    }

    override fun onTabUnselected(position: Int) {

    }

    override fun onTabReselected(position: Int) {

    }
}