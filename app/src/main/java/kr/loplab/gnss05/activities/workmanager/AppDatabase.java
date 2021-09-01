package kr.loplab.gnss05.activities.workmanager;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import java.io.File;

import kr.loplab.gnss05.activities.cors_servermanager.Server;
import kr.loplab.gnss05.activities.cors_servermanager.ServerDao;
import kr.loplab.gnss05.activities.export.FileFormat;
import kr.loplab.gnss05.activities.export.FileFormatDao;

@Database(entities = {Worker.class, Server.class, FileFormat.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
     public abstract WorkerDao workerDao();
     public abstract ServerDao serverDao();
     public abstract FileFormatDao fileFormatDao();
}
