package com.example.whetherapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherModel {

    @SerializedName("Headline")
    @Expose
    var headLine: HeadLine? = null

    @SerializedName("DailyForecasts")
    @Expose
    var dailyForeCast: ArrayList<DailyForeCast>? = null
}