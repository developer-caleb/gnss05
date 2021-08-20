package kr.loplab.gnss05.activities.cors_servermanager;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kr.loplab.gnss05.activities.workmanager.Worker;

@Dao
public interface ServerDao {
    @Query("SELECT * FROM Server")
    List<Server> getAll();

    @Insert
    void insert(Server worker);

    @Update
    void update(Server worker);

    @Delete
    void delete(Server worker);
}
