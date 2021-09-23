package com.hrithik.cricketplayers.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hrithik.cricketplayers.activities.PlayersActivity
import com.hrithik.cricketplayers.databinding.CountryItemLayoutBinding
import com.hrithik.cricketplayers.model.Country

/**
 * This class is the adapter for the recycler view used in the main activity
 *
 * This adapter binds all the data of a particular team to the views, in other words
 * this class handles the interaction of the user with the data
 */
class CountriesAdapter(private val context: Context, private val countriesList:List<Country>)
    : RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val binding = CountryItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return CountriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        val country = countriesList[position]
        holder.bind(country,context)
    }

    override fun getItemCount(): Int {
        return countriesList.size
    }

    class CountriesViewHolder(countriesItemLayoutBinding: CountryItemLayoutBinding)
        :RecyclerView.ViewHolder(countriesItemLayoutBinding.root){

        private val binding = countriesItemLayoutBinding

        fun bind(country: Country,context: Context){
            binding.countryName.text = country.name

            binding.countryCard.setOnClickListener {
                context.startActivity(Intent(context,PlayersActivity::class.java).putExtra("Country",country))
            }
        }

    }

}