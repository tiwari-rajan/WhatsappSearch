package com.rajan.findwhatsapp;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * * 25/11/15.
 */
public class CountryList {

    private ArrayList<Country> list;

    public ArrayList<Country> getList() {
        return list;
    }

    public void setList(ArrayList<Country> list) {
        this.list = list;
    }
    public static <T> T fromJson(Class<T> tClass, String s) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(s, tClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String emptyJson() {
        return "{}";
    }
}
