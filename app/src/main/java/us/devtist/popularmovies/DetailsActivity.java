package us.devtist.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static android.R.id.message;


public class DetailsActivity extends AppCompatActivity {

    private ArrayList<String> movieIds;
    private ArrayList<String> trailerKeys;
    private TextView textView;
    private Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        position = intent.getIntExtra("EXTRA_INT", 0);

        textView = (TextView)findViewById(R.id.textViewRating);

        new MovieInfoQuery().execute();

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
                searchResults = NetworkUtils.HttpMethod("GET",popularMovieUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("Search Results", searchResults);
            movieIds = new JsonUtils().saveToStringArray(searchResults, "results", "id");
            Uri trailerUri = Uri.parse("https://api.themoviedb.org/3/movie/" + movieIds.get(position).toString()+ "/videos?api_key=1d0b6eb47a92279860bb5060f9ae1129&language=en-US");

            return  searchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(movieIds.get(position).toString());
        }
    }
}
