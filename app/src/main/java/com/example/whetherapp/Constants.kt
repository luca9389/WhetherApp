package com.example.whetherapp

import android.widget.Spinner

class Constants {

    companion object{

        //Descripciones para el usuario
        const val SELECCIONAR = "Seleccionar Ciudad"

        //Constanstes para la api
        const val BASEURL = "http://dataservice.accuweather.com/forecasts/v1/daily/"
        const val APIKEY = "W8LAMnCe7s5JaUrZmkaCAybBet9SOZE7"
        const val LOCATIONKEY_BUENOSAIRES = "7894"
        const val LOCATIONKEY_BRASILIA = "" //cometario para ver q ciudades agregar
        const val LOCATIONKEY_ROMA = "11" //cometario para ver q ciudades agregar
        const val LOCATIONKEY_LIMA = "22" //cometario para ver q ciudades agregar
        const val LOCATIONKEY_MIAMI = "347936" //cometario para ver q ciudades agregar
        const val LANGUAGE_SPANISH = "es-ar"

        //Constantes de ciudades
        const val BUENOS_AIRES = "Buenos Aires"
        const val BRASILIA = "Brasilia"
        const val ROMA = "Roma"
        const val LIMA = "Lima"
        const val MIAMI = "Miami"
        const val CIUDAD_NO_ENCONTRADA = "Ciudad no encontrada"
    }

}