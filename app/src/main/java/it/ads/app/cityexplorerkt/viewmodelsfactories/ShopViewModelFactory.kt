package it.ads.app.cityexplorerkt.viewmodelsfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import it.ads.app.cityexplorerkt.viewmodels.ShopViewModel

class ShopViewModelFactory(): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShopViewModel::class.java)){
            return ShopViewModel() as T
        }
        throw IllegalArgumentException ("UnknownViewModel")
    }

}