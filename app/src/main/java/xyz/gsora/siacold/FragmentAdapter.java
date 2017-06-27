package xyz.gsora.siacold;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by gsora on 27/06/17.
 * <p>
 * Fragment adapter for the main view.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {

    // The fragment adapter already knows what kind of fragment it should create, so we just instantiate one
    // and it'll do its job.

    private NamedFragment[] fragments = new NamedFragment[2];

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        fragments[0] = new InformationNamedFragment();
        fragments[1] = new AddressesNamedFragment();
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments[position].getFragmentFancyName();
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
