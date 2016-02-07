package com.example.instagramclient;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
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
 * Created by carlybaja on 2/5/16.
 */
public class InstagramCommentsAdapter extends ArrayAdapter<InstagramComment> {

    // View lookup cache
    private static class ViewHolder {

        TextView  comText;
        TextView  comUsername;
        TextView  comFullname;
        ImageView comProfilePictureUrl;
        TextView  comCreatedTime;
    }


    // Context, Data Source
    public InstagramCommentsAdapter(Context context,  List<InstagramComment> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        InstagramComment comment = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.items_comment, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.comProfilePictureUrl = (ImageView)convertView.findViewById(R.id.ivComProfilePicture);
            viewHolder.comUsername = (TextView)convertView.findViewById(R.id.tvComUsername);
            viewHolder.comFullname = (TextView)convertView.findViewById(R.id.tvComFullname);
            viewHolder.comText = (TextView)convertView.findViewById(R.id.tvComText);
            viewHolder.comCreatedTime = (TextView)convertView.findViewById(R.id.tvCommentTimeStamp);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // set image using Picasso
        Picasso.with(getContext())
                .load(comment.comProfilePictureUrl)
                .placeholder(R.drawable.ic_camera_inter)
                .into(viewHolder.comProfilePictureUrl);

        // set username
        viewHolder.comUsername.setText(comment.comUsername);
        viewHolder.comUsername.setTextColor(Color.rgb(25, 25, 112));

        // set fullname
        viewHolder.comFullname.setText(comment.comFullname);

        String commentHtml = "<font color='" + "#800000" + "'>" + comment.comUsername + "</font> " + comment.comText;
        viewHolder.comText.setText(Html.fromHtml(commentHtml));

        // set the relative timestamp
        String relativeTime = DateUtils.getRelativeTimeSpanString(
                comment.comCreatedTime,
                System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE
        ).toString();
        viewHolder.comCreatedTime.setText(relativeTime);

        return convertView;
    }


}

