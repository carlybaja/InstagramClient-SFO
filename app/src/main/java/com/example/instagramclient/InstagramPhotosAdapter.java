package com.example.instagramclient;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;
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
 * Updated by carlybaja on 2/4/16.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    // View lookup cache
    private static class ViewHolder {
        TextView  caption;
        ImageView profilePhoto;
        TextView  username;
        TextView  fullname;
        TextView  comment;
        TextView  commentBy;
        ImageView photoComments;
        TextView  commentDate;
        TextView  likeCounts;
        TextView  totalComments;
        TextView  likes;
    }

    // Context, Data Source
    public InstagramPhotosAdapter(Context context,  List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // get the data item for this position
        InstagramPhoto photo = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView ==null ){

            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.items_photo, parent, false);
            viewHolder.caption = (TextView) convertView.findViewById(R.id.tvCaption);
            viewHolder.profilePhoto = (ImageView) convertView.findViewById(R.id.ivPhotos);
            viewHolder.username = (TextView) convertView.findViewById(R.id.tvUsername);
            viewHolder.fullname = (TextView) convertView.findViewById(R.id.tvFullname);
            viewHolder.comment = (TextView) convertView.findViewById(R.id.tvComment);
            viewHolder.commentBy = (TextView) convertView.findViewById(R.id.tvComment);
            viewHolder.photoComments = (ImageView) convertView.findViewById(R.id.ivPhotoComments);
            viewHolder.commentDate = (TextView) convertView.findViewById(R.id.tvCommentDate);
            viewHolder.likeCounts = (TextView) convertView.findViewById(R.id.tvLikeCounts);
            viewHolder.likes     = (TextView) convertView.findViewById(R.id.tvLikes);
            viewHolder.totalComments = (TextView) convertView.findViewById(R.id.tvTotalComments);


            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //insert the model data into the view items
       // viewHolder.caption.setText(photo.caption);

        // Clear out the imageView
        viewHolder.profilePhoto.setImageResource(0);
        // insert the profile image using picasso
        Picasso.with(getContext()).load(photo.profileUrl).into(viewHolder.profilePhoto);

        // Clear out the imageView
        viewHolder.photoComments.setImageResource(0);
        // insert the profile image using picasso
        Picasso.with(getContext()).load(photo.imageUrl).into(viewHolder.photoComments);

        viewHolder.username.setText(photo.username);
        viewHolder.username.setTextColor(Color.rgb(25, 25, 112));

        viewHolder.fullname.setText(photo.fullname);

        viewHolder.likeCounts.setText(photo.likesCount + " likes" + ".");
        viewHolder.likeCounts.setTextColor(Color.rgb(25, 25, 112));

        String relativeTime = DateUtils.getRelativeTimeSpanString(
                photo.creationDate,
                System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE
        ).toString();
        viewHolder.commentDate.setText(relativeTime);

        //viewHolder.tvLikeCount.setCom
        Drawable heartIcon = viewHolder.likes.getCompoundDrawables()[0];
        ColorFilter colorFilter = new LightingColorFilter(Color.parseColor("#e62666"),Color.parseColor("#e62666"));
        heartIcon.setColorFilter(colorFilter);

    //    viewHolder.commentDate.setText(photo.creationPhotoDate.toString());
        viewHolder.totalComments.setText("View all "+photo.totalComments + " comments");
        viewHolder.totalComments.setTag(position);

        viewHolder.comment.setText(photo.comment + " By "+photo.commentBy);
        viewHolder.comment.setTextColor(Color.rgb(25, 25, 112));
        //return the created item as a view
        return convertView;

    }
}
