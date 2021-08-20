package kr.loplab.gnss05.activities.cors_servermanager

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Server(var name: String, var ip: String, var port: String, var user: String, var password: String) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}