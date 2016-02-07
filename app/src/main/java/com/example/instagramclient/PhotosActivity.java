package com.example.instagramclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class PhotosActivity extends ActionBarActivity {
    private SwipeRefreshLayout swipeContainer;

    public static final String CLIENT_ID="1e90607fae1e4d4b87b92a606ee60d07";
    public static ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;


   // public static ArrayList<InstagramPhoto> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_camera_inter);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        // SEND OUT API REQUEST
        photos = new ArrayList<InstagramPhoto>();

        // create the adapter linking it to the source
        aPhotos = new InstagramPhotosAdapter(this, photos);
        // Find the list view
        final ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);


        // Set the adapter binding it to the listview
        lvPhotos.setAdapter(aPhotos);

        // Fetch the popular photos
        fetchPopularPhotos();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.scActivityPhotos);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchPopularPhotos();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


    }


    // Trigger API request
    public void fetchPopularPhotos() {


        String url = "https://api.instagram.com/v1/media/popular?client_id="+ CLIENT_ID;
        // create the network client
        AsyncHttpClient client = new AsyncHttpClient();
        // trigger the GET request
        client.get(url, null, new JsonHttpResponseHandler() {
            // onSuccess (worked, 200)

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // clear out old items before appending new ones
                aPhotos.clear();
                // iterate each of the photo items and decode the items into a java object
                JSONArray photosJSON = null;
                try {
                    photosJSON = response.getJSONArray("data"); // array of posts

                    for (int i = 0; i < photosJSON.length(); i++) {
                        //get the json object at that position
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        // decode the attribute of the JSON into a data model
                        InstagramPhoto photo = new InstagramPhoto();

                        // Profile Picture
                        photo.profileUrl = photoJSON.getJSONObject("user").getString("profile_picture");
                        // Author Username : {“data” => [x] => “user” => “Username”
                        photo.username = photoJSON.getJSONObject("user").getString("username");
                        // Author Fullname : {“data” => [x] => “user” => “Username”
                        photo.fullname = photoJSON.getJSONObject("user").getString("full_name");
                        // Caption : {“data” => [x] => “Caption” => “text”}
                        //photo.caption = photoJSON.getJSONObject("caption").getString("text");
                        // URLs : {“data” => [x] => “Images” => “standard_resolution” => “url”}
                        photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        // height
                        photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        // Likes Count
                        photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
                        // Total Comments
                        photo.totalComments = photoJSON.getJSONObject("comments").getInt("count");

                        photo.comments = new ArrayList<InstagramComment>();


                        // Comments
                        photo.comment = photoJSON.getJSONObject("comments").getJSONArray("data").getJSONObject(1).getString("text");
                        photo.commentBy = photoJSON.getJSONObject("comments").getJSONArray("data").getJSONObject(2).getJSONObject("from").getString("username");

                        //Photo Date
                        photo.creationDate = photoJSON.getJSONObject("caption").getLong("created_time")*1000;

                        JSONArray pCommentsJSON = photoJSON.getJSONObject("comments").getJSONArray("data");
                        for (int k = 0; k < pCommentsJSON.length(); k++) {
                            JSONObject commentJSON = pCommentsJSON.getJSONObject(k);
                            InstagramComment comment = new InstagramComment();
                            comment.comText = commentJSON.getString("text");
                            comment.comUsername = commentJSON.getJSONObject("from").getString("username");
                            comment.comFullname = commentJSON.getJSONObject("from").getString("full_name");
                            comment.comProfilePictureUrl = commentJSON.getJSONObject("from").getString("profile_picture");
                            comment.comCreatedTime = commentJSON.getLong("created_time") * 1000;
                            photo.comments.add(comment);

                        }


                        photos.add(photo);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // callback
                aPhotos.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //
                swipeContainer.setRefreshing(false);
            }
        });


    }

    // Show the comments for a photo
    public void showComments(View view) {
        TextView tvTotalComments = (TextView)view;
        int photoPosition = (int)tvTotalComments.getTag();

        Intent intent = new Intent(this, CommentsActivity.class);
        intent.putExtra("photo_position", photoPosition);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }


}
