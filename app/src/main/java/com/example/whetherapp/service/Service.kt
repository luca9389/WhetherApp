package com.example.whetherapp.service

import com.example.whetherapp.model.WeatherModel
import retrofit2.Response
import retrofit2.http.*

interface Service {
    @GET("5day/{locationKey}")
    suspend fun callApiWeather(@Path(value = "locationKey", encoded = true) locationKey: String, @Query("apikey")apikey : String, @Query("language")language : String,@Query("metric")metric:Boolean): Response<WeatherModel>
}