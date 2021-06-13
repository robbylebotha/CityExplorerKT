package it.ads.app.cityexplorerkt.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import it.ads.app.cityexplorerkt.models.Shop

class ShopViewModel: ViewModel() {
    var mutableList = MutableLiveData<ArrayList<Shop>>()
    var tempList = arrayListOf<Shop>()

    fun add(shop: Shop){
        tempList.add(shop)
        mutableList.value = tempList
    }

    fun clear(){
        tempList.clear()
    }

}