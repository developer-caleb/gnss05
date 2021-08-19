package kr.loplab.gnss05.activities.workmanager

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Worker(var worker: String, var name: String, var user: String, var password: String) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}