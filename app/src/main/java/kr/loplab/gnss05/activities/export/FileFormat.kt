package kr.loplab.gnss05.activities.export

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Entity
data class FileFormat(var formatName: String, var extensionName: String, var formatDescription: String) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
/*val MIGRATION_0_1 = object : Migration(0, 1) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE User ADD COLUMN name TEXT NOT NULL DEFAULT '' ")
    }
}*/
