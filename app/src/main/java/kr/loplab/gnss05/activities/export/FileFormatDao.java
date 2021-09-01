package kr.loplab.gnss05.activities.export;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kr.loplab.gnss05.activities.cors_servermanager.Server;

@Dao
public interface FileFormatDao {
    @Query("SELECT * FROM FileFormat")
    List<FileFormat> getAll();

    @Insert
    void insert(FileFormat fileFormat);

    @Update
    void update(FileFormat fileFormat);

    @Delete
    void delete(FileFormat fileFormat);
}
