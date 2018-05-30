package me.paulhobbel.marsphotos.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import me.paulhobbel.marsphotos.R;
import me.paulhobbel.marsphotos.activities.DetailActivity;
import me.paulhobbel.marsphotos.adapters.PhotoListAdapter;
import me.paulhobbel.marsphotos.providers.PhotoProvider;
import me.paulhobbel.marsphotos.providers.models.Photo;

public class PhotoListFragment extends Fragment implements PhotoListAdapter.OnItemClickListener,
        PhotoProvider.ProviderListener {

    public static final String ARG_CAMERA = "arg_camera";

    private enum State { UNINITIALIZED, LOADING, LOADED, FULLY_LOADED }

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ConstraintLayout progressOverlay;

    private List<Photo> photos = new ArrayList<>();
    private PhotoListAdapter photoAdapter;
    private PhotoProvider provider;

    private State state = State.UNINITIALIZED;

    private int page = 1;
    private String camera;

    public static PhotoListFragment newInstance(String camera) {
        PhotoListFragment fragment = new PhotoListFragment();

        Bundle arguments = new Bundle();
        arguments.putString(ARG_CAMERA, camera);

        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        provider = new PhotoProvider(getActivity(), this);

        if(getArguments() != null) {
            camera = getArguments().getString(ARG_CAMERA);
        } else {
            camera = "all";
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_list, container, false);

        layoutManager = new LinearLayoutManager(getActivity());

        photoAdapter = new PhotoListAdapter(photos);
        photoAdapter.setItemClickListener(this);

        progressOverlay = view.findViewById(R.id.progress_overlay);

        recyclerView = view.findViewById(R.id.photo_list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(photoAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(state == State.LOADED){
                    int visibleItems = layoutManager.getChildCount();
                    int totalItems = layoutManager.getItemCount();
                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                    if ((totalItems - visibleItems) <= firstVisibleItem + 10) {
                        setState(State.LOADING);
                        provider.getPhotos(page++, camera);
                        Log.d("PHOTO_LIST", "onScrolled: " + "More content available, fetching next page, STATE = " + state);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setState(State state) {
        if(this.state != state) {
            this.state = state;
            updateUi();
        }
    }

    public void updateUi() {
        switch (state) {
            case UNINITIALIZED:
                progressOverlay.setVisibility(View.VISIBLE);
            case LOADED:
                progressOverlay.setVisibility(View.GONE);
            case FULLY_LOADED:
                
        }
    }

    @Override
    public void onItemClick(View v, Photo photo) {
        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        detailIntent.putExtra(DetailActivity.PHOTO_ARG, photo);

        startActivity(detailIntent);
    }

    @Override
    public void onProviderSuccess(List<Photo> newPhotos) {
        photos.addAll(newPhotos);
        photoAdapter.notifyDataSetChanged();
        setState(newPhotos.size() > 0 ? State.LOADED : State.FULLY_LOADED);

        Log.d("PHOTO_LIST", "onProviderSuccess: Added " + newPhotos.size() + " photos, STATE = " + state);
    }

    @Override
    public void onProviderFailed(VolleyError error) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(photoAdapter.getItemCount() == 0) {
            Log.d("PHOTO_LIST", "onActivityCreated: Populating RecyclerView, STATE = " + state);
            setState(State.UNINITIALIZED);
            provider.getPhotos(page++, camera);
        }
    }
}
