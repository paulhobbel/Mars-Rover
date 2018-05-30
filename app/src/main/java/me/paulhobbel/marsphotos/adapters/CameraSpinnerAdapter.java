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

public class CameraSpinnerAdapter extends ArrayAdapter<CameraSpinnerAdapter.CameraItem> implements AdapterView.OnItemSelectedListener {

    private OnItemSelectedListener itemSelectedListener;

    public CameraSpinnerAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_spinner_dropdown_item);
    }

    public CameraSpinnerAdapter(@NonNull Context context, @NonNull List<CameraItem> objects) {
        super(context, android.R.layout.simple_spinner_dropdown_item, objects);

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

    public void setItemSelectedListener(OnItemSelectedListener listener) {
        itemSelectedListener = listener;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(itemSelectedListener != null) {
            itemSelectedListener.onItemSelected(view, getItem(position));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

    public interface OnItemSelectedListener {
        void onItemSelected(View view, CameraItem item);
    }
}
