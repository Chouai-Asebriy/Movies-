package com.example.movies2;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PopularFragment extends Fragment {

    private NestedScrollView nestedScrollView;
    private RecyclerView rvMovies;
    private ProgressBar progressBar;
    private MovieAdapter adapter;
    private View view;
    private Parcelable recyclerViewState;
    private int page = 1;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Popular Movies");

        view = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        rvMovies = (RecyclerView) view.findViewById(R.id.rvPopular);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.scroll_view);
        progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);

        getData(page);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);


        rvMovies.setLayoutManager(manager);



        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    getData(page);
                }
            }
        });
        return view;
    }

    public void getData(int page){
        MovieService movieService = new Retrofit.Builder()
                .baseUrl(MovieService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieService.class);

        movieService.listPopularMovies("92d9807be90ba6c492aaa7e6a4893a46",page).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                if(response.isSuccessful() && response.body() != null){
                    afficherMovies(response.body());
                    adapter = new MovieAdapter(response.body().getResults(), ((AppCompatActivity)getActivity()).getApplicationContext());
                    rvMovies.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                t.getCause();
            }
        });
    }
    public void afficherMovies(MovieResponse movies) {
        Toast.makeText(getActivity().getApplicationContext(),"nombre de dépots : "+movies.getTotalPages(), Toast.LENGTH_SHORT).show();
    }
}
