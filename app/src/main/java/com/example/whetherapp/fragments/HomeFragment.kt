package com.example.whetherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whetherapp.Constants
import com.example.whetherapp.R
import com.example.whetherapp.adapter.WeatherAdapter
import com.example.whetherapp.databinding.ActivityMainBinding
import com.example.whetherapp.databinding.FragmentHomeBinding
import com.example.whetherapp.model.DailyForeCast
import com.example.whetherapp.service.Service
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var adapter: WeatherAdapter

    private lateinit var dailyForestCastResponse:ArrayList<DailyForeCast>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        getRetrofit()
        initRetrofit(Constants.LOCATIONKEY_BUENOSAIRES)
        spinnerCity()

        return binding.root


    }

    private fun spinnerCity() {
        val city = arrayOf(Constants.SELECCIONAR,Constants.BUENOS_AIRES, Constants.BRASILIA, Constants.ROMA, Constants.LIMA, Constants.MIAMI)

        val cityMap: HashMap<String, String> = HashMap() // hasmap para poner el valor que nos viene de la api, por eso tenemos la calve que la ciudad y el valor que seria el id el cual es lo q nos vien de la api
        cityMap[Constants.BUENOS_AIRES] = Constants.LOCATIONKEY_BUENOSAIRES
        cityMap[Constants.BRASILIA] = Constants.LOCATIONKEY_BRASILIA
        cityMap[Constants.ROMA] = Constants.ROMA
        cityMap[Constants.LIMA]= Constants.LIMA
        cityMap[Constants.MIAMI]= Constants.LOCATIONKEY_MIAMI

        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, city)
        binding.toolbarHome.spinnerCity.adapter = adapter


        binding.toolbarHome.spinnerCity.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

                val selectedSpinnerText: String = binding.toolbarHome.spinnerCity.selectedItem.toString()
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

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    fun initRetrofit(cityValue: String) {
        binding.loadingProgressBar.visibility = View.VISIBLE
        binding.recyclerWeather.visibility = View.GONE
        CoroutineScope(Dispatchers.IO).launch {
            val response = getRetrofit().create(Service::class.java).callApiWeather(
                cityValue,
                Constants.APIKEY,
                Constants.LANGUAGE_SPANISH,
                true)
            activity?.runOnUiThread {
                if (response.isSuccessful) {
                    binding.loadingProgressBar.visibility = View.GONE
                    binding.linearServiceError.visibility = View.GONE
                    dailyForestCastResponse = response.body()?.dailyForeCast ?: arrayListOf()
                    recyclerview(dailyForestCastResponse, cityValue)
                }else{
                    binding.loadingProgressBar.visibility = View.GONE
                    binding.recyclerWeather.visibility = View.GONE
                    binding.linearServiceError.visibility = View.VISIBLE

                    binding.btnRetry.setOnClickListener {
                        initRetrofit(cityValue)
                    }

                    Toast.makeText(requireContext(), response.code().toString(), Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    fun recyclerview(
        dailyForestCastResponse: ArrayList<DailyForeCast>,
        locationkey: String
    ) {
        adapter= WeatherAdapter(dailyForestCastResponse, locationkey)
        binding.recyclerWeather.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerWeather.adapter = adapter
        binding.recyclerWeather.visibility = View.VISIBLE
    }


}