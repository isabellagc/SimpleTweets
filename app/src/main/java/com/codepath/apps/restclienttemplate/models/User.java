package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by icamargo on 6/26/17.
 */

@Parcel
public class User {
    //list attributes
    public String name;
    public long uid;
    public String screenName;
    public String profileImageIUrl;
    public String followersCount, followingCount, tagLine;

    public User(){

    }
    //deseriealize the JSON
    public static User fromJson(JSONObject json) throws JSONException{
        User user = new User();

        //extract and fill the values with json object
        user.name = json.getString("name");
        user.screenName = json.getString("screen_name");
        user.uid = json.getLong("id");
        user.profileImageIUrl = json.getString("profile_image_url").replace("_normal", "");

        user.tagLine = json.getString("description");
        user.followersCount = json.getString("followers_count");
        user.followingCount = json.getString("friends_count");

        return user;
    }
}
