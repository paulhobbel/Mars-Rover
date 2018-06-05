package me.paulhobbel.marsphotos.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.paulhobbel.marsphotos.R;
import me.paulhobbel.marsphotos.providers.models.Photo;

public class DetailActivity extends AppCompatActivity {

    public static final String PHOTO_ARG = "PHOTO_ARG";

    private Photo photo;

    private ImageView image;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        photo = (Photo) getIntent().getSerializableExtra(PHOTO_ARG);

        Log.d("DETAIL_ACTIVITY", "onCreate: " + photo);

        image = findViewById(R.id.detail_photo);
        title = findViewById(R.id.metaCameraText);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Photo ID: " + photo.getId());
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title.setText(photo.getCamera().getFullName());
        Picasso.get().load(photo.getImage()).into(image);
    }

}
