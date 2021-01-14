package io.erikrios.github.myviewmodel


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.erikrios.github.myviewmodel.adapters.WeatherAdapter
import io.erikrios.github.myviewmodel.databinding.ActivityMainBinding
import io.erikrios.github.myviewmodel.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: WeatherAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = WeatherAdapter()
        adapter.notifyDataSetChanged()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        binding.btnCity.setOnClickListener {
            val city = binding.editCity.text.toString()
            if (city.isEmpty()) return@setOnClickListener
            showLoading(true)

            mainViewModel.setWeather(city)
        }

        mainViewModel.getWeathers().observe(this, { weatherItems ->
            weatherItems?.let {
                adapter.setData(weatherItems)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}