package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.parceler.Parcels;

import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.codepath.apps.restclienttemplate.R.id.bvLikeDetails;

/**
 * Created by icamargo on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    private List<Tweet> mTweets;
    private Context context;
    private TwitterClient client;

    //pass in the Tweet array in the constructor
    public TweetAdapter(List<Tweet> tweets){
        mTweets = tweets;
    }

    //for each row, inflate the layout and pass into the ViewHolder class
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    //bind the values based on the position of the element
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //get the data according to position
        final Tweet tweet = mTweets.get(position);

        //populate the views according to this data
        holder.tvUserName.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvTimeStamp.setText(tweet.timeStamp);
        holder.tvNumRetweet.setText(String.valueOf(tweet.numRetweets));
        holder.tvNumLikes.setText(String.valueOf(tweet.numLikes));

        if(tweet.liked == true){
            holder.bvLike.setColorFilter(Color.RED);
            holder.tvNumLikes.setTextColor(Color.RED);
        }

        if(tweet.retweeted == true){
            holder.bvRetweet.setColorFilter(Color.GREEN);
            holder.tvNumRetweet.setTextColor(Color.GREEN);
        }

        Glide.with(context).load(tweet.user.profileImageIUrl).into(holder.ivProfileImage);

        //give viewholder reference to button on the tweet and do the retweet stuff
        //since i have the position and the id here this is the only info i need to retweet look at the API
        holder.bvRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client = TwitterApp.getRestClient();
                client.retweet(Long.toString(tweet.uid), tweet.retweeted, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        holder.bvRetweet.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
                        holder.tvNumRetweet.setTextColor(Color.GREEN);
                        int currentRetweets = Integer.valueOf((String) holder.tvNumRetweet.getText());
                        holder.tvNumRetweet.setText(Integer.toString(currentRetweets + 1));

                        //Toast.makeText(context, "retweet success", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(context, "retweet failure", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        holder.bvLike.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                client = TwitterApp.getRestClient();
                client.like(Long.toString(tweet.uid), tweet.liked, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        holder.bvLike.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                        holder.tvNumLikes.setTextColor(Color.RED);
                        int currentLikes = Integer.valueOf((String) holder.tvNumLikes.getText());
                        holder.tvNumLikes.setText(Integer.toString(currentLikes + 1));
                        //holder.tvNumLikes.setText();
                        //Toast.makeText(context, "like success", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(context, "Like failure", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        //todo: implement the reply function within the composeActivity

//        holder.bvReply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //create the new intent for the new activity
//                Intent intent = new Intent(context, ReplyActivity.class);
//                //pass in the tweet as a parameter making sure that the tweet is parcelable
//                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
//                //show the activity
//                context.startActivity(intent);
//            }
//        });

    }

    //change this to reflect the number of tweets that we have
    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    //create the ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivProfileImage;
        public TextView tvUserName;
        public TextView tvBody;
        public TextView tvTimeStamp;
        public ImageButton bvRetweet;
        public ImageButton bvLike;
        public TextView tvNumLikes;
        public TextView tvNumRetweet;
        //public ImageButton bvReply;

        public ViewHolder(View itemView) {
            super(itemView);

            //perform findViewById lookups
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvTimeStamp = (TextView) itemView.findViewById(R.id.tvTimeStamp);
            bvRetweet = (ImageButton) itemView.findViewById(R.id.bvRetweet);
            bvLike = (ImageButton) itemView.findViewById(bvLikeDetails);
            tvNumLikes = (TextView) itemView.findViewById(R.id.tvNumLikes);
            tvNumRetweet = (TextView) itemView.findViewById(R.id.tvNumRetweet);
            //bvReply = (ImageButton) itemView.findViewById(R.id.bvReply);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get item position
                    int position = getAdapterPosition();
                    //make sure the posiiton is valid, i.e. actually exists in the view
                    if (position != RecyclerView.NO_POSITION) {
                        //get movie at the position (class must be static)
                        Tweet tweet = mTweets.get(position);
                        //create the new intent for the new activity
                        Intent intent = new Intent(context, DetailsActivity.class);
                        //pass in the tweet as a parameter making sure that the tweet is parcelable
                        intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                        //show the activity
                        context.startActivity(intent);
                    }
                }
            });
        }
    }


    //below added to try and update RecyclerView to swipe to refresh
    // Clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }
}
