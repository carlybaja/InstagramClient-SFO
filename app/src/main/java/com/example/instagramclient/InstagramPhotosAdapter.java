package com.example.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by carlybaja on 6/8/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    // Context, Data Source
    public InstagramPhotosAdapter(Context context,  List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // get the data item for this position
        InstagramPhoto photo = getItem(position);
        // Check if we are using a recycled view, if not we need ot inflate
        if (convertView ==null ){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.items_photo,parent,false);
        }

        //Lookups the views for populating the data (image, caption)
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhotos);


        //insert the model data into the view items
        tvCaption.setText(photo.caption);
        // Clear out the imageView
        ivPhoto.setImageResource(0);
        // insert the image using picasso
        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);

        //return the created item as a view
        return convertView;

    }
}
