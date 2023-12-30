package com.example.whetherapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HeadLine {

    @SerializedName("EffectiveDate")
    @Expose
    var effectiveDate:String? = ""

    @SerializedName("EffectiveEpochDate")
    @Expose
    var effectiveEpochDate:Int? = 0

    @SerializedName("Severity")
    @Expose
    var severity: Int? = 0


    @SerializedName("Text")
    @Expose
    var text: String? = ""

    @SerializedName("Category")
    @Expose
    var category: String? = ""

    @SerializedName("EndDate")
    @Expose
    var endDate: String?  = ""

    @SerializedName("EndEpochDate")
    @Expose
    var endEpochDate:Int? = 0

    @SerializedName("MobileLink")
    @Expose
    var mobileLink:String? = ""

    @SerializedName("Link")
    @Expose
    var link:String? = ""


}
