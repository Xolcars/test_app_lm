package com.testapp.testapp_app.features.beerdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.testapp.testapp_app.R
import com.testapp.testapp_app.features.beerlist.BeerListFragmentArgs
import com.testapp.testapp_app.models.BeerBean
import com.testapp.testapp_app.setup.BaseFragment
import kotlinx.android.synthetic.main.fragment_beer_detail.*
import org.koin.android.ext.android.inject

class BeerDetailFragment: BaseFragment() {
    //region Vars
    private val viewModel: BeerDetailViewModel by inject()
    private lateinit var viewPagerAdapter: BeerDetailViewPagerAdapter

    private val selectedBeer by lazy {
        arguments?.let {
            BeerDetailFragmentArgs.fromBundle(it).selectedBeer
        }
    }
    //endregion Vars

    //region Override Methods
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_beer_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupViewModel()

    }
    //endregion Override Methods

    //region Methods
    private fun setupViewPager() {
        viewPagerAdapter = BeerDetailViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "Testing $position"
        }.attach()
    }

    private fun setupViewModel() {
        selectedBeer?.let {
            viewModel.setBeerItem(it)
        }
    }
    //endregion Methods

    companion object {
        private val LOGTAG: String = BeerDetailFragment::class.java.simpleName
    }
}