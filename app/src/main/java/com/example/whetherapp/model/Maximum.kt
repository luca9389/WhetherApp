package com.example.whetherapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Maximum {
    @SerializedName("Value")
    @Expose
    var value: Double? = 0.0

    @SerializedName("Unit")
    @Expose
    var unit: String? = ""

    @SerializedName("UnitType")
    @Expose
    var unitType: Int? = 0
}