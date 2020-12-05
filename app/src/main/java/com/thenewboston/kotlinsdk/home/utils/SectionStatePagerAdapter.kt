package com.thenewboston.kotlinsdk.home.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class SectionStatePagerAdapter(
    fm: FragmentManager,
    private val frags: ArrayList<Fragment>,
    private val titles: ArrayList<String>
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int) = frags[position]
    override fun getCount() = frags.size
    override fun getPageTitle(position: Int) = titles[position]
}
