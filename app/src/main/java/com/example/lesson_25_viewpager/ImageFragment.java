package com.example.lesson_25_viewpager;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ImageFragment extends Fragment {

    private static final String EXTRA_URI = "extra_uri";

    private ImageView mImageView;
    private Uri mUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mUri = getArguments() != null ? getArguments().getParcelable(EXTRA_URI) : null;

        View view = inflater.inflate(R.layout.fragment_image, container, false);
        mImageView = view.findViewById(R.id.detail_image);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mUri != null)
            Glide.with(Objects.requireNonNull(getActivity()))
                    .load(mUri.getPath())
                    .into(mImageView);
    }

    public static ImageFragment newInstance(Uri uri) {

        Bundle args = new Bundle();
        args.putParcelable(EXTRA_URI, uri);
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
