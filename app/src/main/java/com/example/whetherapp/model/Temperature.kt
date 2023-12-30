package com.example.whetherapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Temperature {
    @SerializedName("Minimum")
    @Expose
     var minimum: Minimum? = null

    @SerializedName("Maximum")
    @Expose
     var maximum: Maximum? = null
}