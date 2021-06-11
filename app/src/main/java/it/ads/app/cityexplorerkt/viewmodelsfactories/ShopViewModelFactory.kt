package it.ads.app.cityexplorerkt.viewmodelsfactories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import it.ads.app.cityexplorerkt.viewmodels.ShopViewModel

class ShopViewModelFactory(private val context: Context, private val mallName: String) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShopViewModel(context, mallName) as T
    }

}