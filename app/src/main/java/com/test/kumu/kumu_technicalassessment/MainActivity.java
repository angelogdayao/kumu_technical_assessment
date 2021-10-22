package com.test.kumu.kumu_technicalassessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.kumu.kumu_technicalassessment.networking.FetchMovieLists;
import com.test.kumu.kumu_technicalassessment.offlinestorage.AppUtils;
import com.test.kumu.kumu_technicalassessment.offlinestorage.Movies;
import com.test.kumu.kumu_technicalassessment.recyclers.RecyclerItemClickListener;
import com.test.kumu.kumu_technicalassessment.recyclers.iTunesVideoAdapter;
import com.test.kumu.kumu_technicalassessment.recyclers.iTunesVideoLists;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.ALLOW;
import static androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler_view;
    private iTunesVideoAdapter tunesVideoAdapter;
    private ProgressDialog progressDialog;

    private ArrayList<iTunesVideoLists> tunesVideoList = new ArrayList<>();

    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;
    private TextView lastVisitedTV;
    private Movies movies = new Movies();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading......");
        progressDialog.setCancelable(false);


        recycler_view = findViewById(R.id.recycler_view);
        lastVisitedTV = findViewById(R.id.dateTextView);

        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        recycler_view.addOnItemTouchListener(
                new RecyclerItemClickListener(MyApplication.getAppContext(), recycler_view ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever

                        Intent intent = new Intent(MyApplication.getAppContext(), MovieViewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("trackname",tunesVideoList.get(position).getTrackNameStr());
                        bundle.putString("artwork",tunesVideoList.get(position).getArtworkStr());
                        bundle.putString("price",tunesVideoList.get(position).getPriceStr());
                        bundle.putString("desc",tunesVideoList.get(position).getLongDescriptionStr());
                        bundle.putString("genre",tunesVideoList.get(position).getGenreStr());

                        intent.putExtras(bundle);

                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(SharedPrefManager.getInstance(MyApplication.getAppContext()).isMovieSave()){
            tunesVideoList = SharedPrefManager.loadMovieList(MyApplication.getAppContext());

            tunesVideoAdapter = new iTunesVideoAdapter(tunesVideoList);

            recycler_view.setAdapter(tunesVideoAdapter);
            progressDialog.dismiss();

            lastVisitedTV.setText(SharedPrefManager.getInstance(MyApplication.getAppContext()).getLastDateVisited());
        }else{
            progressDialog.show();
            FetchMovies();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = recycler_view.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);

        SharedPrefManager.getInstance(MyApplication.getAppContext()).saveVisitInformation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(SharedPrefManager.getInstance(MyApplication.getAppContext()).isMovieSave()){
            tunesVideoList = SharedPrefManager.loadMovieList(MyApplication.getAppContext());

            tunesVideoAdapter = new iTunesVideoAdapter(tunesVideoList);

            recycler_view.setAdapter(tunesVideoAdapter);
            progressDialog.dismiss();

            if (mBundleRecyclerViewState != null) {
                Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
                recycler_view.getLayoutManager().onRestoreInstanceState(listState);
            }

            lastVisitedTV.setText(SharedPrefManager.getInstance(MyApplication.getAppContext()).getLastDateVisited());
        }
    }

    public void FetchMovies(){
        FetchMovieLists.GetMovieList(new FetchMovieLists.MovieListCallback() {
            @Override
            public void onSuccess(ArrayList<iTunesVideoLists> tunesVideoLists) {
                tunesVideoList = tunesVideoLists;

                tunesVideoAdapter = new iTunesVideoAdapter(tunesVideoLists);

                recycler_view.setAdapter(tunesVideoAdapter);
                progressDialog.dismiss();
                SharedPrefManager.getInstance(MyApplication.getAppContext()).saveMovieList(tunesVideoList);
                lastVisitedTV.setText(AppUtils.getCurrentDateTime().toString());


            }

            @Override
            public void onFail() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPrefManager.getInstance(MyApplication.getAppContext()).saveVisitInformation();
        movies.setModifiedAt(AppUtils.getCurrentDateTime());

    }

    public String getDateToday(){
        String dateStr;
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
        dateStr = df.format(c);

        return dateStr;
    }

}