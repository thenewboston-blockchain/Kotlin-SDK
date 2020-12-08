package com.thenewboston.kotlinsdk.home.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thenewboston.kotlinsdk.*
import com.thenewboston.kotlinsdk.home.utils.SectionStatePagerAdapter
import kotlinx.android.synthetic.main.bank_fragment.*

class BankFragment : Fragment() {

    private val pageHeads = arrayListOf(
        OVERVIEW,
        ACCOUNTS,
        TRANSACTIONS,
        BLOCKS,
        CONFIRMATION,
        INVALID_BLOCKS,
        BANKS,
        VALIDATORS,
        CONFIRMATION_SERVICES
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bank_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val frags = arrayListOf<Fragment>(BankOverviewFragment())
        pageHeads.takeLast(pageHeads.size - 1).map {
            frags.add(ListDisplayFragment(BANK_PAGE, it))
        }
        viewPagerBank.adapter = SectionStatePagerAdapter(requireActivity().supportFragmentManager, frags, pageHeads)
        tablayoutBank.setupWithViewPager(viewPagerBank)
    }
}
