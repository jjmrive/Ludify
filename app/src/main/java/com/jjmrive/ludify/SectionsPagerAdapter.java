package com.jjmrive.ludify;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jjmrive.ludify.visit.Visit;

public class SectionsPagerAdapter extends FragmentPagerAdapter{

    final int PAGE_COUNT = 2;
    private String tabTitles[] =
            new String[] {"Score", "Prizes"};
    private Visit visit;

    public SectionsPagerAdapter(FragmentManager fm, Visit visit) {
        super(fm);
        this.visit = visit;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = null;

        switch (position) {
            case 0:
                f = ScoreFragment.newInstance(visit);
                break;
            case 1:
                f = PrizesFragment.newInstance(visit);
                break;
        }

        return f;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
