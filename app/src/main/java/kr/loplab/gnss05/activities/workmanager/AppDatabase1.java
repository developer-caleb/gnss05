package kr.loplab.gnss05.activities.workmanager;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import kr.loplab.gnss05.activities.cors_servermanager.Server;
import kr.loplab.gnss05.activities.cors_servermanager.ServerDao;

@Database(entities = {Worker.class}, version = 1)
public abstract class AppDatabase1 extends RoomDatabase {
     public abstract WorkerDao workerDao();
}
