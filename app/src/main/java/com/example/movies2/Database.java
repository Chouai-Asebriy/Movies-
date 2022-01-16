package com.example.movies2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    public static String DBNAME= "myFavMovies.db";

    public Database(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS favorites (id TEXT PRIMARY KEY , poster_path TEXT," +
                " backdrop_path TEXT, title TEXT , overview TEXT, release_date TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // insert data into database
    public void insertIntoTheDatabase(int id, String poster_path, String backdrop_path, String title, String overview, String release_date) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("poster_path", poster_path);
        cv.put("backdrop_path", backdrop_path);
        cv.put("title", title);
        cv.put("overview", overview);
        cv.put("release_date", release_date);
        db.insert("favorites",null, cv);
    }

    public boolean checkData(String title){
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("select * from favorites where title=?",new String[]{title});
        if(cursor.getCount()>0)
            return true;
        else
            return  false;
    }

    public ArrayList<Movie> select_all() {
        SQLiteDatabase db = this.getReadableDatabase();
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursor = db.rawQuery("select * from favorites", null);

        // on below line we are creating a new array list.
        ArrayList<Movie> movies = new ArrayList<Movie>();

        // moving our cursor to first position.
        if (cursor.moveToFirst()) {
            do {
                movies.add(new Movie(cursor.getInt(0),cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5) ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return movies;
    }

    public Cursor select_all_favorite_list() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM favorites ";
        return db.rawQuery(sql,null,null);
    }

    public boolean removeData(String title){
        SQLiteDatabase myDB = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        long result = myDB.delete("favorites","title=?",new String[]{title});
        if(result==-1)return false;
        return  true;
    }
}
