package com.example.movies2;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private View view;
    private NestedScrollView nestedScrollView;
    private RecyclerView rvFavorites;
    private FavoriteAdapter favAdapter;
    private List<Movie> movies  = new ArrayList<>(); ;
    private Database db;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Favorite Movies");

        view = inflater.inflate(R.layout.fragment_favorites, container, false);

        rvFavorites= (RecyclerView) view.findViewById(R.id.rvFavorites);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.scroll_view);
        context = ((AppCompatActivity)getActivity()).getApplicationContext();
        db = new Database(context);
        favAdapter = new FavoriteAdapter(movies);
        rvFavorites.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        loadData();
        
        return view;
    }

    private void loadData() {
        if (movies != null) {
            movies.clear();
        }
        Cursor cursor = db.select_all_favorite_list();
        try {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String poster_path = cursor.getString(1);
                String backdrop_path = cursor.getString(2);
                String title = cursor.getString(3);
                String overview = cursor.getString(4);
                String release_date = cursor.getString(5);

                Movie favItem = new Movie(id,poster_path,backdrop_path,title,overview,release_date);
                movies.add(favItem);
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }

        rvFavorites.setAdapter(favAdapter);

    }

}
