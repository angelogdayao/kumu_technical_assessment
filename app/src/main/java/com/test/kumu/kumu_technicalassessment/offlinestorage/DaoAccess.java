package com.test.kumu.kumu_technicalassessment.offlinestorage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoAccess {
    @Insert
    Long insertTask(Movies movies);


    @Query("SELECT * FROM Movies ORDER BY created_at desc")
    LiveData<List<Movies>> fetchAllTasks();


    @Query("SELECT * FROM Movies WHERE id =:taskId")
    LiveData<Movies> getTask(int taskId);


    @Update
    void updateTask(Movies movies);


    @Delete
    void deleteTask(Movies movies);
}
