package com.example.whetherapp.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
class DailyForeCast {
    @SerializedName("Date")
    @Expose
     var date: String? = ""

    @SerializedName("EpochDate")
    @Expose
     var epochDate: Int? = 0

    @SerializedName("Temperature")
    @Expose
     var temperature: Temperature? = null

    @SerializedName("Day")
    @Expose
     var day: Day? = null

    @SerializedName("Night")
    @Expose
     var night: Night? = null

    @SerializedName("Sources")
    @Expose
     var sources: List<String?>? = listOf()

    @SerializedName("MobileLink")
    @Expose
     var mobileLink: String? = null

    @SerializedName("Link")
    @Expose
     var link: String? = null


}