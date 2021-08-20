package kr.loplab.gnss05.activities.cors_servermanager

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Entity
data class Server(var name: String, var ip: String, var port: String, var user: String, var password: String) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
/*val MIGRATION_0_1 = object : Migration(0, 1) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE User ADD COLUMN name TEXT NOT NULL DEFAULT '' ")
    }
}*/
