package com.example.photoapiconsumer.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photoapiconsumer.Model.Photo;
import com.example.photoapiconsumer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.widget.Toast.*;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>
{
    private List<Photo> Photos;
    private int rowLayout;
    private Context context;
    private onPhotoListener onPhotoListener;

    public PhotoAdapter(List<Photo> Photos, int rowLayout, Context context, onPhotoListener onPhotoListener)
    {
        this.Photos = Photos;
        this.rowLayout = rowLayout;
        this.context = context;
        this.onPhotoListener = onPhotoListener;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PhotoViewHolder(view, onPhotoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position)
    {
        String image_url = Photos.get(position).getThumbnailUrl();
        Picasso.with(context).load(image_url).placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.Image);

        holder.photoTitle.setText(Photos.get(position).getTitle());


    }

    @Override
    public int getItemCount()
    {
        return Photos.size();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        LinearLayout photosLayout;
        ImageView Image;
        TextView photoTitle;
        onPhotoListener onPhotoListener;

        public PhotoViewHolder(@NonNull View v, onPhotoListener onPhotoListener)
        {
            super(v);
            this.photosLayout = (LinearLayout) v.findViewById(R.id.photos_layout);
            this.Image = (ImageView) v.findViewById(R.id.image);
            this.photoTitle = (TextView) v.findViewById(R.id.title);
            this.onPhotoListener = onPhotoListener;

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            Log.d("layout", "Clicked");
            onPhotoListener.onPhotoClick(getAdapterPosition());
        }
    }

    public interface onPhotoListener
    {
        void onPhotoClick(int Position);
    }

    public void setPhotos(List<Photo> photos) {
        Photos = photos;
    }
}
