package com.example.lesson_25_viewpager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class ImageActivity extends AppCompatActivity {

    private static final String EXTRA_URIS = "extra_uris";
    private static final String EXTRA_POSITION = "extra_position";

    private List<Uri> mUris;
    private int mPosition;

    private ViewPager mImagePager;
    private ImageStatePagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUris = getIntent().getParcelableArrayListExtra(EXTRA_URIS);
        mPosition = getIntent().getIntExtra(EXTRA_POSITION, 0);

        setContentView(R.layout.activity_image);
        mImagePager = findViewById(R.id.image_pager);

        mPagerAdapter = new ImageStatePagerAdapter(getSupportFragmentManager(), mUris);
        mImagePager.setAdapter(mPagerAdapter);
        mImagePager.setCurrentItem(mPosition);

    }

    public static Intent newIntent(Context context, ArrayList<Uri> uris, int position) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_URIS, uris);
        intent.putExtra(EXTRA_POSITION, position);
        return intent;
    }
}
