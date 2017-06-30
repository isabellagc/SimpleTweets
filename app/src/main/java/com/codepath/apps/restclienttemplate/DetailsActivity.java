package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

/**
 * Created by icamargo on 6/30/17.
 */

public class DetailsActivity extends AppCompatActivity{
    Tweet tweet;
    ImageView ivProfImage;
    TextView tvUserName, tvHandle, tvTweetBody, tvTimeStamp;

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

        //get tweet from parcel...
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        Log.d("DetailsActivity", String.format("Showing details for '%s'", tweet.user));

        //set values based on tweet object
        tvUserName.setText(tweet.user.name);
        tvTweetBody.setText(tweet.user.screenName);
        tvTweetBody.setText(tweet.body);
        tvTimeStamp.setText(tweet.timeStamp);

    }
}
