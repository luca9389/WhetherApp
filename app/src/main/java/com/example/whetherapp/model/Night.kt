package com.example.whetherapp.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Night {
    @SerializedName("Icon")
    @Expose
    val icon: Int? = null

    @SerializedName("IconPhrase")
    @Expose
    val iconPhrase: String? = null

    @SerializedName("HasPrecipitation")
    @Expose
    val hasPrecipitation: Boolean? = null
}