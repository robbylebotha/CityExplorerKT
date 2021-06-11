package it.ads.app.cityexplorerkt.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Shop(

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("website")
    val website: String? = null


) : Serializable