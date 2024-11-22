package com.example.gcceolinteractivepaper2.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ExamTypeTableViewPagerAdapter(
    private val supportFragmentManager: FragmentManager,
    private val tabFragments: ArrayList<Fragment>,
    private val tabTitles: List<String?>
) : FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getCount(): Int {
        return tabFragments.size
    }

    override fun getItem(position: Int): Fragment {
        return tabFragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }
}