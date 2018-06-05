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

    public void getPhotos(final Filter filter) {
        Uri uri = baseUri.buildUpon()
                .appendQueryParameter("sol", filter.getSol() + "")
                .appendQueryParameter("page", filter.getPage() + "")
                .appendQueryParameter("api_key", "0ZBjXZ07Yeyh14xMENH7bU2YVqrbI1TsgNdzlDPG")
                .build();

        if(filter.getCamera() != null && filter.getCamera().length() > 0) {
            uri = uri.buildUpon().appendQueryParameter("camera", filter.getCamera()).build();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, uri.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Photo> result = formatResponse(response);
                        listener.onProviderSuccess(result, filter);
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

    private List<Photo> formatResponse(JSONObject object) {
        List<Photo> result = new ArrayList<>();

        try {
            JSONArray rawArray = object.getJSONArray("photos");

            for(int i = 0; i < rawArray.length(); i++) {
                result.add(new Photo().formatObject(rawArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static class Filter {
        private int sol = 1000;
        private int page = 1;
        private String camera;

        public int getSol() {
            return sol;
        }

        public void setSol(int sol) {
            this.sol = sol;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public String getCamera() {
            return camera;
        }

        public void setCamera(String camera) {
            this.camera = camera;
        }
    }

    public interface ProviderListener {
        void onProviderSuccess(List<Photo> photos, Filter filter);
        void onProviderFailed(VolleyError error);
    }
}
