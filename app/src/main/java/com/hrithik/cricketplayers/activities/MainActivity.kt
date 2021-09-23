package com.hrithik.cricketplayers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hrithik.cricketplayers.MyApplication
import com.hrithik.cricketplayers.adapter.CountriesAdapter
import com.hrithik.cricketplayers.databinding.ActivityMainBinding
import com.hrithik.cricketplayers.helpers.GetCountriesFromJson
import com.hrithik.cricketplayers.model.Country
import java.net.URL
import java.util.concurrent.Executors

/**
 * This is the launcher activity of the application
 *
 * This activity shows the data(names) of all the Cricket Teams
 * and on clicking on a particular team will move to another screen
 * to show the data(names) of the players of that team
 */
class MainActivity : AppCompatActivity() {

    private lateinit var listOfCountries: List<Country>
    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        val myApp = applicationContext as MyApplication

        // Check for Network Availability
        if (myApp.isOffline()){
            val noInternetMsg = "It seems you are offline.\n Please connect to the internet!"
            b.noInternetTV.text = noInternetMsg
            b.noInternetTV.visibility = View.VISIBLE
            return
        }
        else {
            b.noInternetTV.visibility = View.GONE
        }


        // Move forward if network is available
        val url = "https://test.oye.direct/players.json"

        b.progressbar.visibility = View.VISIBLE
        b.countriesRV.visibility = View.GONE
        // Executor to handle long running task on background/worker thread
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        executor.execute {

            // Perform background task here
            val jsonStr = URL(url).readText()

            handler.post {
                // Perform MainThread task here
                listOfCountries = GetCountriesFromJson().get(jsonStr)

                listOfCountries = listOfCountries.sortedBy { country -> country.name }

                setUpAdapter()

                b.countriesRV.visibility = View.VISIBLE
                b.progressbar.visibility = View.GONE
            }
        }


    }

    private fun setUpAdapter() {

        val countriesAdapter = CountriesAdapter(this,listOfCountries)

        b.countriesRV.adapter = countriesAdapter
        b.countriesRV.layoutManager = LinearLayoutManager(this)

    }

}