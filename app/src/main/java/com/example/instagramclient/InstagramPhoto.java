package com.example.instagramclient;

import java.util.ArrayList;

/**
 * Created by carlybaja on 6/8/15.
 */
public class InstagramPhoto {
    public String profileUrl;
    public String username;
    public String fullname;
    public String caption;
    public String imageUrl;
    public String comment;
    public String commentBy;
    public int imageHeight;
    public int likesCount;
    public long creationDate;
    public int totalComments;
    public ArrayList<InstagramComment> comments;
}
