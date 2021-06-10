package it.ads.app.cityexplorerkt

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import it.ads.app.city_explorer_sdk.CityExplore
import it.ads.app.city_explorer_sdk.interfaces.CityExploreCallBack
import it.ads.app.city_explorer_sdk.network.CheckNetwork
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    val cityEplorer = CityExplore(this)
    val TAG = "city_explorer_kotlin"
    var progressDialog: ProgressDialog? = null
    //these swithces will prevent spinners from automatically selcting first option on load
    var spinnerMallSwitch = 1
    var spinnerCitySwitch = 1
    var spinnerShopSwitch = 1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressDialog = ProgressDialog(this)
        progressDialog!!.setTitle("Please Wait")
        progressDialog!!.setMessage("Loading ...")

        //check if there is network
        var hasNetwork = CheckNetwork(applicationContext)
        if (hasNetwork.isAvailable){
            Log.i(TAG, "network is available")
            //get information from City Explorer Lirbary/SDK
            //oncreate we get the city spinner
            progressDialog!!.show()
            cityEplorer.getCities(object : CityExploreCallBack{
                override fun onSuccess(list: ArrayList<String>?) {
                    if (list != null) {
                        var adapter = ArrayAdapter( applicationContext, android.R.layout.simple_spinner_item, list)
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
                                cityEplorer.getMalls(cityName, object : CityExploreCallBack{
                                    override fun onSuccess(list: ArrayList<String>?) {
                                        Log.i(TAG, list!!.get(0))
                                        spinnerMallSwitch = 1 //reset switches
                                        spinnerShopSwitch = 1
                                        shopSpinner.adapter = null //remove items in lists
                                        mallSpinner.adapter = null
                                        createMallSpinner(list)
                                        progressDialog!!.hide()
                                    }

                                    override fun onFail(message: String?) {
                                        Log.i(TAG, message!!)
                                        progressDialog!!.hide()
                                    }

                                })
                            }

                        }

                    }
                    progressDialog!!.hide()
                }

                override fun onFail(message: String?) {
                    if (message != null) {
                        Log.i(TAG, message)
                    }
                    progressDialog!!.hide()
                }

            })
        }
    }

    /**
     * Create Mall Spinner
     */
    fun createMallSpinner(list: ArrayList<String>){
        var adapter = ArrayAdapter( applicationContext, android.R.layout.simple_spinner_item, list)
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
                cityEplorer.getShops(mallName, object : CityExploreCallBack{
                    override fun onSuccess(list: ArrayList<String>?) {
                        Log.i(TAG, "Got shops")
                        createShopSpinner(list!!)
                        progressDialog!!.hide()
                    }

                    override fun onFail(message: String?) {
                        Log.i(TAG, message!!)
                        progressDialog!!.hide()
                    }

                })

            }

        }

    }

    /**
     * Create Shop Spinner
     */
    fun createShopSpinner(list: ArrayList<String>){
        var adapter = ArrayAdapter( applicationContext, android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        shopSpinner.adapter = adapter
        shopSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (spinnerShopSwitch == 1) {
                    spinnerShopSwitch = 0
                    return
                }
                val shopName = parent!!.getItemAtPosition(position).toString()
                Log.i("city", shopName)
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