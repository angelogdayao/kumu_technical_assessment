package com.test.kumu.kumu_technicalassessment.offlinestorage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

@SuppressLint("StaticFieldLeak")
public class MovieRepository {

    private String DB_NAME = "db_movies";
    private MoviesDatabase moviesDatabase;

    public MovieRepository(Context context) {
        moviesDatabase = Room.databaseBuilder(context, MoviesDatabase.class, DB_NAME).build();
    }

    public void insertTask(String trackName,String trackDescription,
                           String trackPrice,String trackGenre,String trackImage) {

        insertTask(trackName, trackDescription,trackPrice, trackGenre,trackImage, false, null);
    }


    public void insertTask(String trackName,String trackDescription,
                           String trackPrice,String trackGenre,String trackImage,
                           boolean encrypt,
                           String password) {

        Movies movies = new Movies();
        movies.setTrackName(trackName);
        movies.setTrackDescription(trackDescription);
        movies.setTrackPrice(trackPrice);
        movies.setTrackGenre(trackGenre);
        movies.setTrackImage(trackImage);

        movies.setCreatedAt(AppUtils.getCurrentDateTime());
        movies.setModifiedAt(AppUtils.getCurrentDateTime());

        movies.setEncrypt(encrypt);


        if(encrypt) {
            movies.setPassword(AppUtils.generateHash(password));
        } else movies.setPassword(null);

        insertTask(movies);
    }

    public void insertTask(final Movies movies) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                moviesDatabase.daoAccess().insertTask(movies);
                return null;
            }
        }.execute();
    }


    public void updateTask(final Movies movies) {
        movies.setModifiedAt(AppUtils.getCurrentDateTime());

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                moviesDatabase.daoAccess().updateTask(movies);
                return null;
            }
        }.execute();
    }

    public void deleteTask(final int id) {
        final LiveData<Movies> task = getTask(id);
        if(task != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    moviesDatabase.daoAccess().deleteTask(task.getValue());
                    return null;
                }
            }.execute();
        }
    }
    public void deleteTask(final Movies movies) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                moviesDatabase.daoAccess().deleteTask(movies);
                return null;
            }
        }.execute();
    }

    public LiveData<Movies> getTask(int id) {
        return moviesDatabase.daoAccess().getTask(id);
    }

    public LiveData<List<Movies>> getTasks() {
        return moviesDatabase.daoAccess().fetchAllTasks();
    }
}
