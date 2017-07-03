package fragments;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by icamargo on 7/3/17.
 */

public class TweetsPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"Home", "Mentions"};
    private Context context;

    public TweetsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    // return the total number of fragments there are
    public int getCount(){
        return 2;
    }

    //return the fragment to use depending on position
    @Override
    public android.support.v4.app.Fragment getItem(int position){
        if (position == 0){
            return new HomeTimelineFragment();
        }else if (position == 1){
            return new MentionsTimelineFragment();
        }else{
            return null;
        }
    }

    //generate title based on position
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
