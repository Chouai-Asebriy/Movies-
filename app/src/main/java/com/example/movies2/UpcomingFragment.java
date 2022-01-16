package com.example.movies2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpcomingFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Upcoming Movies");
        View view = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        RecyclerView rvMovies = (RecyclerView) view.findViewById(R.id.rvPopular);

        MovieService movieService = new Retrofit.Builder()
                .baseUrl(MovieService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieService.class);

        movieService.listUpcomingMovies("92d9807be90ba6c492aaa7e6a4893a46",1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                afficherMovies(response.body());
                MovieAdapter adapter= new MovieAdapter(response.body().getResults(), ((AppCompatActivity)getActivity()).getApplicationContext());
                rvMovies.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                rvMovies.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                t.getCause();
            }
        });
        return view;
    }
    public void afficherMovies(MovieResponse movies) {
        Toast.makeText(getActivity().getApplicationContext(),"nombre de dépots : "+movies.getTotalPages(), Toast.LENGTH_SHORT).show();
    }
}
