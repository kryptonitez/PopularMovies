package us.devtist.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chris.bounds on 3/22/2017.
 */

public class JsonUtils {


    public ArrayList<HashMap<String, String>> saveToArrayHashMap(String jsonResults, String nodeName, String[] resultNames) {
        ArrayList<HashMap<String, String>> dataList;
        dataList = new ArrayList<>();
        if (!jsonResults.equals("")) {
            try {
                JSONObject jsonObject = new JSONObject(jsonResults);
                JSONArray jsonArray = jsonObject.getJSONArray(nodeName);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jdata = jsonArray.getJSONObject(i);
                    HashMap<String, String> data = new HashMap<>();
                    for (int j = 0; j < resultNames.length; j++) {
                        data.put(resultNames[j], jdata.getString(resultNames[j]));
                    }
                    Log.v("Hash data", jdata.toString());
                    dataList.add(data);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }

    public ArrayList<String> saveToStringArray(String jsonResults, String nodeName, String resultToSave) {
        ArrayList<String> dataList = new ArrayList<String>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResults);
            JSONArray jsonArray = jsonObject.getJSONArray(nodeName);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jdata = jsonArray.getJSONObject(i);
                Log.v("String data", jdata.getString(resultToSave));
                dataList.add(jdata.getString(resultToSave));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public String saveToString(String jsonResults, String resultToSave) {
        String data = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonResults);
            data = jsonObject.getString(resultToSave);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<Integer> saveToIntArray(String jsonResults, String nodeName, String resultToSave) {
        ArrayList<Integer> dataList = new ArrayList<Integer>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResults);
            JSONArray jsonArray = jsonObject.getJSONArray(nodeName);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jdata = jsonArray.getJSONObject(i);
                Log.v("String data", jdata.getString(resultToSave));
                dataList.add(jdata.getInt(resultToSave));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public Integer saveToInteger(String jsonResults, String resultToSave) {
        Integer data = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonResults);
            data = jsonObject.getInt(resultToSave);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
