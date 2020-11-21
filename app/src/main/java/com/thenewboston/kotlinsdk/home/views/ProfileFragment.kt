package com.thenewboston.kotlinsdk.home.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thenewboston.kotlinsdk.*
import com.thenewboston.kotlinsdk.home.utils.SectionStatePagerAdapter
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment() {

    private val pageHeads = arrayListOf(
        OVERVIEW,
        TRANSACTIONS
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val frags = arrayListOf(ProfileOverviewFragment(), ListDisplayFragment(PROFILE_PAGE, TRANSACTIONS))
        viewPagerProfile.adapter = SectionStatePagerAdapter(requireActivity().supportFragmentManager, frags, pageHeads)
        viewPagerProfile.offscreenPageLimit = 0
        tablayoutProfile.setupWithViewPager(viewPagerProfile)
    }
}
