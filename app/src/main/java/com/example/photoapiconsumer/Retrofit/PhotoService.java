package com.example.photoapiconsumer.Retrofit;

import com.example.photoapiconsumer.Model.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PhotoService
{
    @GET("photos")
    Call<List<Photo>> getPhotos();
}
