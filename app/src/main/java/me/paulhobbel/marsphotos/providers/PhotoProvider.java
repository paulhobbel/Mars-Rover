package me.paulhobbel.marsphotos.providers;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.paulhobbel.marsphotos.providers.models.Photo;

public class PhotoProvider {

    private final Uri baseUri = Uri.parse("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos");

    private final RequestQueue queue;
    private final ProviderListener listener;

    public PhotoProvider(Context context, ProviderListener listener) {
        this.queue = Volley.newRequestQueue(context);
        this.listener = listener;
    }

    public void getPhotos(int page, String camera) {
        Uri uri = baseUri.buildUpon()
                .appendQueryParameter("sol", "1000")
                .appendQueryParameter("page", page + "")
                .appendQueryParameter("api_key", "0ZBjXZ07Yeyh14xMENH7bU2YVqrbI1TsgNdzlDPG")
                .build();

        if(camera != null && camera.length() > 0) {
            uri = uri.buildUpon().appendQueryParameter("camera", camera).build();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, uri.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onProviderSuccess(formatResponse(response));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onProviderFailed(error);
                    }
                });

        this.queue.add(request);
    }

    public List<Photo> formatResponse(JSONObject object) {
        List<Photo> result = new ArrayList<>();

        try {
            JSONArray rawArray = object.getJSONArray("photos");

            for(int i = 0; i < rawArray.length(); i++) {
                Photo photo = new Photo().formatObject(rawArray.getJSONObject(i));
                result.add(photo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public interface ProviderListener {
        void onProviderSuccess(List<Photo> photos);
        void onProviderFailed(VolleyError error);
    }
}
