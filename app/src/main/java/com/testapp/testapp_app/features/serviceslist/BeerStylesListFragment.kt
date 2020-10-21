package com.testapp.testapp_app.features.serviceslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.testapp.testapp_app.R
import com.testapp.testapp_app.models.BeerStyleBean
import com.testapp.testapp_app.setup.BaseFragment
import kotlinx.android.synthetic.main.fragment_services_list.*
import org.koin.android.ext.android.inject

class BeerStylesListFragment: BaseFragment(), BeerStyleAdapter.OnItemListDelegate {
    //region Vars
    private val viewModel: BeerStylesListViewModel by inject()
    private lateinit var adapter: BeerStyleAdapter
    //endregion Vars

    //region Override Methods
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_services_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
        initRecyclerAdapter()
        observers()
    }

    override fun onItemClicked(item: BeerStyleBean) {
        //val action = ServicesListFragmentDirections.actionServicesListFragmentToServiceDetailFragment(item)
        //findNavController().navigate(action)
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
        viewModel.getServicesRequest()
        refreshRecycler?.setOnRefreshListener {
            viewModel.getServicesRequest()
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