package com.hrithik.cricketplayers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.hrithik.cricketplayers.R
import com.hrithik.cricketplayers.databinding.ActivityPlayersBinding
import com.hrithik.cricketplayers.databinding.PlayerItemLayoutBinding
import com.hrithik.cricketplayers.model.Country
import com.hrithik.cricketplayers.model.Player

/**
 * This activity/screen will open when user click on a particular team in the launcher/main activity
 *
 * This screen shows the data(names) of all the players of a particular team
 */
class PlayersActivity : AppCompatActivity() {
    private lateinit var playersList: List<Player>
    private lateinit var countryName: String
    private lateinit var b: ActivityPlayersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityPlayersBinding.inflate(layoutInflater)
        setContentView(b.root)

        val country = intent.getSerializableExtra("Country") as? Country

        countryName = country?.name.toString()

        supportActionBar?.title = "$countryName's Players"

        if (country != null) {
            playersList = country.players
        }

        if (country != null) {
            b.noDataTV.visibility = View.GONE
            setUpViews()
        }else{
            b.noDataTV.visibility = View.VISIBLE
        }

    }

    private fun setUpViews() {
        if (playersList == null){
            b.noDataTV.visibility = View.VISIBLE
            return
        }else
            b.noDataTV.visibility = View.GONE

        b.playersLL.removeAllViews()

        for (player in playersList){
            val playersBinding = PlayerItemLayoutBinding.inflate(layoutInflater)

            playersBinding.playerName.text = player.name

            // Highlight the captain
            if (player.captain){
                playersBinding.countryCard.setCardBackgroundColor(ResourcesCompat.getColor(resources,R.color.orange_light,null))
                playersBinding.countryCard.setOnClickListener {
                    showToast("${player.name} is $countryName's captain")
                }
                playersBinding.playerName.setTextColor(ResourcesCompat.getColor(resources,R.color.white,null))
            }

            b.playersLL.addView(playersBinding.root)
        }
    }

    private fun showToast(msg : String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.players_activity_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.sortByFirstName){
            sortListByFirstName()
        }
        else if (item.itemId == R.id.sortByLastName){
            sortListByLastName()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun sortListByLastName() {
        playersList = playersList.sortedBy { player ->
            if (player.name.split(" ").size > 1) player.name.split(" ").last()
            else player.name }

        setUpViews()
    }

    private fun sortListByFirstName() {
        playersList = playersList.sortedBy { player -> player.name }

        setUpViews()
    }

}