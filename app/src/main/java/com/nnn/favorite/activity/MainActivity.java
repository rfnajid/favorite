package com.nnn.favorite.activity;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nnn.favorite.R;
import com.nnn.favorite.adapter.MovieAdapter;
import com.nnn.favorite.model.Movie;
import com.nnn.favorite.util.S;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String AUTHORITY = "com.nnn.moviee";
    public static final String OBJECT ="favorite";
    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/"+OBJECT);

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    MovieAdapter adapter=null;

    List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        adapter = new MovieAdapter(movieList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        loadData();

    }

    void loadData(){
        Cursor cursor = getContentResolver().query(CONTENT_URI,null,null,null,null);

        S.log(CONTENT_URI+"");
        S.log("cursor count : "+cursor.getCount());

        movieList.clear();
        while (cursor.moveToNext()){

            movieList.add(new Movie(
                    cursor.getLong(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("posterPath")),
                    cursor.getString(cursor.getColumnIndex("overview")),
                    cursor.getString(cursor.getColumnIndex("releaseDate")),
                    cursor.getString(cursor.getColumnIndex("rating"))
            ));
        }

        S.log("list size : "+movieList.size());

        adapter.notifyDataSetChanged();

    }
}
