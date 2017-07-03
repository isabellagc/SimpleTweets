package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.restclienttemplate.DividerItemDecoration;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweetAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by icamargo on 7/3/17.
 */

public class TweetsListFragment extends Fragment {
    //fragments contain an extra lifecycle
    //adapter
    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;
    //inflateion happens inside OnCreateView

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate the layout
        View v = inflater.inflate(R.layout.fragments_tweets_list, container, false);
        //find the recyclerView
        rvTweets = (RecyclerView) v.findViewById(R.id.rvTweet);

        //instantiate the datasource
        tweets = new ArrayList<>();

        //construct adapter from the datasource
        tweetAdapter = new TweetAdapter(tweets);

        //RecyclerView setup (layoutmanager, use adapter)
        //this refers to our current activity
        rvTweets.setLayoutManager(new LinearLayoutManager(getContext()));

        rvTweets.addItemDecoration(new DividerItemDecoration(getContext()));
        //set the adapter
        rvTweets.setAdapter(tweetAdapter);



        return v;
    }

    public void addItems(JSONArray response){
        //convert each object to a tweet and put in our list of tweets
        //notify adapter that we added an item
        //iterate through the JSON array
        //for each entry,deserialize the JSON object
        try {
            for (int i = 0; i < response.length(); i++) {
                Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                tweets.add(tweet);
                tweetAdapter.notifyItemInserted(tweets.size() - 1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


















//