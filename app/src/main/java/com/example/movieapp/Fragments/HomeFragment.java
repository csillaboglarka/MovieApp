package com.example.movieapp.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.movieapp.API.Client;
import com.example.movieapp.API.Constans;
import com.example.movieapp.API.Example;
import com.example.movieapp.API.GetMovie;
import com.example.movieapp.API.Result;
import com.example.movieapp.Activities.HomeActivity;
import com.example.movieapp.Adapters.MovieListAdapter;
import com.example.movieapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    List<Result> movieList;
    LinearLayoutManager layoutManager;
    MovieListAdapter movieListAdapter;
    EditText et_searchmovie;
    Button btn_search;
    private static final int START_PAGE = 1;
    private int TOTAL_PAGE = 20;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int CURRENT_PAGE = START_PAGE;


    Client client = new Client();
    GetMovie getMovie = client.getClient().create(GetMovie.class);

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = v.findViewById(R.id.list_movies);

        movieList = new ArrayList<>();
        movieListAdapter = new MovieListAdapter(movieList, v.getContext());
        et_searchmovie= v.findViewById(R.id.searchmovie);
        btn_search = v.findViewById(R.id.btnsearch);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(movieListAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        movieListAdapter.notifyDataSetChanged();
        recyclerView.addOnScrollListener(new HomeActivity.PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                CURRENT_PAGE += 1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGE;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        Call<Example> call = getMovie.getPopularMovies(Constans.API_KEY, START_PAGE);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                List<Result> movies = response.body().getResults();
                recyclerView.setAdapter(new MovieListAdapter(movies, getContext()));

                if(CURRENT_PAGE <= TOTAL_PAGE ){
                    movieListAdapter.addBottemIttem();
                }else{
                    isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.d("error", t.getMessage());
                Toast.makeText(getActivity(), "Error Fetching data", Toast.LENGTH_SHORT).show();
            }
        });


//        //search a movie
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String searchedMovie = et_searchmovie.getText().toString();
                Call<Example> call = getMovie.getAllData(searchedMovie);
                call.enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                        List<Result> movie = response.body().getResults();
                        recyclerView.setAdapter(new MovieListAdapter(movie, getActivity().getApplicationContext()));
                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        Log.d("error", t.getMessage());
                        Toast.makeText(getActivity(), "Error Fetching data", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });




        return v;
    }

    private void loadNextPage() {

        Call<Example> call = getMovie.getPopularMovies(Constans.API_KEY,CURRENT_PAGE);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                List<Result> movies = response.body().getResults();
                movieListAdapter.removedLastEmptyItem();
                isLoading=false;
                movieListAdapter.addAll(movies);

                if(CURRENT_PAGE != TOTAL_PAGE ){
                    movieListAdapter.addBottemIttem();
                }else{
                    isLastPage = true;
                }

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.d("error", t.getMessage());
                Toast.makeText(getActivity(), "Error Fetching data", Toast.LENGTH_SHORT).show();
            }
        });


    }

}

