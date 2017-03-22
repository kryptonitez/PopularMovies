package us.devtist.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chris.bounds on 3/22/2017.
 */

public class JsonUtils {


    public ArrayList<HashMap<String, String>> saveToArrayHashMap(String jsonResults, String nodeName, String[] resultNames){
        ArrayList<HashMap<String, String>> dataList;
        dataList = new ArrayList<>();
        if(!jsonResults.equals("")){
            try{
                JSONObject jsonObject = new JSONObject(jsonResults);
                JSONArray jsonArray = jsonObject.getJSONArray(nodeName);
                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject jdata = jsonArray.getJSONObject(i);
                    HashMap<String, String> data = new HashMap<>();
                    for(int j=0; j < resultNames.length; j++){
                        data.put(resultNames[j],jdata.getString(resultNames[j]));
                    }
                    Log.v("Hash data", jdata.toString());
                    dataList.add(data);
                }
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return dataList;
    }

    public ArrayList<String> saveToStringArray (String jsonResults, String nodeName, String resultToSave){
        ArrayList<String> dataList = new ArrayList<String>();
        if(!jsonResults.equals("")){
            try {
                JSONObject jsonObject = new JSONObject(jsonResults);
                JSONArray jsonArray = jsonObject.getJSONArray(nodeName);
                for(int i=0; i < jsonArray.length(); i++) {
                    JSONObject jdata = jsonArray.getJSONObject(i);
                    Log.v("String data", jdata.getString(resultToSave));
                    dataList.add(jdata.getString(resultToSave));
                }
                } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return dataList;
    }
}
