package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by icamargo on 6/26/17.
 */

@Parcel
public class Tweet {
    //list out attributes
    public String body;
    public long uid; //database id for the tweet
    public User user;
    public String createdAt;
    public String timeStamp;
    public boolean liked;
    public boolean retweeted;
    public String numRetweets, numLikes;

    public Tweet(){

    }
    //deserialize the JSON (take in JSON object and give back the tweet object)
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException{
        Tweet tweet = new Tweet();

        //extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.timeStamp = getRelativeTimeAgo(tweet.createdAt);
        tweet.liked = jsonObject.getBoolean("favorited");
        tweet.retweeted = jsonObject.getBoolean("retweeted");

        return tweet;
    }

    public static String getRelativeTimeAgo(String createdAt) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(createdAt).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

}
