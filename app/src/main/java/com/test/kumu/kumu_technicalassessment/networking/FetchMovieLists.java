package com.test.kumu.kumu_technicalassessment.networking;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.test.kumu.kumu_technicalassessment.MyApplication;
import com.test.kumu.kumu_technicalassessment.offlinestorage.AppUtils;
import com.test.kumu.kumu_technicalassessment.offlinestorage.MovieRepository;
import com.test.kumu.kumu_technicalassessment.offlinestorage.Movies;
import com.test.kumu.kumu_technicalassessment.recyclers.iTunesVideoLists;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FetchMovieLists {

    public static void GetMovieList(final MovieListCallback movieListCallback){
        ArrayList<iTunesVideoLists> tunesVideoLists = new ArrayList<>();
        MovieRepository movieRepository = new MovieRepository(MyApplication.getAppContext());
        Movies movies = new Movies();
        // Volley Android - Used for GET request on the given URL to fetch all the movie list.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://itunes.apple.com/search?term=star&amp;country=au&amp;media=movie&amp;all", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj  = new JSONObject(response);
                    JSONObject movieObj;

                    //Checking if result is not empty
                    if(obj.getInt("resultCount") <= 0 || obj.length() == 0){
                        movieListCallback.onFail();
                        return;
                    }

                    //Fetch the result into a jsonarray.
                    JSONArray jsonArray = new JSONArray(obj.getString("results"));
                    for(int i = 0; i < jsonArray.length(); i++) {
                        try {
                            movieObj = jsonArray.getJSONObject(i);
                            String description;
                            String trackName;
                            String trackPrice;
                            if(!movieObj.isNull("longDescription")){
                                description = movieObj.getString("longDescription");
                            }else if(!movieObj.isNull("shortDescription")){
                                description = movieObj.getString("shortDescription");
                            }else if(!movieObj.isNull("description")){
                                description = movieObj.getString("description");
                            }else{
                                description = "";
                            }

                            if(movieObj.getString("wrapperType").equals("audiobook")){
                                trackName = movieObj.getString("collectionName");
                                trackPrice = movieObj.getString("collectionPrice");
                            }else{
                                trackName = movieObj.getString("trackName");
                                trackPrice = movieObj.getString("trackPrice");
                            }
                            tunesVideoLists.add(new iTunesVideoLists(trackName, movieObj.getString("artworkUrl100"),
                                    trackPrice,movieObj.getString("primaryGenreName"),description));

                            movieRepository.insertTask(trackName,description,trackPrice,movieObj.getString("primaryGenreName"), movieObj.getString("artworkUrl100"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    movieListCallback.onSuccess(tunesVideoLists);
                    return;

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(MyApplication.getAppContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        requestQueue.add(stringRequest);
    }


    public interface MovieListCallback{
        void onSuccess(ArrayList<iTunesVideoLists> tunesVideoLists);
        void onFail();
    }
}
