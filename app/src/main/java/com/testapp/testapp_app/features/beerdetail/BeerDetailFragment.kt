package com.testapp.testapp_app.features.beerdetail

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import com.testapp.testapp_app.R
import com.testapp.testapp_app.setup.BaseFragment
import kotlinx.android.synthetic.main.dialog_big_image.*
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
        fillUI()
        buttonFavSetOnClickListener()
        onImageClickListener()
    }
    //endregion Override Methods

    //region Methods
    private fun setupViewPager() {
        viewPagerAdapter = BeerDetailViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position) {
                TabSelectedPosition.INFO_TAB.value -> {
                    tab.text = getString(R.string.info)
                }
                TabSelectedPosition.MORE_TAB.value -> {
                    tab.text = getString(R.string.more)
                }
            }
        }.attach()
    }

    private fun setupViewModel() {
        selectedBeer?.let {
            viewModel.setBeerItem(it)
        }
    }

    private fun fillUI() {
        titleBeer?.text = selectedBeer?.name
        textAbvBeer?.text = selectedBeer?.abv
        Picasso.get()
            .load(selectedBeer?.images?.large ?: getString(R.string.image_beer_placeholder_url))
            .placeholder(R.drawable.ic_launcher_foreground).into(imageBeer)
        fillButtonUI()
    }

    private fun buttonFavSetOnClickListener() {
        buttonFav?.setOnClickListener {
            selectedBeer?.let {
                when(viewModel.isBeerOnFavs()) {
                    true -> viewModel.removeBeerToFavs(it)
                    false -> viewModel.addBeerToFavs(it)
                }
                fillButtonUI()
            }
        }
    }

    private fun onImageClickListener() {
        imageBeer?.setOnLongClickListener {
            showDialogBigImage()
            true
        }
    }

    private fun fillButtonUI() {
        if(viewModel.isBeerOnFavs()) {
            buttonFav?.text = getString(R.string.remove_to_favs)
        } else {
            buttonFav?.text = getString(R.string.add_to_favs)
        }
    }

    private fun showDialogBigImage() {
        context?.let {
            Dialog(it).apply {
                setCancelable(true)
                setContentView(R.layout.dialog_big_image)

                val urlImage = selectedBeer?.images?.large ?: getString(R.string.image_beer_placeholder_url)
                Picasso.get().load(urlImage).into(imageBig)

                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                show()
            }
        }
    }
    //endregion Methods

    companion object {
        private val LOGTAG: String = BeerDetailFragment::class.java.simpleName
    }
}