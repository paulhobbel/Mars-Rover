package me.paulhobbel.marsphotos.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import me.paulhobbel.marsphotos.R;
import me.paulhobbel.marsphotos.adapters.CameraSpinnerAdapter;
import me.paulhobbel.marsphotos.adapters.PhotoListAdapter;
import me.paulhobbel.marsphotos.fragments.PhotoListFragment;
import me.paulhobbel.marsphotos.providers.PhotoProvider;
import me.paulhobbel.marsphotos.providers.models.Photo;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.photo_camera_spinner);

        CameraSpinnerAdapter spinnerAdapter = new CameraSpinnerAdapter(this);
        spinnerAdapter.add(new CameraSpinnerAdapter.CameraItem(null, "All Camera's"));
        spinnerAdapter.add(new CameraSpinnerAdapter.CameraItem("fhaz", "Front Hazard Avoidance Camera"));
        spinnerAdapter.add(new CameraSpinnerAdapter.CameraItem("rhaz", "Rear Hazard Avoidance Camera"));
        spinnerAdapter.add(new CameraSpinnerAdapter.CameraItem("mast", "Mast Camera"));
        spinnerAdapter.add(new CameraSpinnerAdapter.CameraItem("chemcam", "Chemistry and Camera Complex"));
        spinnerAdapter.add(new CameraSpinnerAdapter.CameraItem("mahli", "Mars Hand Lens Imager"));
        spinnerAdapter.add(new CameraSpinnerAdapter.CameraItem("mardi", "Mars Descent Imager"));
        spinnerAdapter.add(new CameraSpinnerAdapter.CameraItem("navcam", "Navigation Camera"));

        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CameraSpinnerAdapter.CameraItem item = (CameraSpinnerAdapter.CameraItem) parent.getItemAtPosition(position);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.photo_container, PhotoListFragment.newInstance(item.getKey())).commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
