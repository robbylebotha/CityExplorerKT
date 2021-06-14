package it.ads.app.cityexplorerkt.activities

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import it.ads.app.city_explorer_sdk.CityExplore
import it.ads.app.city_explorer_sdk.interfaces.CityExploreCallBack
import it.ads.app.city_explorer_sdk.network.CheckNetwork
import it.ads.app.cityexplorerkt.R
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import it.ads.app.cityexplorerkt.adapters.ShopRecyclerAdapter
import it.ads.app.cityexplorerkt.models.Shop
import it.ads.app.cityexplorerkt.viewmodels.ShopViewModel
import it.ads.app.cityexplorerkt.viewmodelsfactories.ShopViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {
    val cityExplorer = CityExplore(this)
    val TAG = "city_explorer_kotlin"
    var progressDialog: ProgressDialog? = null
    //these switches will prevent spinners from automatically selecting first option on load
    var spinnerMallSwitch = 1
    var spinnerCitySwitch = 1
    private var viewManager = LinearLayoutManager(this)
    private lateinit var viewModel: ShopViewModel
    private lateinit var recycler_main: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Build progress dialog
        //TODO stop using depracated progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog!!.setTitle("Please Wait")
        progressDialog!!.setMessage("Loading ...")

        //setup recycle view and model
        recycler_main = findViewById(R.id.recycler_main)
        viewModel = ViewModelProviders.of(this,ShopViewModelFactory()).get(ShopViewModel::class.java)
        initialiseAdapter()


        //check if there is network
        val hasNetwork = CheckNetwork(applicationContext)
        if (hasNetwork.isAvailable){
            Log.i(TAG, "network is available")
            //get information from City Explorer Lirbary/SDK
            //oncreate we get all cities and set them in spinner
            progressDialog!!.show()
            cityExplorer.getCities(object : CityExploreCallBack{
                override fun onSuccess(list: ArrayList<String>?) {
                    viewModel.clear()
                    //SDK has returned a successfull result. Now you can use the list.
                    //in this case, im using list to create another spinner, to display a list
                    //of malls in the city
                    if (list != null) {
                        val adapter = ArrayAdapter( applicationContext, android.R.layout.simple_spinner_item, list)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        citySpinner.adapter = adapter
                        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }

                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                                if (spinnerCitySwitch == 1) {
                                    spinnerCitySwitch = 0
                                    return
                                }
                                //When user selects city, fetch mall data and create spinner
                                val cityName = parent!!.getItemAtPosition(position).toString()
                                Log.i(TAG, cityName)
                                progressDialog!!.show()
                                cityExplorer.getMalls(cityName, object : CityExploreCallBack{
                                    override fun onSuccess(list: ArrayList<String>?) {
                                        Log.i(TAG, list!!.get(0))
                                        spinnerMallSwitch = 1 //reset switches
                                        mallSpinner.adapter = null
                                        createMallSpinner(list)
                                        creatAllShopsList(cityName)
                                        progressDialog!!.hide()
                                    }

                                    override fun onFail(message: String?) {
                                        Log.i(TAG, message!!)
                                        toast(message)
                                        progressDialog!!.hide()
                                    }
                                })
                            }
                        }
                    }
                    progressDialog!!.hide()
                }

                override fun onFail(message: String?) {
                    //SDK has failed to get data, message is the error message
                    //Now you can write your logic to deal with it
                    if (message != null) {
                        Log.i(TAG, message)
                        toast(message.toString())
                    }
                    progressDialog!!.hide()
                }

            })
        }
    }

    private fun initialiseAdapter(){
        recycler_main.layoutManager = viewManager
        observeData()
    }

    fun observeData(){
        viewModel.mutableList.observe(this, Observer{
            Log.i(TAG,it.toString())
            recycler_main.adapter= ShopRecyclerAdapter(viewModel, it, this)
        })
    }

    /**
     * Create a list of shops
     * @param mallName name of mall
     */
    fun createShopsList(mallName: String){

        //clear previous list
        viewModel.clear()

        val cityExplore = CityExplore(applicationContext)
        cityExplore.getShops(mallName, object : CityExploreCallBack{
            override fun onSuccess(list: ArrayList<String>?) {

                val shopNameList = ArrayList<Shop>()
                try {
                    if (list != null) {
                        for(shopname in list){
                            val shop = Shop("$shopname - $mallName", "www.$shopname.co.za")
                            shopNameList.add(shop)
                            viewModel.add(shop)
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
                toast(message.toString())
            }

        })

        recycler_main.adapter?.notifyDataSetChanged()
    }

    /**
     * Creat a list of ALL shops in a city
     * @param cityName name of city
     */
    fun creatAllShopsList(cityName: String){
        //clear previous list
        viewModel.clear()

        val cityExplore = CityExplore(applicationContext)
        cityExplore.getShopsInCity(cityName, object : CityExploreCallBack{
            override fun onSuccess(list: ArrayList<String>?) {

                val shopNameList = ArrayList<Shop>()
                try {
                    if (list != null) {
                        for(shopname in list){
                            val shop = Shop(shopname, "www.$shopname.co.za")
                            shopNameList.add(shop)
                            viewModel.add(shop)
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
                toast(message.toString())
            }

        })

        recycler_main.adapter?.notifyDataSetChanged()
    }
    /**
     * Create Mall Spinner
     */
    fun createMallSpinner(list: ArrayList<String>){
        val adapter = ArrayAdapter( applicationContext, android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mallSpinner.adapter = adapter
        mallSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (spinnerMallSwitch == 1) {
                    spinnerMallSwitch = 0
                    return
                }
                val mallName = parent!!.getItemAtPosition(position).toString()
                Log.i("city", mallName)
                progressDialog!!.show()
                cityExplorer.getShops(mallName, object : CityExploreCallBack{
                    override fun onSuccess(list: ArrayList<String>?) {
                        Log.i(TAG, "Got shops")
//                        createShopSpinner(list!!)
                        createShopsList(mallName)
                        progressDialog!!.hide()
                    }

                    override fun onFail(message: String?) {
                        Log.i(TAG, message!!)
                        toast(message.toString())
                        progressDialog!!.hide()
                    }

                })

            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
    }
}