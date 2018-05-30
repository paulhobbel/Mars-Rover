package me.paulhobbel.marsphotos.providers.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Photo implements JSONModel<Photo>, Serializable {
    private int id;
    private int sol;

    private Camera camera;
    private String image;
    private String earthDate;
    private Rover rover;

    public Photo() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSol() {
        return sol;
    }

    public void setSol(int sol) {
        this.sol = sol;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEarthDate() {
        return earthDate;
    }

    public void setEarthDate(String earthDate) {
        this.earthDate = earthDate;
    }

    public Rover getRover() {
        return rover;
    }

    public void setRover(Rover rover) {
        this.rover = rover;
    }

    @Override
    public Photo formatObject(JSONObject object) throws JSONException {
        id = object.getInt("id");
        sol = object.getInt("sol");

        camera = new Camera().formatObject(object.getJSONObject("camera"));
        image = object.getString("img_src");
        earthDate = object.getString("earth_date");
        rover = new Rover().formatObject(object.getJSONObject("rover"));

        return this;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", sol=" + sol +
                ", camera=" + camera +
                ", image='" + image + '\'' +
                ", earthDate='" + earthDate + '\'' +
                ", rover=" + rover +
                '}';
    }
}
