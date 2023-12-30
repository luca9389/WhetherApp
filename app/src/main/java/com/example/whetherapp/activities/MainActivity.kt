package com.example.whetherapp.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whetherapp.Constants
import com.example.whetherapp.R
import com.example.whetherapp.adapter.WeatherAdapter
import com.example.whetherapp.databinding.ActivityMainBinding
import com.example.whetherapp.fragments.HomeFragment
import com.example.whetherapp.model.DailyForeCast
import com.example.whetherapp.service.Service
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: WeatherAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var spinner : Spinner
    private lateinit var linearError: LinearLayout
    private lateinit var buttonRetry: Button
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController
    private lateinit var containerFragment: Fragment

    private lateinit var binding: ActivityMainBinding

    private lateinit var dailyForestCastResponse:ArrayList<DailyForeCast>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        navController=Navigation.findNavController(this,R.id.container_fragment)

        setupWithNavController(binding.bottomNavigationView,navController)

       /* navController=Navigation.findNavController(this,R.id.activity_main_nav_host_fragment)
        setupWithNavController(binding.bottomNavigationView,navController)*/


       /* initViews()
        getRetrofit()
        initRetrofit(Constants.LOCATIONKEY_BUENOSAIRES)
        spinnerCity()*/

    }


    private fun initViews() {
        recyclerView = findViewById(R.id.recycler_weather)
        spinner = findViewById(R.id.spinner_city)

        linearError = findViewById(R.id.linear_service_error)
        buttonRetry = findViewById(R.id.btn_retry)
        loadingProgressBar = findViewById(R.id.loadingProgressBar)
    }

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    fun initRetrofit(cityValue: String) {
        loadingProgressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        CoroutineScope(Dispatchers.IO).launch {
            val response = getRetrofit().create(Service::class.java).callApiWeather(
                cityValue,
                Constants.APIKEY,
                Constants.LANGUAGE_SPANISH,
                true)
            runOnUiThread {
                if (response.isSuccessful) {
                    loadingProgressBar.visibility = View.GONE
                    dailyForestCastResponse = response.body()?.dailyForeCast ?: arrayListOf()
                    recyclerview(dailyForestCastResponse, cityValue)
                }else{
                    loadingProgressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    linearError.visibility = View.GONE

                    buttonRetry.setOnClickListener {
                        initRetrofit(cityValue)
                    }

                    Toast.makeText(this@MainActivity, response.code().toString(), Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    fun recyclerview(
        dailyForestCastResponse: ArrayList<DailyForeCast>,
        locationkey: String
    ) {
        adapter= WeatherAdapter(dailyForestCastResponse, locationkey)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.visibility = View.VISIBLE
    }

    private fun spinnerCity() {
        val city = arrayOf(Constants.SELECCIONAR,Constants.BUENOS_AIRES, Constants.BRASILIA, Constants.ROMA, Constants.LIMA, Constants.MIAMI)

        val cityMap: HashMap<String, String> = HashMap() // hasmap para poner el valor que nos viene de la api, por eso tenemos la calve que la ciudad y el valor que seria el id el cual es lo q nos vien de la api
        cityMap[Constants.BUENOS_AIRES] = Constants.LOCATIONKEY_BUENOSAIRES
        cityMap[Constants.BRASILIA] = Constants.LOCATIONKEY_BRASILIA
        cityMap[Constants.ROMA] = Constants.ROMA
        cityMap[Constants.LIMA]= Constants.LIMA
        cityMap[Constants.MIAMI]= Constants.LOCATIONKEY_MIAMI

        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, city)
        spinner.adapter = adapter


        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

                val selectedSpinnerText: String = spinner.selectedItem.toString()
                if (selectedSpinnerText != Constants.SELECCIONAR) {
                    for (name in cityMap.keys) {
                        val key = name.toString()
                        val value = cityMap.get(name)
                        if (key == selectedSpinnerText) {
                            initRetrofit(value.toString())
                        }
                    }
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }

}
