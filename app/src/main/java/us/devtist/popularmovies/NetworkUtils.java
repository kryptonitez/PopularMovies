package us.devtist.popularmovies;

/**
 * Created by chris.bounds on 3/22/2017.
 */

import android.net.Uri;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    public static URL buildUrl(Uri builtUri) {
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static String HttpMethod(String method, URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        switch (method){
            case "GET":  urlConnection.setRequestMethod("GET");
                break;
            case "POST": urlConnection.setRequestMethod("POST");
                break;
            case "PUT": urlConnection.setRequestMethod("PUT");
                break;
        }

        int responseCode = urlConnection.getResponseCode();
        Log.v("Response code", String.valueOf(responseCode));

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}

