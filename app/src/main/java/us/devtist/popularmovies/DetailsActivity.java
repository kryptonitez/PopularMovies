package us.devtist.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static android.R.id.list;


public class DetailsActivity extends AppCompatActivity {

    private ArrayList<String> trailerKeys;
    private TextView textViewYear;
    private TextView textViewLength;
    private TextView textViewSummary;
    private TextView textViewRating;
    private ImageView imageViewPoster;
    private TextView textViewTitle;
    private String movieTitle;
    private String movieSummary;
    private String movieReleaseYear;
    private Integer movieLength;
    private Integer movieRating;
    private String moviePosterPath;
    private Integer movieId;
    private ListView listView;
    private String posterPathUrl;
    Parcelable state;
    Movie movie;
    private String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        movie = new Movie();
        movieId = intent.getIntExtra("EXTRA_INT", 0);
        apiKey = intent.getStringExtra("EXTRA_APIKEY");
        Log.v("apiKey", apiKey);

        textViewYear = (TextView) findViewById(R.id.textViewYear);
        textViewLength = (TextView) findViewById(R.id.textViewLength);
        textViewSummary = (TextView) findViewById(R.id.textViewSummary);
        textViewRating = (TextView) findViewById(R.id.textViewRating);
        textViewTitle = (TextView)findViewById(R.id.textViewTitle);
        imageViewPoster = (ImageView)findViewById(R.id.imageViewMovie);

        listView = (ListView) findViewById(R.id.listview_details);
        new MovieInfoQuery().execute();

        if (savedInstanceState != null) {
            Log.v("onCreate listView", String.valueOf(savedInstanceState.getInt("listView")));
            final int listIndex = savedInstanceState.getInt("listView");
            listView.clearFocus();
            listView.post(new Runnable() {
                @Override
                public void run() {
                    listView.requestFocusFromTouch();
                    listView.setSelection(listIndex);
                    listView.requestFocus();
                }
            });
        }

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

            Uri trailerUri = Uri.parse("https://api.themoviedb.org/3/movie/" + movieId + "/videos?api_key=" + apiKey + "&language=en-US").buildUpon().build();
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
                Uri movieUri = Uri.parse("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey + "&language=en-US").buildUpon().build();
                URL movieDetailsUrl = NetworkUtils.buildUrl(movieUri);
                movieDetailResults = NetworkUtils.HttpMethod("GET", movieDetailsUrl);
                movieTitle = new JsonUtils().saveToString(movieDetailResults, "original_title");
                movie.setTitle(movieTitle);
                movieId = new JsonUtils().saveToInteger(movieDetailResults, "id");
                movie.setId(movieId);
                moviePosterPath = new JsonUtils().saveToString(movieDetailResults, "poster_path");
                movie.setPosterPath(moviePosterPath);
                movieSummary = new JsonUtils().saveToString(movieDetailResults, "overview");
                movie.setOverview(movieSummary);
                movieLength = new JsonUtils().saveToInteger(movieDetailResults, "runtime");
                movie.setRuntime(movieLength);
                movieRating = new JsonUtils().saveToInteger(movieDetailResults, "vote_average");
                movie.setRating(movieRating);
                movieReleaseYear = new JsonUtils().saveToString(movieDetailResults, "release_date");
                movieReleaseYear = movieReleaseYear.substring(0,4);
                movie.setReleaseYear(movieReleaseYear);
                posterPathUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
                Log.v("MovieTitle", movie.getTitle());

            } catch (IOException e){
                e.printStackTrace();
            }
            return movieDetailResults;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            CustomListView adapter = new
                    CustomListView(DetailsActivity.this, trailerKeys);
            listView.setAdapter(adapter);
            textViewSummary.setText(movie.getOverview());
            textViewLength.setText(String.valueOf(movie.getRuntime()));
            textViewRating.setText(String.valueOf(movie.getRating()) + "/10");
            textViewTitle.setText(String.valueOf(movie.getTitle()));
            textViewYear.setText(movie.getReleaseYear());
            Picasso.with(DetailsActivity.this).load(posterPathUrl).into(imageViewPoster);

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        state = listView.onSaveInstanceState();
        outState.putParcelable("listView", state);
        outState.putInt("listView",listView.getFirstVisiblePosition());
    }
}


