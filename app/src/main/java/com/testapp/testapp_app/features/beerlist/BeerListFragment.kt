package com.testapp.testapp_app.features.beerlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.testapp.testapp_app.R
import com.testapp.testapp_app.models.BeerBean
import com.testapp.testapp_app.setup.BaseFragment
import kotlinx.android.synthetic.main.fragment_beer_style_list.*
import org.koin.android.ext.android.inject

class BeerListFragment: BaseFragment(), BeerAdapter.OnItemListDelegate {
    //region Vars
    private val viewModel: BeerListViewModel by inject()
    private lateinit var adapter: BeerAdapter

    private val selectedStyle by lazy {
        arguments?.let {
            BeerListFragmentArgs.fromBundle(it).styleSelected
        } ?: 0
    }
    //endregion Vars

    //region Override Methods
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_beer_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
        initRecyclerAdapter()
        observers()
    }

    override fun onItemClicked(item: BeerBean) {
        val action = BeerListFragmentDirections.actionBeerLisToDetail(item)
        findNavController().navigate(action)
    }
    //endregion Override Methods

    //region Methods
    private fun initRecyclerAdapter() {
        recyclerView?.layoutManager = GridLayoutManager(context, 3)
        context?.let { context ->
            adapter = BeerAdapter(context, viewModel.beerList.value ?: mutableListOf(), this)
        }
        recyclerView?.adapter = adapter
    }

    private fun setUpViews() {
        viewModel.getBeersListByStyleRequest(selectedStyle, 1)
        refreshRecycler?.setOnRefreshListener {
            viewModel.getBeersListByStyleRequest(selectedStyle, 1)
        }
    }

    //region Observers
    private fun observers() {
        isLoadingObserver()
        servicesListObserver()
        onErrorObserver()
        isEmptyObserver()
    }

    private fun servicesListObserver() {
        viewModel.beerList.observe(viewLifecycleOwner, Observer {
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
        private val LOGTAG: String = BeerListFragment::class.java.simpleName
    }
}