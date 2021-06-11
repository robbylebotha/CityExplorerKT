package it.ads.app.cityexplorerkt.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import it.ads.app.city_explorer_sdk.CityExplore
import it.ads.app.city_explorer_sdk.interfaces.CityExploreCallBack
import it.ads.app.cityexplorerkt.models.Shop
import java.lang.Exception

object ShopRepository{
    const val TAG = "city_explorer_kotlin"

    fun getMutableLiveData(context: Context, mallName: String) : MutableLiveData<ArrayList<Shop>> {

        var mutableLiveData = MutableLiveData<ArrayList<Shop>>()
        val cityExplore = CityExplore(context)
        cityExplore.getShops(mallName, object : CityExploreCallBack{
            override fun onSuccess(list: ArrayList<String>?) {

                var shopNameList = ArrayList<Shop>()
                try {
                    if (list != null) {
                        for(shopname in list){
                            var shop = Shop(shopname, "www.$shopname.co.za")
                            shopNameList.add(shop)
                        }
                    }
                    mutableLiveData.value = shopNameList

                }catch (e: Exception){
                    Log.e(TAG,e.toString())
                }

                Log.i(TAG, "response success from SDK. list: "+list!!.toString())
            }

            override fun onFail(message: String?) {
                Log.e(TAG, "Failed to get shops: $message")
            }

        })

        return mutableLiveData
    }


}