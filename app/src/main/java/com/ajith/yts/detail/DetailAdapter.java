package com.ajith.yts.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ajith.yts.R;

import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {

    ArrayList<MovieDetail> movieDetails;
    Context context;

    public DetailAdapter(ArrayList<MovieDetail> movieDetails, Context context) {
        this.movieDetails = movieDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        final MovieDetail movieDetail = movieDetails.get(position);

        holder.quality.setText(removeQuotes(movieDetail.getQuality()));
        holder.type.setText(removeQuotes(movieDetail.getType()));
        holder.button.setText(removeQuotes(movieDetail.getSize()));

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse(removeQuotes(movieDetail.getUrl()));
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                context.startActivity(launchBrowser);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieDetails.size();
    }

    static class DetailViewHolder extends RecyclerView.ViewHolder{

        private TextView quality, type;
        private Button button;

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);

            quality = itemView.findViewById(R.id.quality);
            type = itemView.findViewById(R.id.type);
            button = itemView.findViewById(R.id.button);
        }
    }

    private String removeQuotes(String string) {

        return string.replace("\"", "");
    }
}
