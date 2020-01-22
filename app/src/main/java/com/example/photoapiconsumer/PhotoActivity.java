package com.example.photoapiconsumer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class PhotoActivity extends AppCompatActivity {

    private ImageView photo = null;
    private TextView titleView = null;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String photo_url = i.getStringExtra("imageUrl");
        photo = (ImageView) findViewById(R.id.photo);
        titleView = (TextView) findViewById(R.id.ImageTitle);
        titleView.setText(title);
        Picasso.with(this).load(photo_url).into(photo);

        //bitmap = ((BitmapDrawable) photo.getDrawable()).getBitmap();

    }
}
