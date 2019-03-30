package com.example.lesson_25_viewpager;

import android.net.Uri;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ImageStatePagerAdapter extends FragmentStatePagerAdapter {

    private List<Uri> mUris;

    public ImageStatePagerAdapter(FragmentManager fm, List<Uri> uriList) {
        super(fm);
        mUris = uriList;
    }

    @Override
    public Fragment getItem(int i) {
        return ImageFragment.newInstance(mUris.get(i));
    }

    @Override
    public int getCount() {
        return mUris.size();
    }
}
