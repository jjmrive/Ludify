package com.jjmrive.ludify;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jjmrive.ludify.visit.Visit;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataHolder {

    private static final String VISITSLIST = "VisitsList";

    private static List<Visit> visitsList = new ArrayList<>();

    public static List<Visit> getVisitsList(){
        return visitsList;
    }

    public static void setVisitsList(List<Visit> visitsList){
        DataHolder.visitsList=visitsList;
    }

    public static void loadVisitsList(Context context){
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = appSharedPrefs.getString(VISITSLIST, "");
        Type type = new TypeToken<List<Visit>>() {
        }.getType();
        List<Visit> visits = gson.fromJson(json, type);
        if (visits != null){
            DataHolder.setVisitsList(visits);
        }
    }

    public static void saveVisitsList(Context context){
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String jsonVisitList = gson.toJson(DataHolder.getVisitsList());
        prefsEditor.putString(VISITSLIST, jsonVisitList);
        prefsEditor.commit();
    }

}
