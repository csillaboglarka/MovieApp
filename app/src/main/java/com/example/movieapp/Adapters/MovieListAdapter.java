package com.example.movieapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.movieapp.API.Result;
import com.example.movieapp.R;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    List<Result> movielist;
    Context context;
    MovieListAdapter.OnItemClickListener mListener;

    public interface  OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }


    public MovieListAdapter(List<Result> s, Context context) {
            this.movielist = s;
            this.context=context;
        }

        @NonNull
        @Override
        public MovieListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.onemovie_item, parent, false);
            return new ViewHolder(v,mListener);
        }

        @Override
        public void onBindViewHolder(@NonNull MovieListAdapter.ViewHolder holder, int position) {
            holder.title.setText(movielist.get(position).getOriginalTitle());
            holder.shortdescription.setText(movielist.get(position).getOverview());
            Glide.with(context).load(movielist.get(position).getPosterPath()).into(holder.image);
        }

        @Override
        public int getItemCount() {
            return movielist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            TextView shortdescription;
            ImageView image;

            public ViewHolder(@NonNull View itemView,final MovieListAdapter.OnItemClickListener listener ) {
                super(itemView);
                title = itemView.findViewById(R.id.movietitle);
                shortdescription = itemView.findViewById(R.id.shortdescription);
                image = itemView.findViewById(R.id.userImage);


            }
        }

        public void add(Result movie){
            movielist.add(movie);
            notifyItemInserted(movielist.size()-1);
        }

        public void addAll(List<Result> movies){
            for(Result m: movies){
                add(m);
            }
        }
        //add empty item
        public void addBottemIttem(){
                add(new Result());
        }

        public void removedLastEmptyItem(){
            int position = movielist.size()-1;
            Result item =  getItem(position);
            if(item != null){
                movielist.remove(position);
                notifyItemRemoved(position);
            }
        }

        private Result getItem(int position){
            return movielist.get(position);
        }
    }