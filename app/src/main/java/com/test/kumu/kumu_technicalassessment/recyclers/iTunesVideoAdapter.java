package com.test.kumu.kumu_technicalassessment.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.test.kumu.kumu_technicalassessment.R;

import java.util.ArrayList;

public class iTunesVideoAdapter extends RecyclerView.Adapter<iTunesVideoAdapter.ViewHolder> {

    ArrayList<iTunesVideoLists> iTunesVideoLists = new ArrayList<>();

    public iTunesVideoAdapter(ArrayList<iTunesVideoLists> iTunesVideoLists) {
        this.iTunesVideoLists = iTunesVideoLists;
    }


    @NonNull
    @Override
    public iTunesVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itunesvideo_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull iTunesVideoAdapter.ViewHolder holder, int position) {
        String tv_show_nameStr = iTunesVideoLists.get(position).getTrackNameStr();
        String tv_show_overviewStr = iTunesVideoLists.get(position).getLongDescriptionStr();
        String tv_show_genreStr = iTunesVideoLists.get(position).getGenreStr();
        String tv_show_priceStr = iTunesVideoLists.get(position).getPriceStr();
        String movie_imageStr = iTunesVideoLists.get(position).getArtworkStr();

        holder.setData(tv_show_nameStr, tv_show_overviewStr, tv_show_genreStr, tv_show_priceStr, movie_imageStr);

    }

    @Override
    public int getItemCount() {
        return iTunesVideoLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_show_name, tv_show_overview, tv_show_genre, tv_show_price;
        ImageView movie_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_show_name = itemView.findViewById(R.id.tv_show_name);
            tv_show_overview = itemView.findViewById(R.id.tv_show_overview);
            tv_show_genre = itemView.findViewById(R.id.tv_show_genre);
            tv_show_price = itemView.findViewById(R.id.tv_show_price);
            movie_image = itemView.findViewById(R.id.movie_image);
        }

        public void setData(String tv_show_nameStr, String tv_show_overviewStr, String tv_show_genreStr, String tv_show_priceStr, String movie_imageStr){
            tv_show_name.setText(tv_show_nameStr);
            tv_show_overview.setText(tv_show_overviewStr);
            tv_show_genre.setText(tv_show_genreStr);
            tv_show_price.setText("$".concat(tv_show_priceStr));

            Picasso.get()
                    .load(movie_imageStr)
                    .resize(50, 50)
                    .centerCrop()
                    .placeholder(R.mipmap.placeholderimage)
                    .into(movie_image);
        }
    }
}
