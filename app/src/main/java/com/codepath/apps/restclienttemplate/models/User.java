package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by icamargo on 6/26/17.
 */

public class User {
    //list attributes
    public String name;
    public long uid;
    public String screenName;
    public String profileImageIUrl;


    //deseriealize the JSON
    public static User fromJson(JSONObject json) throws JSONException{
        User user = new User();

        //extract and fill the values with json object
        user.name = json.getString("name");
        user.screenName = json.getString("screen_name");
        user.uid = json.getLong("id");
        user.profileImageIUrl = json.getString("profile_image_url");

        return user;
    }
}
