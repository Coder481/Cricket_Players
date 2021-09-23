package com.hrithik.cricketplayers.model

import java.io.Serializable


data class Country(val name : String
                  ,val players: List<Player>) : Serializable
