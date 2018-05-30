package me.paulhobbel.marsphotos.providers.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Camera implements JSONModel<Camera>, Serializable {
    private int id;
    private String name;
    private String fullName;

    public Camera() { }

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public Camera formatObject(JSONObject object) throws JSONException {
        if(object.has("id")) {
            id = object.getInt("id");
        }

        name = object.getString("name");
        fullName = object.getString("full_name");

        return this;
    }

    @Override
    public String toString() {
        return "Camera{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
