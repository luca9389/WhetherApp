package com.example.whetherapp.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextClock
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.whetherapp.Constants
import com.example.whetherapp.R
import com.example.whetherapp.model.DailyForeCast
import com.squareup.picasso.Picasso
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class WeatherAdapter(val dailyForeCast: ArrayList<DailyForeCast>, val city: String) : RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return WeatherHolder(layoutInflater.inflate(R.layout.item_weather, parent, false))
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        val item = dailyForeCast[position]
        holder.bind(item, city, position)

    }

    override fun getItemCount(): Int
         =  dailyForeCast.size

    class WeatherHolder(view: View):RecyclerView.ViewHolder(view){
        private val cityTv:TextView = view.findViewById(R.id.cityTv)
        private val date:TextView = view.findViewById(R.id.dateTv)
        private val hour:TextClock = view.findViewById(R.id.hourTv)
        private val max:TextView = view.findViewById(R.id.gradesMaxTv)
        private val min:TextView = view.findViewById(R.id.gradesMinTv)
        private val dayTv:TextView = view.findViewById(R.id.dayDescription)
        private val nighTv:TextView = view.findViewById(R.id.nighDescription)
        private val nightImage:ImageView = view.findViewById(R.id.nightImage)
        private val dayImage:ImageView = view.findViewById(R.id.dayImage)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: DailyForeCast, city: String, position: Int) {
            if (position == 0){
                cityTv.visibility = View.VISIBLE
                cityTv.text = cityChangeName(city)
            }
            date.text = formatDate(item.date, "yyyy-MM-dd'T'HH:mm:ssXXX", "dd-MM-yyy")
            max.text = item.temperature?.maximum?.value.toString()
            min.text = item.temperature!!.minimum?.value.toString()
            dayTv.text = item.day!!.iconPhrase
            nighTv.text = item.night!!.iconPhrase

            iconNight(item.night!!.icon)
            iconDay(item.day!!.icon)

        }

        private fun iconNight(icon: Int?) {
            //se hizo esto para poder  poner los iconos, ya que la api solos nos devuelve un entero y no una url,
            //por lo tanto lo que se hizo fue concatenar la url con el entero agregandole un  cero adelante
            //entre el rango 1 y 10 ya que la url de la imagen contiene un 0 adelante de los enteros
            //y la api a nosotros nos devuelve solamente un numero entero sin el cero adelante
            when (icon) {
                in (1..9)-> {
                    val iconStringNight: String = "0" + icon.toString()
                    picassoImages("https://developer.accuweather.com/sites/default/files/$iconStringNight-s.png", nightImage)
                }else->
                picassoImages("https://developer.accuweather.com/sites/default/files/$icon-s.png", nightImage)
            }
        }

        private fun iconDay(icon: Int?) {
            when (icon) {
                in (1..9)-> {
                    val iconStringDay: String = "0" + icon.toString()
                    picassoImages("https://developer.accuweather.com/sites/default/files/$iconStringDay-s.png", dayImage)
                }else->
                picassoImages("https://developer.accuweather.com/sites/default/files/$icon-s.png", dayImage)
            }
        }

        private fun picassoImages(url: String, imageView: ImageView) {
            Picasso.get().load(url).error(R.drawable.no_photography_icon).error(R.drawable.no_photography_icon).into(imageView)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun formatDate(date: String?, inputFormatterString: String, outputFormatterString: String): String { // funcion poara el formato de la fecha
            val inputFormatter =
                DateTimeFormatter.ofPattern(inputFormatterString, Locale.ENGLISH)
            val outputFormatter = DateTimeFormatter.ofPattern(outputFormatterString, Locale.ENGLISH)
            val dateFormat = LocalDate.parse(date, inputFormatter)
            val formattedDate = outputFormatter.format(dateFormat)
            return formattedDate.toString()
        }

        fun cityChangeName(city: String): String {
            when(city){ //Se hace esto para el cambio de ciudades, lo traemos de la main para igualar a las ciudades y poder cambiar el nombre del titulo de la ciudad
                Constants.LOCATIONKEY_BUENOSAIRES -> return Constants.BUENOS_AIRES
                Constants.LOCATIONKEY_BRASILIA -> return Constants.BRASILIA
                Constants.LOCATIONKEY_ROMA -> return Constants.ROMA
                Constants.LOCATIONKEY_LIMA -> return Constants.LIMA
                Constants.LOCATIONKEY_MIAMI -> return Constants.MIAMI
                else -> return Constants.CIUDAD_NO_ENCONTRADA
            }
        }

    }


}