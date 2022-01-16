package com.example.movies2;

import static com.example.movies2.MovieAdapter.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetails extends AppCompatActivity {
    String list_genres = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_movie);

        Intent intent = getIntent();
        Movie movie;
        String title;
        String backdrop_path;
        String overview;
        String release_date;
        int[] genres;
        if (getIntent().getExtras() != null){
            movie = (Movie) getIntent().getSerializableExtra("movie");

            title = movie.getTitle();
            getSupportActionBar().setTitle(title);

            backdrop_path = movie.getBackdrop_path();
            ImageView image = (ImageView) findViewById(R.id.movieBackImage);
            Glide.with(this).load(BASE_URL + backdrop_path).into(image);

            overview = movie.getOverview();
            TextView overviewText = (TextView)findViewById(R.id.overview);
            overviewText.setText(overview);

            release_date = movie.getRelease_date();
            TextView releaseDateText = (TextView)findViewById(R.id.releaseDate);
            releaseDateText.setText(release_date);

            genres = movie.getGenre_ids();
            MovieService movieService = new Retrofit.Builder()
                    .baseUrl(MovieService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MovieService.class);
            movieService.getAllGenres("92d9807be90ba6c492aaa7e6a4893a46").enqueue(new Callback<GenresResponse>() {
                @Override
                public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
                    List<Genre> all_genres = response.body().getResults();
                    for (Genre genre : all_genres) {
                        for (int i : genres) {
                            if(genre.getId() == i) {
                                list_genres  = list_genres + "\n"+genre.getName();
                            }
                        }
                    }
                    TextView genresText = (TextView)findViewById(R.id.genres);
                    genresText.setText(String.valueOf(list_genres));
                }

                @Override
                public void onFailure(Call<GenresResponse> call, Throwable t) {

                }
            });

        }
    }
}
