package com.testapp.testapp_app.features.beerdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.testapp.testapp_app.R
import com.testapp.testapp_app.models.BeerBean
import kotlinx.android.synthetic.main.pager_beer_info.*
import org.koin.android.ext.android.inject

class BeerDetailViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = BeerInfoFragment()
        fragment.arguments = Bundle().apply {
            putInt(TAB_SELECTED, position)
        }
        return fragment
    }
}

class BeerInfoFragment : Fragment() {
    private val viewModel: BeerDetailViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.pager_beer_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val beerItem = viewModel.beerItem.value
        var tabSelected = 0
        arguments?.takeIf { it.containsKey(TAB_SELECTED) }?.apply {
            tabSelected = getInt(TAB_SELECTED)
        }
        beerItem?.let {
            fillUI(tabSelected, it)
        }
    }

    private fun fillUI(tabSelected: Int, beerItem: BeerBean) {
        when(tabSelected) {
            TabSelectedPosition.INFO_TAB.value -> {
                fillInfoTabTexts(beerItem)
            }
            TabSelectedPosition.MORE_TAB.value -> {
                fillMoreTabTexts(beerItem)
            }
        }
    }

    private fun fillInfoTabTexts(beerItem: BeerBean) {
        text1?.text = beerItem.style?.name ?: ""
        text2?.text = beerItem.description ?: ""
    }

    private fun fillMoreTabTexts(beerItem: BeerBean) {

        if(beerItem.isRetired) {
            text1?.text = getString(R.string.beer_retired)
        } else {
            text1?.text = getString(R.string.beer_not_retired)
        }
        text2?.text = getString(R.string.food_pairing, beerItem.foodPairings ?: getString(R.string.what_you_want))
    }

}

enum class TabSelectedPosition(val value: Int) {
    INFO_TAB(0),
    MORE_TAB(1)
}

private const val TAB_SELECTED = "TAB_SELECTED"