package us.devtist.popularmovies;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by chris.bounds on 3/22/2017.
 */

public class GridViewAdapater extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> imageUrls;

    public GridViewAdapater(Context c, ArrayList<String> s) {
        mContext = c;
        imageUrls = s;
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
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            int displayHeight = new DisplayMetrics().getDisplayHeight(mContext);
            int displayWidth = new DisplayMetrics().getDisplayWidth(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(displayWidth/4 + 56, displayHeight/3));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
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