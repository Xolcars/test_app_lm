package com.testapp.testapp_app.features.stylesbeerlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.testapp.testapp_app.R
import com.testapp.testapp_app.models.BeerStyleBean
import kotlinx.android.synthetic.main.cell_beer_style_item.view.*

class BeerStyleAdapter(private val context: Context,
                       private var list: MutableList<BeerStyleBean>,
                       private val listener: OnItemListDelegate): RecyclerView.Adapter<BeerStyleAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.cell_beer_style_item,parent,false)
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
        fun onItemClicked(item: BeerStyleBean)
    }

    fun update(data: MutableList<BeerStyleBean>) {
        list = data
        notifyDataSetChanged()
    }

    //region ViewHolder
    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var item: BeerStyleBean
        private lateinit var listener: (BeerStyleBean) -> Unit

        init {
            view.setOnClickListener {
                listener(this.item)
            }
        }

        fun bind(item: BeerStyleBean, listener: (BeerStyleBean) -> Unit) {
            this.item = item
            this.listener = listener

            fillCellView()
        }

        private fun fillCellView() {
            view.textName?.text = item.name
            view.textDescription?.text = item.description
            view.textAbv?.text = view.context.getString(R.string.abv_percent, item.abvMin, item.abvMax)
            //Picasso.get().load(item.logo?.url).into(view.imageLogo)
        }
    }
    //endregion ViewHolder
}