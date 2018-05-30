package me.paulhobbel.marsphotos.providers.models;

import org.json.JSONException;
import org.json.JSONObject;

public interface JSONModel<T> {
    T formatObject(JSONObject object) throws JSONException;
}
