package com.hrithik.cricketplayers.helpers

import com.hrithik.cricketplayers.model.Country
import com.hrithik.cricketplayers.model.Player
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * This is a helper class which helps to parse JSON data (fetched from URL) into the kotlin models
 */
class GetCountriesFromJson {

    fun get(jsonStr: String) : List<Country> {

        // Convert json string to JSONObject
        val jsonObject = JSONObject(jsonStr)

        val listOfCountries = ArrayList<Country>()

        // Get all the names of the countries as JSONArray
        val jsonNamesArray = jsonObject.names() ?: return listOfCountries


        // Iterate through the JSONArray to get the players' data
        for (i in 0 until jsonNamesArray.length()){

            // name of the country
            val name = jsonNamesArray[i] as String

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

        return listOfCountries
    }

}