package us.devtist.popularmovies;

import android.content.Context;

/**
 * Created by chris.bounds on 3/22/2017.
 */

public class DisplayMetrics {

    public Integer getDisplayHeight(Context c){
        int height = c.getResources().getDisplayMetrics().heightPixels;
        return height;
    }

    public Integer getDisplayWidth(Context c){
        int width = c.getResources().getDisplayMetrics().heightPixels;
        return width;
    }
}
