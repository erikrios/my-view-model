package io.erikrios.github.myviewmodel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.erikrios.github.myviewmodel.R
import io.erikrios.github.myviewmodel.databinding.WeatherItemsBinding
import io.erikrios.github.myviewmodel.models.WeatherItems

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    private val mData = ArrayList<WeatherItems>()

    fun setData(items: ArrayList<WeatherItems>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val mView =
            LayoutInflater.from(parent.context).inflate(R.layout.weather_items, parent, false)
        return WeatherViewHolder(mView)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = WeatherItemsBinding.bind(itemView)
        fun bind(weatherItems: WeatherItems) {
            with(itemView) {
                binding.textCity.text = weatherItems.name
                binding.textTemp.text = weatherItems.temprerature
                binding.textDesc.text = weatherItems.description

            }
        }
    }
}