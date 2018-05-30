package me.paulhobbel.marsphotos.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import me.paulhobbel.marsphotos.R;
import me.paulhobbel.marsphotos.adapters.CameraSpinnerAdapter;
import me.paulhobbel.marsphotos.adapters.PhotoListAdapter;
import me.paulhobbel.marsphotos.providers.PhotoProvider;
import me.paulhobbel.marsphotos.providers.models.Photo;

public class MainActivity extends AppCompatActivity implements
        PhotoProvider.ListCallback, PhotoListAdapter.OnItemClickListener {

    private List<Photo> photos = new ArrayList<>();

    private PhotoProvider provider;
    private PhotoListAdapter adapter;

    private Spinner spinner;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.photo_camera_spinner);

        CameraSpinnerAdapter spinnerAdapter = new CameraSpinnerAdapter(this);
        spinnerAdapter.add(new CameraSpinnerAdapter.CameraItem("Test", "Test"));
        spinnerAdapter.add(new CameraSpinnerAdapter.CameraItem("Test", "Test"));

        spinner.setAdapter(spinnerAdapter);

        provider = new PhotoProvider(this);
        adapter = new PhotoListAdapter(photos);
        adapter.setItemClickListener(this);

        recyclerView = findViewById(R.id.photo_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        provider.getPhotos(1, "mast", this);
    }

    @Override
    public void onItemClick(View v, Photo photo) {
        Intent detailIntent = new Intent(getApplicationContext(), DetailActivity.class);
        detailIntent.putExtra(DetailActivity.PHOTO_ARG, photo);

        startActivity(detailIntent);
    }

    @Override
    public void onProviderSuccess(List<Photo> newPhotos) {
        photos.addAll(newPhotos);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onProviderFailed(VolleyError error) {
        Log.e("MAIN_ACTIVITY", error.getLocalizedMessage());
    }
}
