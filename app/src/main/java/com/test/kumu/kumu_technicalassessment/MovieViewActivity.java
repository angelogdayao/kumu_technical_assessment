package com.test.kumu.kumu_technicalassessment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class MovieViewActivity extends AppCompatActivity {
    TextView tv_show_name, tv_show_overview, tv_show_genre, tv_show_price;
    ImageView movie_image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movieview);

        tv_show_name = findViewById(R.id.tv_show_name);
        tv_show_overview = findViewById(R.id.tv_show_overview);
        tv_show_genre = findViewById(R.id.tv_show_genre);
        tv_show_price = findViewById(R.id.tv_show_price);
        movie_image = findViewById(R.id.movie_image);


        //Get the bundle
        Bundle bundle = getIntent().getExtras();
        tv_show_name.setText(bundle.getString("trackname"));
        tv_show_overview.setText(bundle.getString("desc"));
        tv_show_genre.setText(bundle.getString("genre"));
        tv_show_price.setText("$".concat(bundle.getString("price")));

        Picasso.get()
                .load(bundle.getString("artwork"))
                .resize(50, 50)
                .centerCrop()
                .into(movie_image);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MyApplication.getAppContext(), MainActivity.class));
    }
}
