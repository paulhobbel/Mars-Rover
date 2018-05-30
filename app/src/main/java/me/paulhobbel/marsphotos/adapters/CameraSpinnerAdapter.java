package me.paulhobbel.marsphotos.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class CameraSpinnerAdapter extends ArrayAdapter<CameraSpinnerAdapter.CameraItem> {

    public CameraSpinnerAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_spinner_item);
    }

    public CameraSpinnerAdapter(@NonNull Context context, @NonNull List<CameraItem> objects) {
        super(context, android.R.layout.simple_spinner_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        return view;
    }

    public static class CameraItem {
        String key;
        String name;

        public CameraItem(String key, String name) {
            this.key = key;
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "CameraItem{" +
                    "key='" + key + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
