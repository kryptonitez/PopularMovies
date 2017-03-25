package us.devtist.popularmovies;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by chris.bounds on 3/22/2017.
 */

public class GridViewAdapater extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> imageUrls;
    private Boolean isLandscape;

    public GridViewAdapater(Context c, ArrayList<String> s) {
        mContext = c;
        imageUrls = s;
        isLandscape = mContext.getResources().getBoolean(R.bool.is_landscape);
    }

    public int getCount() {
        return imageUrls.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        Log.v("Screen orientation", String.valueOf(mContext.getResources().getBoolean(R.bool.is_landscape)));
        Log.v("DisplayMetricsWidth" , String.valueOf(mContext.getResources().getDisplayMetrics().widthPixels));
        Log.v("DisplayMetricsHeight" , String.valueOf(mContext.getResources().getDisplayMetrics().heightPixels));
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            int displayHeight = mContext.getResources().getDisplayMetrics().heightPixels;
            int displayWidth = mContext.getResources().getDisplayMetrics().widthPixels;
            Log.v("displayHeight", String.valueOf(displayHeight));
            Log.v("displayWidth", String.valueOf(displayWidth));
            Resources resources = mContext.getResources();

            if( resources.getBoolean(R.bool.is_landscape)!= true) {
                imageView.setLayoutParams(new GridView.LayoutParams(displayWidth / 2, displayHeight / 2));
            } else {
                imageView.setLayoutParams(new GridView.LayoutParams(displayWidth / 2, displayHeight));
            }
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso
                .with(mContext)
                .load(imageUrls.get(position))
                .fit()
                .into(imageView);
        return imageView;
    }
}