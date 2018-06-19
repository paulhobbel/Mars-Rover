package me.paulhobbel.marsphotos.providers.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Rover implements JSONModel<Rover>, Parcelable {

    public static final Creator<Rover> CREATOR = new Creator<Rover>() {
        @Override
        public Rover createFromParcel(Parcel in) {
            return new Rover(in);
        }

        @Override
        public Rover[] newArray(int size) {
            return new Rover[size];
        }
    };

    private int id;
    private String name;
    private String status;

    private String landingDate;
    private String launchDate;
    private String maxDate;

    private int maxSol;
    private int totalPhotos;

    private final List<Camera> cameras;

    public Rover() {
        cameras = new ArrayList<>();
    }

    protected Rover(Parcel in) {
        id = in.readInt();
        name = in.readString();
        status = in.readString();
        landingDate = in.readString();
        launchDate = in.readString();
        maxDate = in.readString();
        maxSol = in.readInt();
        totalPhotos = in.readInt();
        cameras = in.createTypedArrayList(Camera.CREATOR);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLandingDate() {
        return landingDate;
    }

    public void setLandingDate(String landingDate) {
        this.landingDate = landingDate;
    }

    public String getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

    public int getMaxSol() {
        return maxSol;
    }

    public void setMaxSol(int maxSol) {
        this.maxSol = maxSol;
    }

    public int getTotalPhotos() {
        return totalPhotos;
    }

    public void setTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

    public List<Camera> getCameras() {
        return cameras;
    }

    public void addCamera(Camera camera) {
        this.cameras.add(camera);
    }

    public void removeCamera(Camera camera) {
        this.cameras.remove(camera);
    }

    @Override
    public Rover formatObject(JSONObject object) throws JSONException {
        Rover rover = new Rover();

        id = object.getInt("id");
        name = object.getString("name");
        status = object.getString("status");

        landingDate = object.getString("landing_date");
        launchDate = object.getString("launch_date");
        maxDate = object.getString("max_date");

        maxSol = object.getInt("max_sol");
        totalPhotos = object.getInt("total_photos");

        JSONArray rawCameras = object.getJSONArray("cameras");
        for(int i = 0; i < rawCameras.length(); i++) {
            addCamera(new Camera().formatObject(rawCameras.getJSONObject(i)));
        }

        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(status);
        dest.writeString(landingDate);
        dest.writeString(launchDate);
        dest.writeString(maxDate);
        dest.writeInt(maxSol);
        dest.writeInt(totalPhotos);
        dest.writeTypedList(cameras);
    }

    @Override
    public String toString() {
        return "Rover{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", landingDate='" + landingDate + '\'' +
                ", launchDate='" + launchDate + '\'' +
                ", maxDate='" + maxDate + '\'' +
                ", maxSol=" + maxSol +
                ", totalPhotos=" + totalPhotos +
                ", cameras=" + cameras +
                '}';
    }
}
