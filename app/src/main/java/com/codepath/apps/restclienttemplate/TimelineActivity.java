package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;
    //reference to our twitter client
    private TwitterClient client;
    //adapter
    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;
    //to make the app swipe to refresh!
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
//        ActionBar bar = getActionBar();
//        bar.setBackgroundDrawable(new ColorDrawable());

        //Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                //code to refresh the list
                tweetAdapter.clear();
                populateTimeline();
                swipeContainer.setRefreshing(false);
                //fetchTimelineAsync(0);
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //find the recyclerView
        rvTweets = (RecyclerView) findViewById(R.id.rvTweet);

        //instantiate the datasource
        tweets = new ArrayList<>();

        //construct adapter from the datasource
        tweetAdapter = new TweetAdapter(tweets);

        //RecyclerView setup (layoutmanager, use adapter)
        //this refers to our current activity
        rvTweets.setLayoutManager(new LinearLayoutManager(this));

        rvTweets.addItemDecoration(new DividerItemDecoration(this));
        //set the adapter
        rvTweets.setAdapter(tweetAdapter);

        client = TwitterApp.getRestClient();
        populateTimeline();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miCompose:
                composeMessage();
                return true;
//            case R.id.miProfile:
//                showProfileView();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void composeMessage(){
        Intent intent = new Intent(this, ComposeActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE i
        // s defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            Tweet tweet = (Tweet) Parcels.unwrap(data.getParcelableExtra("tweet"));
            tweets.add(0, tweet);
            tweetAdapter.notifyItemInserted(0);
            rvTweets.scrollToPosition(0);
        }
    }

//    //TODO: add functionality
//    private void showProfileView(){
//
//    }

    private void populateTimeline(){
        //make network request, create anonymous class to deal withthe response of the handler
        client.getHomeTimeline(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient", response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //Log.d("TwitterClient", response.toString());
                //iterate through the JSON array
                //for each entry,deserialize the JSON object
                for(int i = 0; i < response.length(); i ++){
                    //convert each object to a tweet and put in our list of tweets
                    //notify adapter that we added an item
                    try {
                        Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                        tweets.add(tweet);
                        tweetAdapter.notifyItemInserted(tweets.size() - 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }
        });
    }
}
