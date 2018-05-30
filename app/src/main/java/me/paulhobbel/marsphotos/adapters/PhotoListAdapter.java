package me.paulhobbel.marsphotos.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.paulhobbel.marsphotos.R;
import me.paulhobbel.marsphotos.providers.models.Photo;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {

    private List<Photo> photos;
    private OnItemClickListener itemClickListener;

    public PhotoListAdapter(List<Photo> photos) {
        this.photos = photos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo photo = getItem(position);

        holder.title.setText("Image ID: " + photo.getId());

        Picasso.get().cancelRequest(holder.image);
        Picasso.get().load(photo.getImage())
                .into(holder.image);
    }

    public Photo getItem(int position) {
        return photos.get(position);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void setItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView image;

        ViewHolder(View view) {
            super(view);

            view.setOnClickListener(this);

            title = view.findViewById(R.id.photo_item_title);
            image = view.findViewById(R.id.photo_item_image);
        }

        @Override
        public void onClick(View v) {
            if(itemClickListener != null) {
                itemClickListener.onItemClick(v, getItem(getLayoutPosition()));
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View v, Photo photo);
    }
}
