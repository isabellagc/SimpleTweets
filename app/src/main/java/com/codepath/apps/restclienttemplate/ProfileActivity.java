package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import fragments.UserTimelineFragment;

public class ProfileActivity extends AppCompatActivity {
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        client = TwitterApp.getRestClient();

        String screenName = getIntent().getStringExtra("screen_name");
        Long userID = getIntent().getLongExtra("id", 0);

        //create the user fragment
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);

        //display the user timeline fragment inside the container (dynamically)
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        //make changes
        ft.replace(R.id.flContainer, userTimelineFragment);

        //commit transaction
        ft.commit();

        if(screenName == null || userID == 0) {
            client.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        //deserialize the User object
                        //set title of action bar based on user object
                        User user = User.fromJson(response);
                        getSupportActionBar().setTitle(user.screenName);
                        //populate user headline
                        populateUserHeadline(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    //super.onFailure(statusCode, headers, throwable, errorResponse);
                    try {
                        throw throwable;
                    } catch (Throwable throwable1) {
                        throwable1.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    //super.onFailure(statusCode, headers, throwable, errorResponse);
                    try {
                        throw throwable;
                    } catch (Throwable throwable1) {
                        throwable1.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    //super.onFailure(statusCode, headers, throwable, errorResponse);
                    try {
                        throw throwable;
                    } catch (Throwable throwable1) {
                        throwable1.printStackTrace();
                    }
                }
            });
        }else{
            client.getUserInfo(userID, screenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Toast.makeText(ProfileActivity.this, "made the thing", Toast.LENGTH_LONG).show();
                    User user = null;
                    try {
                        user = User.fromJson(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ProfileActivity.this, "failing here", Toast.LENGTH_LONG).show();
                    }
                    getSupportActionBar().setTitle(user.screenName);
                    //populate user headline
                    populateUserHeadline(user);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        }


    }

    public void populateUserHeadline(User user) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);

        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

        tvName.setText(user.name);
        tvTagline.setText(user.tagLine);
        tvFollowers.setText(user.followersCount + " Followers");
        tvFollowing.setText(user.followingCount + " Following");
        //load
        Glide.with(this).load(user.profileImageIUrl).into(ivProfileImage);
    }
}








//