package com.testapp.testapp_app.features.beerlist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.testapp.testapp_app.R
import com.testapp.testapp_app.models.BeerBean
import kotlinx.android.synthetic.main.cell_beer_item.view.*

class BeerAdapter(private val context: Context,
                       private var list: MutableList<BeerBean>,
                       private val listener: OnItemListDelegate): RecyclerView.Adapter<BeerAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.cell_beer_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int  = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item) {
            listener.onItemClicked(item)
        }
    }

    interface OnItemListDelegate {
        fun onItemClicked(item: BeerBean)
    }

    fun update(data: MutableList<BeerBean>) {
        if(!data.isNullOrEmpty()) {
            list = data
            notifyDataSetChanged()
        }
    }

    //region ViewHolder
    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var item: BeerBean
        private lateinit var listener: (BeerBean) -> Unit

        init {
            view.setOnClickListener {
                listener(this.item)
            }
        }

        fun bind(item: BeerBean, listener: (BeerBean) -> Unit) {
            this.item = item
            this.listener = listener

            fillCellView()
        }

        @SuppressLint("StringFormatInvalid")
        private fun fillCellView() {
            view.textName?.text = item.name
            if(!item.abv.isNullOrBlank()) {
                view.textBeerAbv?.text = view.context.getString(R.string.abv_percent_one, item.abv)
            } else {
                view.textBeerAbv?.text = ""
            }
            Picasso.get().load(item.images?.medium).placeholder(R.drawable.ic_launcher_foreground).into(view.imageBeer)
        }
    }
    //endregion ViewHolder
}