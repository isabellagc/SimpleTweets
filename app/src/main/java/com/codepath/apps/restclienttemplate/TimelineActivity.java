package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import fragments.TweetsListFragment;
import fragments.TweetsPagerAdapter;

public class TimelineActivity extends AppCompatActivity implements TweetsListFragment.TweetSelectedListener{

    private final int REQUEST_CODE = 20;


    //to make the app swipe to refresh!
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
//        ActionBar bar = getActionBar();
//        bar.setBackgroundDrawable(new ColorDrawable());

//        //Lookup the swipe container view
//        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
//            @Override
//            public void onRefresh(){
//                //code to refresh the list
//                tweetAdapter.clear();
//                populateTimeline();
//                swipeContainer.setRefreshing(false);
//                //fetchTimelineAsync(0);
//            }
//        });
//
//        // Configure the refreshing colors
//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);

        //get the view pager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        //setup the adapter for the pager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager(), this));
        //setup the tab layout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onProfileView(MenuItem item) {
        // launch the profile view
        Intent i =  new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    @Override
    public void onTweetSelected(Tweet tweet) {
        //create the new intent for the new activity
        Intent intent = new Intent(this, DetailsActivity.class);
        //pass in the tweet as a parameter making sure that the tweet is parcelable
        intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
        //show the activity
        this.startActivity(intent);

        //Toast.makeText(this, tweet.body, Toast.LENGTH_LONG).show();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle presses on the action bar items
//        switch (item.getItemId()) {
//            case R.id.miCompose:
//                composeMessage();
//                return true;
////            case R.id.miProfile:
////                showProfileView();
////                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    private void composeMessage(){
//        Intent intent = new Intent(this, ComposeActivity.class);
//        startActivityForResult(intent, REQUEST_CODE);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // REQUEST_CODE i
//        // s defined above
//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
//            Tweet tweet = (Tweet) Parcels.unwrap(data.getParcelableExtra("tweet"));
//            tweets.add(0, tweet);
//            tweetAdapter.notifyItemInserted(0);
//            rvTweets.scrollToPosition(0);
//        }
//    }



}
