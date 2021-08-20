package kr.loplab.gnss05.activities.cors_servermanager;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import kr.loplab.gnss05.activities.workmanager.Worker;
import kr.loplab.gnss05.activities.workmanager.WorkerDao;

@Database(entities = {Server.class}, version = 0)
public abstract class AppDatabase2 extends RoomDatabase {
     public abstract ServerDao serverDao();
}
