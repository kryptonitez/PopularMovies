package us.devtist.popularmovies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chris on 3/22/2017.
 */

public class CustomListView extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> trailer;
    public CustomListView(Activity context,
                          ArrayList<String> trailer) {
        super(context, R.layout.listview, trailer);
        this.context = context;
        this.trailer = trailer;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.listview, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(trailer.get(position));

        imageView.setImageResource(R.drawable.play_button);
        return rowView;
    }
}
