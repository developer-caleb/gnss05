package kr.loplab.gnss05.activities.workmanager;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WorkerDao {
    @Query("SELECT * FROM Worker")
    List<Worker> getAll();

    @Insert
    void insert(Worker worker);

    @Update
    void update(Worker worker);

    @Delete
    void delete(Worker worker);
}
