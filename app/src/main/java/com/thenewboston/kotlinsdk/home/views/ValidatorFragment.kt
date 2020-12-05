package com.thenewboston.kotlinsdk.home.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thenewboston.kotlinsdk.*
import com.thenewboston.kotlinsdk.home.utils.SectionStatePagerAdapter
import kotlinx.android.synthetic.main.validator_frag.*

class ValidatorFragment : Fragment() {

    private val pageHeads = arrayListOf(
        OVERVIEW,
        ACCOUNTS,
        BANKS,
        VALIDATORS
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.validator_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val frags = arrayListOf<Fragment>(ValidatorOverviewFragment())
        pageHeads.takeLast(pageHeads.size - 1).map {
            frags.add(ListDisplayFragment(VALIDATOR_PAGE, it))
        }
        viewPagerValidator.adapter = SectionStatePagerAdapter(requireActivity().supportFragmentManager, frags, pageHeads)
        tablayoutValidator.setupWithViewPager(viewPagerValidator)
    }
}
