package com.testapp.testapp_app.features.stylesbeerlist

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.testapp.testapp_app.R
import com.testapp.testapp_app.features.beerlist.BeerListFragmentDirections
import com.testapp.testapp_app.models.BeerBean
import com.testapp.testapp_app.models.BeerStyleBean
import com.testapp.testapp_app.setup.BaseFragment
import kotlinx.android.synthetic.main.dialog_random_beer.*
import kotlinx.android.synthetic.main.fragment_beer_style_list.*
import org.koin.android.ext.android.inject

class BeerStylesListFragment: BaseFragment(), BeerStyleAdapter.OnItemListDelegate {
    //region Vars
    private val viewModel: BeerStylesListViewModel by inject()
    private lateinit var adapter: BeerStyleAdapter
    //endregion Vars

    //region Override Methods
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_beer_style_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
        initRecyclerAdapter()
        observers()
    }

    override fun onItemClicked(item: BeerStyleBean) {
        val action = BeerStylesListFragmentDirections.actionStylesToBeersList(item.id)
        findNavController().navigate(action)
    }
    //endregion Override Methods

    //region Methods
    private fun initRecyclerAdapter() {
        recyclerView?.layoutManager = LinearLayoutManager(context)
        context?.let { context ->
            adapter = BeerStyleAdapter(context, viewModel.beerStylesList.value ?: mutableListOf(), this)
        }
        recyclerView?.adapter = adapter
    }

    private fun setUpViews() {
        doInitialRequest()
        refreshRecycler?.setOnRefreshListener {
            viewModel.getBeerStylesListRequest()
        }
    }

    private fun doInitialRequest() {
        if(viewModel.needRequestRandomBeer()) {
            showProgressDialog()
            viewModel.randomBeer.observe(viewLifecycleOwner, randomBeerObserver)
            viewModel.onErrorRandomBeer.observe(viewLifecycleOwner, onErrorRandomBeerObserver)
            viewModel.getRandomBeer()
        } else {
            viewModel.getBeerStylesListRequest()
        }
    }

    private fun showDialogRandomBeer(randomBeer: BeerBean) {
        viewModel.randomBeer.removeObserver(randomBeerObserver)
        viewModel.onErrorRandomBeer.removeObserver(onErrorRandomBeerObserver)
        context?.let {
            Dialog(it).apply {
                setCancelable(true)
                setContentView(R.layout.dialog_random_beer)

                fillDialogUI(this, randomBeer)
                setDialogButtonsActions(this, randomBeer)

                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                show()
            }
        }
    }

    private fun setDialogButtonsActions(dialog: Dialog, randomBeer: BeerBean) {
        dialog.buttonSeeDetails?.setOnClickListener {
            val action = BeerStylesListFragmentDirections.actionStylesToBeerDetail(randomBeer)
            findNavController().navigate(action)
            dialog.cancel()
        }
        dialog.buttonClose?.setOnClickListener {
            dialog.cancel()
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun fillDialogUI(dialog: Dialog, randomBeer: BeerBean) {
        dialog.textRBName?.text = randomBeer.name
        if(!randomBeer.abv.isNullOrBlank())
            dialog.textRBabv?.text = getString(R.string.abv_percent_random_beer, randomBeer.abv)

        val urlImage = randomBeer.images?.large ?: getString(R.string.image_beer_placeholder_url)
        Picasso.get().load(urlImage).into( dialog.imageRandomBeer)
    }

    //region Observers
    private fun observers() {
        isLoadingObserver()
        beerStylesListObserver()
        onErrorObserver()
        isEmptyObserver()
    }

    private val randomBeerObserver = Observer<BeerBean> {
        hideProgressDialog()
        showDialogRandomBeer(it)
        viewModel.getBeerStylesListRequest()
    }

    private val onErrorRandomBeerObserver = Observer<Boolean> { onError ->
        if(onError == true) {
            view?.let {view ->
                showError(getString(R.string.network_error), view)
            }
        }
    }

    private fun beerStylesListObserver() {
        viewModel.beerStylesList.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })
    }

    private fun isLoadingObserver() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            refreshRecycler?.isRefreshing = it
        })
    }

    private fun onErrorObserver() {
        viewModel.onError.observe(viewLifecycleOwner, Observer { onError ->
            if(onError == true) {
                view?.let {view ->
                    showError(getString(R.string.network_error), view)
                }
            }
        })
    }

    private fun isEmptyObserver() {
        viewModel.isEmpty.observe(viewLifecycleOwner, Observer {
            when(it) {
                true -> textNotContent?.visibility = View.VISIBLE
                false -> textNotContent?.visibility = View.GONE
            }
        })
    }
    //endregion Observers
    //endregion Methods

    companion object {
        private val LOGTAG: String = BeerStylesListFragment::class.java.simpleName
    }
}
