package us.devtist.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

/** PLEASE REMEMBER TO FILL OUT THE apiKey or you will get an error
 *
 */
public class MainActivity extends AppCompatActivity {

    private ArrayList<String> movieImg;
    private ArrayList<Integer> movieIds;
    private GridView gridView;
    private String sortBySelection;
    private final static String apiKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridview);

        sortBySelection = null;
        new HttpQueryTask().execute();
        if (savedInstanceState != null) {
            gridView.setSelection(savedInstanceState.getInt("gridView", 0));
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                Log.v("movie id mainactivity", String.valueOf(movieIds.get(position)));
                intent.putExtra("EXTRA_INT", movieIds.get(position));
                intent.putExtra("EXTRA_APIKEY", apiKey);
                startActivity(intent);
            }
        });

    }

    public class HttpQueryTask extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... params) {
            String searchResults = null;
            searchResults = getSortByStatus();
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

        private String getSortByStatus() {
            String searchResults = null;
            if (sortBySelection != null) {
                switch (sortBySelection) {
                    case "mostpopular":
                        Uri builtUri = Uri.parse("https://api.themoviedb.org/3/movie/popular").buildUpon()
                                .appendQueryParameter("api_key", apiKey)
                                .appendQueryParameter("language", "en-US")
                                .build();

                        URL popularMovieUrl = NetworkUtils.buildUrl(builtUri);

                        try {
                            searchResults = NetworkUtils.HttpMethod("GET", popularMovieUrl);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.e("Search Results", searchResults);
                        movieIds = new JsonUtils().saveToIntArray(searchResults, "results", "id");
                        return searchResults;

                    case "toprated":
                        Uri topRatedUri = Uri.parse("https://api.themoviedb.org/3/movie/top_rated").buildUpon()
                                .appendQueryParameter("api_key", apiKey)
                                .appendQueryParameter("language", "en-US")
                                .build();

                        URL topRatedUrl = NetworkUtils.buildUrl(topRatedUri);

                        try {
                            searchResults = NetworkUtils.HttpMethod("GET", topRatedUrl);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.e("Search Results", searchResults);
                        movieIds = new JsonUtils().saveToIntArray(searchResults, "results", "id");
                        return searchResults;
                }
            }
                    Uri defaultUri = Uri.parse("https://api.themoviedb.org/3/movie/popular").buildUpon()
                            .appendQueryParameter("api_key",apiKey)
                            .appendQueryParameter("language", "en-US")
                            .build();

                    URL defaultUrl = NetworkUtils.buildUrl(defaultUri);

                    try {
                        searchResults = NetworkUtils.HttpMethod("GET",defaultUrl);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e("Search Results", searchResults);
                    movieIds = new JsonUtils().saveToIntArray(searchResults, "results", "id");
            return  searchResults;
            }
        }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("gridView", gridView.getFirstVisiblePosition());
        Log.v("outState", String.valueOf(gridView.getFirstVisiblePosition()));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mostpopular:
                sortBySelection = "mostpopular";
                new HttpQueryTask().execute();
                return true;
            case R.id.toprated:
                sortBySelection = "toprated";
                new HttpQueryTask().execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
