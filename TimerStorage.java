package com.example.timerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TimerStorage {

    private static final String PREF_NAME = "timer_prefs";
    private static final String TIMER_PACKS_KEY = "timer_packs";
    private static final String TAG = "TimerStorage";

    public static ArrayList<TimerPack> loadTimerPacks(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(TIMER_PACKS_KEY, null);
        
        if (json != null) {
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<TimerPack>>() {}.getType();
                return gson.fromJson(json, type);
            } catch (Exception e) {
                Log.e(TAG, "Error loading timer packs", e);
            }
        }
        
        return new ArrayList<>();
    }

    public static void saveTimerPacks(Context context, ArrayList<TimerPack> timerPacks) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        
        try {
            Gson gson = new Gson();
            String json = gson.toJson(timerPacks);
            editor.putString(TIMER_PACKS_KEY, json);
            editor.apply();
        } catch (Exception e) {
            Log.e(TAG, "Error saving timer packs", e);
        }
    }

    public static void saveTimerPack(Context context, TimerPack timerPack, int index) {
        ArrayList<TimerPack> timerPacks = loadTimerPacks(context);
        
        if (index >= 0 && index < timerPacks.size()) {
            timerPacks.set(index, timerPack);
            saveTimerPacks(context, timerPacks);
        }
    }

    public static void deleteTimerPack(Context context, int index) {
        ArrayList<TimerPack> timerPacks = loadTimerPacks(context);
        
        if (index >= 0 && index < timerPacks.size()) {
            timerPacks.remove(index);
            saveTimerPacks(context, timerPacks);
        }
    }
}