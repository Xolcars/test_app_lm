package com.testapp.testapp_app.features.beerdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.testapp.testapp_app.R
import com.testapp.testapp_app.features.beerlist.BeerListFragmentArgs
import com.testapp.testapp_app.setup.BaseFragment
import org.koin.android.ext.android.inject

class BeerDetailFragment: BaseFragment() {
    //region Vars
    private val viewModel: BeerDetailViewModel by inject()

    private val selectedBeer by lazy {
        arguments?.let {
            BeerDetailFragmentArgs.fromBundle(it).selectedBeer
        } ?: 0
    }
    //endregion Vars

    //region Override Methods
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_beer_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    //endregion Override Methods

    //region Methods
    //endregion Methods

    companion object {
        private val LOGTAG: String = BeerDetailFragment::class.java.simpleName
    }
}