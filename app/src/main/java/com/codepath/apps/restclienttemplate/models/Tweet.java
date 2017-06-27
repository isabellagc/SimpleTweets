package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by icamargo on 6/26/17.
 */

public class Tweet {
    //list out attributes
    public String body;
    public long uid; //database id for the tweet
    public User user;
    public String createdAt;

    //deserialize the JSON (take in JSON object and give back the tweet object)
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException{
        Tweet tweet = new Tweet();

        //extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));

        return tweet;
    }

}
