package us.devtist.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static android.R.id.list;


public class DetailsActivity extends AppCompatActivity {

    private ArrayList<String> movieIds;
    private ArrayList<String> trailerKeys;
    private TextView textViewYear;
    private TextView textViewLength;
    private TextView textViewSummary;
    private TextView textViewRating;
    private String movieTitle;
    private String movieSummary;
    private String movieLength;
    private String movieRating;
    private Integer position;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        position = intent.getIntExtra("EXTRA_INT", 0);

        textViewYear = (TextView) findViewById(R.id.textViewYear);
        textViewLength = (TextView) findViewById(R.id.textViewLength);
        textViewSummary = (TextView) findViewById(R.id.textViewSummary);
        textViewRating = (TextView) findViewById(R.id.textViewRating);

        listView = (ListView) findViewById(R.id.list);
        new MovieInfoQuery().execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(DetailsActivity.this, "You Clicked at " + trailerKeys.get(position), Toast.LENGTH_SHORT).show();

            }
        });

    }


    public class MovieInfoQuery extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... params) {
            Uri builtUri = Uri.parse("https://api.themoviedb.org/3/movie/popular").buildUpon()
                    .appendQueryParameter("api_key", "")
                    .appendQueryParameter("language", "en-US")
                    .appendQueryParameter("page", "1")
                    .build();

            URL popularMovieUrl = NetworkUtils.buildUrl(builtUri);
            String searchResults = null;
            try {
                searchResults = NetworkUtils.HttpMethod("GET", popularMovieUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("Search Results", searchResults);
            movieIds = new JsonUtils().saveToStringArray(searchResults, "results", "id");
            Uri trailerUri = Uri.parse("https://api.themoviedb.org/3/movie/" + movieIds.get(position).toString() + "/videos?api_key=1d0b6eb47a92279860bb5060f9ae1129&language=en-US").buildUpon().build();
            URL trailerUrl = NetworkUtils.buildUrl(trailerUri);
            String trailerResults = null;
            try {
                trailerResults = NetworkUtils.HttpMethod("GET", trailerUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            trailerKeys = new JsonUtils().saveToStringArray(trailerResults, "results", "key");

            String movieDetailResults = null;
            try {
                Uri movieUri = Uri.parse("https://api.themoviedb.org/3/movie/" + movieIds.get(position).toString() + "?api_key=1d0b6eb47a92279860bb5060f9ae1129&language=en-US").buildUpon().build();
                URL movieDetailsUrl = NetworkUtils.buildUrl(movieUri);
                movieDetailResults = NetworkUtils.HttpMethod("GET", movieDetailsUrl);
                movieTitle = new JsonUtils().saveToString(movieDetailResults, "original_title");

            } catch (IOException e){
                e.printStackTrace();
            }
            return searchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textViewLength.setText();
            CustomListView adapter = new
                    CustomListView(DetailsActivity.this, trailerKeys);
            listView.setAdapter(adapter);
        }
    }
}


