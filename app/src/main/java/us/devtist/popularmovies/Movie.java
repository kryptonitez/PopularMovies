package us.devtist.popularmovies;

import android.content.Context;

/**
 * Created by chris on 3/23/2017.
 */

public class Movie {

    private String mTitle;
    private String mPosterPath;
    private Integer mRating;
    private Integer mId;
    private Integer mRuntime;
    private String mOverview;
    private String mReleaseYear;

    public void setTitle(String title){
        mTitle = title;
    }

    public void setPosterPath(String poster){
        mPosterPath = poster;
    }

    public void setRating(Integer rating) {
        mRating = rating;
    }

    public void setId(Integer id){
        mId = id;
    }

    public void setRuntime(Integer runtime){
        mRuntime = runtime;
    }

    public void setOverview(String overview){
        mOverview = overview;
    }

    public void setReleaseYear(String year){ mReleaseYear = year;}

    public String getTitle(){
        return mTitle;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public Integer getRating(){
        return mRating;
    }

    public Integer getId(){
        return mId;
    }

    public Integer getRuntime(){
        return mRuntime;
    }

    public String getOverview(){
        return mOverview;
    }

    public String getReleaseYear() {return mReleaseYear;}
}
