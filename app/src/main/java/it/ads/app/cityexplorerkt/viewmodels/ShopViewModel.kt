package it.ads.app.cityexplorerkt.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import it.ads.app.cityexplorerkt.models.Shop
import it.ads.app.cityexplorerkt.repositories.ShopRepository

class ShopViewModel(private val context: Context, private val mallName: String) : ViewModel() {

    private var listData = MutableLiveData<ArrayList<Shop>>()
    val TAG = "city_explorer_kotlin"

    init {
        val shopRepository : ShopRepository by lazy {
            ShopRepository
        }

        listData = shopRepository.getMutableLiveData(context, mallName)
    }

    fun getData() : MutableLiveData<ArrayList<Shop>>{
        Log.i(TAG, " getData() : MutableLiveData<ArrayList<Shop>>");
        return listData
    }
}