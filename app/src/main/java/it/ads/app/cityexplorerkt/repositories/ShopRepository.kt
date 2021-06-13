package it.ads.app.cityexplorerkt.repositories

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import it.ads.app.city_explorer_sdk.CityExplore
import it.ads.app.city_explorer_sdk.interfaces.CityExploreCallBack
import it.ads.app.cityexplorerkt.interfaces.CallBack
import it.ads.app.cityexplorerkt.models.Shop
import org.jetbrains.anko.toast
import java.lang.Exception

object ShopRepository {

    fun getData(context: Context, cityName: String) : ArrayList<Shop>{
        val TAG = "city_repo"
        var shopNameList = ArrayList<Shop>()

        val cityExplore = CityExplore(context)
        cityExplore.getShopsInCity(cityName, object : CityExploreCallBack {
            override fun onSuccess(list: java.util.ArrayList<String>?) {


                try {
                    if (list != null) {
                        for(shopname in list){
                            val shop = Shop(shopname, "www.$shopname.co.za")
                            shopNameList.add(shop)
                        }
                        Log.i(TAG, "Shop list: $shopNameList")
                    }

                }catch (e: Exception){
                    Log.e(TAG,e.toString())
                }

                Log.i(TAG, "response success from SDK. list: "+list!!.toString())
            }

            override fun onFail(message: String?) {
                Log.e(TAG, "Failed to get shops: $message")
            }

        })


        return shopNameList
    }

}