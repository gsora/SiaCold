package xyz.gsora.siacold.WelcomeUI;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by gsora on 6/27/17.
 *
 * Fragment adapter for the welcome UI.
 */
public class WelcomeUIAdapter extends FragmentStatePagerAdapter {

    Fragment[] fragments = new Fragment[3];

    public WelcomeUIAdapter(FragmentManager fm) {
        super(fm);
        fragments[0] = WelcomeInfos.newInstance();
        fragments[1] = GenerateSeed.newInstance();
        fragments[2] = GoToMasterUI.newInstance();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
