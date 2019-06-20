package assignment2.sages.au.edu.utas.slistapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Sage on 5/28/2018.
 */

public class customAdapter extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    public customAdapter(Activity context,
                      String[] web, Integer[] imageId) {
        super(context, R.layout.activity_add_item, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }
    @Override
    @NonNull
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.activity_add_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.name);
        TextView txtQuant = (TextView) rowView.findViewById(R.id.quant);
        TextView txtPrice = (TextView) rowView.findViewById(R.id.price);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web[position]);
        txtQuant.setText(web[position]);
        txtPrice.setText(web[position]);


        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}