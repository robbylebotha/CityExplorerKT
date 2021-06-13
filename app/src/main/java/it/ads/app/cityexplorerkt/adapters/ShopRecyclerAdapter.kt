package it.ads.app.cityexplorerkt.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import it.ads.app.cityexplorerkt.R
import it.ads.app.cityexplorerkt.models.Shop
import it.ads.app.cityexplorerkt.viewmodels.ShopViewModel
import kotlinx.android.synthetic.main.shop_row.view.*

class ShopRecyclerAdapter(val viewModel: ShopViewModel, val arrayList: ArrayList<Shop>, val context: Context): RecyclerView.Adapter<ShopRecyclerAdapter.NotesViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopRecyclerAdapter.NotesViewHolder {
        var root = LayoutInflater.from(parent.context).inflate(R.layout.shop_row,parent,false)
        return NotesViewHolder(root)
    }

    override fun onBindViewHolder(holder: ShopRecyclerAdapter.NotesViewHolder, position: Int) {
        holder.bind(arrayList.get(position))
    }

    override fun getItemCount(): Int {

        return arrayList.size
    }


    inner  class NotesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(shop: Shop){
            view.shop_name.text = shop.name
//            binding.delete.setOnClickListener {
//                viewModel.remove(shop)
//                notifyItemRemoved(arrayList.indexOf(shop))
//            }
        }

    }

}