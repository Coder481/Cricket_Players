package com.hrithik.cricketplayers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.hrithik.cricketplayers.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URL
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        val url = "https://test.oye.direct/players.json"

        // Executor to handle long running task on background/worker thread
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        executor.execute {

            // Perform background task here
            val jsonStr = URL(url).readText()

            handler.post {
                // Perform MainThread task here
                showData(jsonStr)
            }
        }


    }

    // todo: Create a separate class for this function
    private fun showData(jsonStr: String) {

        // Convert json string to JSONObject
        val jsonObject = JSONObject(jsonStr)

        // Get all the names of the countries as JSONArray
        val jsonNamesArray = jsonObject.names() ?: return

        val listOfCountries = ArrayList<Country>()

        // Iterate through the JSONArray to get the players' data
        for (i in 0 until jsonNamesArray.length()){

            // name of the country
            val name = jsonNamesArray[0] as String

            // JSONArray of all the players
            val playersJsonArray : JSONArray = jsonObject[name] as JSONArray

            val listOfPlayers = ArrayList<Player>()


            // Iterate through all the players' data
            for (j in 0 until playersJsonArray.length()){

                // Get player JSONObject
                val playerJSONObj = playersJsonArray.get(j) as JSONObject


                // Get player name from player's JSONObject
                val playerName = playerJSONObj.get("name") as String


                // Check player is captain or not in player's JSONObject
                var isCaptain = false
                try {
                    isCaptain = playerJSONObj.get("captain") as Boolean
                }catch (e : JSONException){
                    e.printStackTrace()
                }


                // Add player to the list
                val player = Player(playerName,isCaptain)
                listOfPlayers.add(player)
            }

            val country = Country(name,listOfPlayers)
            listOfCountries.add(country)
        }

        val msg = "${listOfCountries[0].name}-->${listOfCountries[0].players[0]}"
        b.textV.text = msg
    }

}