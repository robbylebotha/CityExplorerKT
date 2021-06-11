package it.ads.app.cityexplorerkt.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.ads.app.cityexplorerkt.R
import it.ads.app.cityexplorerkt.models.Shop
import java.lang.Exception

class ShopsAdapter(private var context: Context, private var list: MutableList<Shop>) : RecyclerView.Adapter<ShopsAdapter.MyViewHolder>() {
    private val TAG = "city_explorer_kotlin"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var inflater = LayoutInflater.from(context)
        var view: View = inflater.inflate(R.layout.shop_row,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            var shop = list.get(position)
            holder.name?.text = shop.name
            holder.website?.text = shop.website?.toLowerCase()
    }

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view){

        var name: TextView? = null
        var website: TextView? = null

        init {
            name = view.findViewById(R.id.shop_name)
            website = view.findViewById(R.id.show_website)
        }

    }

}