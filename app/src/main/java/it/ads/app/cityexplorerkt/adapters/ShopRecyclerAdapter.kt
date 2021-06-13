package it.ads.app.cityexplorerkt.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import it.ads.app.cityexplorerkt.R
import it.ads.app.cityexplorerkt.models.Shop
import it.ads.app.cityexplorerkt.viewmodels.ShopViewModel
import kotlinx.android.synthetic.main.shop_row.view.*

class ShopRecyclerAdapter(val viewModel: ShopViewModel, val arrayList: ArrayList<Shop>, val context: Context): RecyclerView.Adapter<ShopRecyclerAdapter.ShopViewHolder>() {
    private var lastPosition = -1
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopRecyclerAdapter.ShopViewHolder {
        var root = LayoutInflater.from(parent.context).inflate(R.layout.shop_row,parent,false)
        return ShopViewHolder(root)
    }

    override fun onBindViewHolder(holder: ShopRecyclerAdapter.ShopViewHolder, position: Int) {
        holder.bind(arrayList.get(position))
        //some custom animation because lists are boring.
        val animation = AnimationUtils.loadAnimation(
            context,
            if (position > lastPosition) R.anim.up_from_bottom else R.anim.down_from_top
        )
        holder.itemView.startAnimation(animation)
        lastPosition = position

        holder.itemView.setOnClickListener(View.OnClickListener {
            //direct user to shop just for added fun and practice
            val gmmIntentUri =
                Uri.parse("geo:0,0?q= ${holder.itemView.shop_name.text}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            context.startActivity(mapIntent)

        })
    }

    override fun getItemCount(): Int {

        return arrayList.size
    }


    inner  class ShopViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(shop: Shop){
            view.shop_name.text = shop.name
            view.show_website.text = shop.website?.toLowerCase()
        }

    }

}