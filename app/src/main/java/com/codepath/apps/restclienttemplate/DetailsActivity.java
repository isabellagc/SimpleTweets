package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

/**
 * Created by icamargo on 6/30/17.
 */

public class DetailsActivity extends AppCompatActivity{
    Tweet tweet;
    ImageView ivProfImage;
    ImageButton bvRetweetDetails, bvLikeDetails;
    TextView tvUserName, tvHandle, tvTweetBody, tvTimeStamp, tvLikesDetails, tvRetweetDetails;
    TwitterClient client;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //give references to all our fields...
        ivProfImage = (ImageView) findViewById(R.id.ivProfImage);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvHandle = (TextView) findViewById(R.id.tvHandle);
        tvTweetBody = (TextView) findViewById(R.id.tvTweetBody);
        tvTimeStamp = (TextView) findViewById(R.id.tvTimeStamp);
        tvLikesDetails = (TextView) findViewById(R.id.tvLikesDetails);
        tvRetweetDetails = (TextView) findViewById(R.id.tvRetweetDetails);
        bvLikeDetails = (ImageButton) findViewById(R.id.bvLikeDetails);
        bvRetweetDetails = (ImageButton) findViewById(R.id.bvRetweetDetails);
        context = this;

        //get tweet from parcel...
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        Log.d("DetailsActivity", String.format("Showing details for '%s'", tweet.user));

        //set values based on tweet object
        tvUserName.setText(tweet.user.name);
        tvTweetBody.setText(tweet.user.screenName);
        tvTweetBody.setText(tweet.body);
        tvTimeStamp.setText(tweet.timeStamp);
        tvHandle.setText(tweet.user.screenName);
        tvLikesDetails.setText(String.valueOf(tweet.numLikes) + "  Favorites");
        tvRetweetDetails.setText(String.valueOf(tweet.numRetweets) + "  Retweets");

        if(tweet.liked == true){
            bvLikeDetails.setColorFilter(Color.RED);
        }

        if(tweet.retweeted == true){
            bvRetweetDetails.setColorFilter(Color.GREEN);
        }

        Glide.with(this).load(tweet.user.profileImageIUrl).into(ivProfImage);

        bvLikeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client = TwitterApp.getRestClient();
                client.like(Long.toString(tweet.uid), tweet.liked, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        bvLikeDetails.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                        //Toast.makeText(context, "like success", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        //Toast.makeText(context, "Like failure", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        bvRetweetDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client = TwitterApp.getRestClient();
                client.retweet(Long.toString(tweet.uid), tweet.retweeted,  new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        bvRetweetDetails.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
                        //Toast.makeText(context, "retweet success", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        //Toast.makeText(context, "retweet failure", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}
