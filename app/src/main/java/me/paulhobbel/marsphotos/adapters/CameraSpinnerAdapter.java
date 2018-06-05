package me.paulhobbel.marsphotos.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CameraSpinnerAdapter extends ArrayAdapter<CameraSpinnerAdapter.CameraItem> {

    public CameraSpinnerAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_spinner_dropdown_item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        textView.setText(getItem(position).name);
        return textView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        textView.setText(getItem(position).name);
        return textView;
    }

    public static class CameraItem {
        int sol;
        String key;
        String name;

        public CameraItem(String key, String name) {
            this(key, name, 1000);
        }

        public CameraItem(String key, String name, int sol) {
            this.sol = sol;
            this.key = key;
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public int getSol() {
            return sol;
        }

        public String getName() {
            return name;
        }
    }
}
