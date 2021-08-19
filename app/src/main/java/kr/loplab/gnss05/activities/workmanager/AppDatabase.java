package kr.loplab.gnss05.activities.workmanager;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Worker.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
     public abstract WorkerDao workerDao();

}
