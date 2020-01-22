package com.example.photoapiconsumer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.photoapiconsumer.Adapter.PhotoAdapter;
import com.example.photoapiconsumer.Model.Photo;
import com.example.photoapiconsumer.Retrofit.PhotoService;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements PhotoAdapter.onPhotoListener
{

    private static final String Tag = MainActivity.class.getSimpleName();
    public static final String apiURL = "http://jsonplaceholder.typicode.com/";
    private static Retrofit retrofit = null;
    private RecyclerView recyclerView = null;
    private List<Photo> listPhotos;
    private PhotoAdapter photoAdapter;
    public static final int PERMISSION_WRITE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photoAdapter = new PhotoAdapter(listPhotos, R.layout.photo_list_item, getApplicationContext(), this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.connectAndGetApiData();
    }

    public void connectAndGetApiData()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(apiURL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        PhotoService albumApiService = retrofit.create(PhotoService.class);
        Call<List<Photo>> call = albumApiService.getPhotos();

        call.enqueue(new Callback<List<Photo>>()
        {

            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response)
            {
                List<Photo> photos = response.body();
                photoAdapter.setPhotos(photos);
                listPhotos = response.body();
                recyclerView.setAdapter(photoAdapter);
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t)
            {
                Log.e(Tag, t.toString());
            }
        });
    }

    @Override
    public void onPhotoClick(int Position)
    {
        Intent i = new Intent(this, PhotoActivity.class);
        i.putExtra("title", listPhotos.get(Position).getTitle());
        i.putExtra("imageUrl", listPhotos.get(Position).getUrl());
        startActivity(i);
    }

}
