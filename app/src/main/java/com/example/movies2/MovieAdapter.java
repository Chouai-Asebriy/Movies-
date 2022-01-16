package com.example.movies2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    private Context context;
    private Database db;
    public static final String BASE_URL = "https://image.tmdb.org/t/p/w500";
    private final List<Movie> movies;

    public MovieAdapter(List<Movie> lMovies, Context context){
        movies = lMovies;
        this.context = context;
        db = new Database(context);

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View movieview = inflater.inflate(R.layout.item_movie,parent, false);
        return new ViewHolder(movieview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Movie movie = movies.get(position);
        ImageView image = holder.image;

        TextView title = holder.movieTitle;
        title.setText(movie.getTitle());

        ImageButton button = holder.favBtn;
        if(db.checkData(title.getText().toString())){
            button.setImageResource(R.drawable.ic_favorite_24);
        }
        else{
            button.setImageResource(R.drawable.ic_favorite_border_24);
        }

        Glide.with(holder.itemView).load(BASE_URL+movie.getPoster_path()).into(image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetails.class);
                intent.putExtra("movie",movie);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView movieTitle;
        public ImageButton favBtn;

        public ViewHolder(View itemView){
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.movieImage);
            movieTitle = (TextView) itemView.findViewById(R.id.MovieTitle);
            favBtn = itemView.findViewById(R.id.favorite_btn);
            movieTitle.setVisibility(View.INVISIBLE);

            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    final Movie movie = movies.get(position);
                    if(!db.checkData(movie.getTitle())){
                        db.insertIntoTheDatabase(movie.getId(),movie.getPoster_path(),movie.getBackdrop_path(),movie.getTitle(),movie.getOverview(),movie.getRelease_date());
                        Toast.makeText(view.getContext(),"add to favorites",Toast.LENGTH_SHORT).show();
                        favBtn.setImageResource(R.drawable.ic_favorite_24);
                    }
                    else{
                        db.removeData(movie.getTitle());
                        Toast.makeText(view.getContext(),"removed from favorites",Toast.LENGTH_SHORT).show();
                        favBtn.setImageResource(R.drawable.ic_favorite_border_24);
                    }
                }
            });
        }
    }
}
