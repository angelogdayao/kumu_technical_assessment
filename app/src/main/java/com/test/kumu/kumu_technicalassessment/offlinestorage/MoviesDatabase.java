package com.test.kumu.kumu_technicalassessment.offlinestorage;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Movies.class}, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();
}
