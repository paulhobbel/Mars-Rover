package me.paulhobbel.marsphotos.activities;

import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import me.paulhobbel.marsphotos.R;
import me.paulhobbel.marsphotos.adapters.CameraSpinnerAdapter;
import me.paulhobbel.marsphotos.adapters.CameraSpinnerAdapter.CameraItem;
import me.paulhobbel.marsphotos.fragments.PhotoListFragment;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = findViewById(R.id.photo_camera_spinner);

        CameraSpinnerAdapter spinnerAdapter = new CameraSpinnerAdapter(this);
        spinnerAdapter.add(new CameraItem(null, "All Camera's", 1100));
        spinnerAdapter.add(new CameraItem("fhaz", "Front Hazard Avoidance Camera", 400));
        spinnerAdapter.add(new CameraItem("rhaz", "Rear Hazard Avoidance Camera", 300));
        spinnerAdapter.add(new CameraItem("mast", "Mast Camera"));
        spinnerAdapter.add(new CameraItem("chemcam", "Chemistry and Camera Complex", 100));
        spinnerAdapter.add(new CameraItem("mahli", "Mars Hand Lens Imager", 400));
        spinnerAdapter.add(new CameraItem("mardi", "Mars Descent Imager", 100));
        spinnerAdapter.add(new CameraItem("navcam", "Navigation Camera", 1100));

        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CameraItem item = (CameraItem) parent.getItemAtPosition(position);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.photo_container,
                PhotoListFragment.newInstance(item.getSol(), item.getKey())).commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
