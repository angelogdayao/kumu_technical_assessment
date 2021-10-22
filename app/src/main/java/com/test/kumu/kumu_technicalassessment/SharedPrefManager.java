package com.test.kumu.kumu_technicalassessment;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.test.kumu.kumu_technicalassessment.offlinestorage.AppUtils;
import com.test.kumu.kumu_technicalassessment.recyclers.iTunesVideoLists;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "kumusharedpref";
    private static final String KEY_VIDEOSIZE = "videolist";
    private static final String KEY_LASTVISITED = "lastvisited";
    private static final String KEY_ITEMCLICKEDCNT = "clickcount";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean saveMovieList(ArrayList<iTunesVideoLists> tunesVideoLists){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_VIDEOSIZE, tunesVideoLists.size());

        for(int i=0;i<tunesVideoLists.size();i++)
        {
            editor.remove("TrackName_" + i);
            editor.remove("LongDescription_" + i);
            editor.remove("Genre_" + i);
            editor.remove("Price_" + i);
            editor.remove("Artwork_" + i);
            editor.putString("TrackName_" + i, tunesVideoLists.get(i).getTrackNameStr());
            editor.putString("LongDescription_" + i, tunesVideoLists.get(i).getLongDescriptionStr());
            editor.putString("Genre_" + i, tunesVideoLists.get(i).getGenreStr());
            editor.putString("Price_" + i, tunesVideoLists.get(i).getPriceStr());
            editor.putString("Artwork_" + i, tunesVideoLists.get(i).getArtworkStr());
        }
        editor.apply();
        return editor.commit();
    }

    public boolean saveVisitInformation(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(KEY_LASTVISITED);
        editor.putString(KEY_LASTVISITED, AppUtils.getCurrentDateTime().toString());
        editor.apply();
        return editor.commit();
    }


    public static ArrayList<iTunesVideoLists> loadMovieList(Context mContext)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        ArrayList<iTunesVideoLists> tunesVideoLists = new ArrayList<>();

        int size = sharedPreferences.getInt(KEY_VIDEOSIZE, 0);

        for(int i=0;i<size;i++)
        {
            tunesVideoLists.add(new iTunesVideoLists(sharedPreferences.getString("TrackName_" + i, null), sharedPreferences.getString("Artwork_" + i, null),
                    sharedPreferences.getString("Price_" + i, null),sharedPreferences.getString("Genre_" + i, null),sharedPreferences.getString("LongDescription_" + i, null)));
        }

        return tunesVideoLists;

    }

    public boolean isMovieSave(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_VIDEOSIZE, 0) > 0;
    }

    public int getClickCounter(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_ITEMCLICKEDCNT, 0);
    }

    public String getLastDateVisited(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LASTVISITED, null);
    }


}
