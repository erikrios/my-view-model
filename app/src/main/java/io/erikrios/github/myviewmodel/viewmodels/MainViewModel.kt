package io.erikrios.github.myviewmodel.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import io.erikrios.github.myviewmodel.models.WeatherItems
import org.json.JSONObject
import java.text.DecimalFormat

class MainViewModel : ViewModel() {
    private val listWeathers = MutableLiveData<ArrayList<WeatherItems>>()

    fun getWeathers(): LiveData<ArrayList<WeatherItems>> {
        return listWeathers
    }

    fun setWeather(cities: String) {
        val listItems = ArrayList<WeatherItems>()

        val apiKey = "c55d4a58da4dc4d141c1adbcb64cd509"
        val url =
            "https://api.openweathermap.org/data/2.5/group?id=${cities}&units=metric&appid=${apiKey}"

        val client = AsyncHttpClient()
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                try {
                    val result = String(responseBody as ByteArray)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("list")

                    for (i in 0 until list.length()) {
                        val weather = list.getJSONObject(i)
                        val weatherItems = WeatherItems()
                        weatherItems.id = weather.getInt("id")
                        weatherItems.name = weather.getString("name")
                        weatherItems.currentWeather =
                            weather.getJSONArray("weather").getJSONObject(0).getString("main")
                        weatherItems.description = weather.getJSONArray("weather").getJSONObject(0)
                            .getString("description")
                        val tempInKelvie = weather.getJSONObject("main").getDouble("temp")
                        val tempInCelcius = tempInKelvie - 273
                        weatherItems.temprerature = DecimalFormat("##.##").format(tempInCelcius)
                        listItems.add(weatherItems)
                    }

                    listWeathers.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }
}