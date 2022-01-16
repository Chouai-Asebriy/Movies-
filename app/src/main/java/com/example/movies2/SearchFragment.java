package com.example.movies2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment {
    Boolean isScrolling;
    int token = 1;
    String querySearch;
    GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
    MovieService movieService = new Retrofit.Builder()
            .baseUrl(MovieService.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService.class);
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Search");

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        RecyclerView rvMovies = (RecyclerView) view.findViewById(R.id.searchMovies);

        //Populate rv
        movieService.listPopularMovies("92d9807be90ba6c492aaa7e6a4893a46",1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieAdapter adapter= new MovieAdapter(response.body().getResults(),((AppCompatActivity)getActivity()).getApplicationContext());
                rvMovies.setLayoutManager(manager);
                rvMovies.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                t.getCause();
            }
        });

        //SearchView
        SetupSearchView(inflater,container,view, rvMovies);
    return view;
    }

    private void SetupSearchView(@NonNull LayoutInflater inflater, ViewGroup container, View view, RecyclerView rv){

        final SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                querySearch = query;
                System.out.println(query);
                movieService.getMovieByName("92d9807be90ba6c492aaa7e6a4893a46",query,1).enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        token++;
                        MovieAdapter adapter= new MovieAdapter(response.body().getResults(), ((AppCompatActivity)getActivity()).getApplicationContext());
                        rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        rv.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        t.getCause();
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }
}



