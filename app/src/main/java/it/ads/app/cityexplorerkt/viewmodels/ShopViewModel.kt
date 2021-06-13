package it.ads.app.cityexplorerkt.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import it.ads.app.cityexplorerkt.interfaces.CallBack
import it.ads.app.cityexplorerkt.models.Shop
import it.ads.app.cityexplorerkt.repositories.ShopRepository

class ShopViewModel: ViewModel() {
    var mutableList = MutableLiveData<ArrayList<Shop>>()
    var tempList = arrayListOf<Shop>()

    fun add(shop: Shop){
        tempList.add(shop)
        mutableList.value = tempList
    }

    fun getAllShops(context: Context, cityName: String){

    }

    fun clear(){
        tempList.clear()
    }

}