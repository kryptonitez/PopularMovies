package us.devtist.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> movieImg;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridview);
        Uri builtUri = Uri.parse("https://api.themoviedb.org/3/movie/popular").buildUpon()
                .appendQueryParameter("api_key", "1d0b6eb47a92279860bb5060f9ae1129")
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("page", "1")
                .build();

        new HttpQueryTask().execute(builtUri);

    }

    public class HttpQueryTask extends AsyncTask<Uri, Void, String> {


        protected String doInBackground(Uri... params) {
            URL popularMovieUrl = NetworkUtils.buildUrl(params[0]);
            String searchResults = null;
            try {
                searchResults = NetworkUtils.HttpMethod("GET",popularMovieUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("Search Results", searchResults);
            return  searchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            movieImg = new JsonUtils().saveToStringArray(s, "results", "poster_path");
            for (int i = 0; i < movieImg.size(); i++) {
                movieImg.set(i, "https://image.tmdb.org/t/p/w500" + movieImg.get(i));
                Log.v("Movie URLs" , movieImg.get(i));
            }
            gridView.setAdapter(new GridViewAdapater(MainActivity.this, movieImg));
        }
    }
}
