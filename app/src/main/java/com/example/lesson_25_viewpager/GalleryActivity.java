package com.example.lesson_25_viewpager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GalleryActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    private ExecutorService mService;
    private List<Uri> mUris;

    private RecyclerView mGalleryRecycler;
    private ImagesRecyclerAdapter mRecyclerAdapter;

    private int mPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGalleryRecycler = findViewById(R.id.gallery_recycler);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mService = Executors.newSingleThreadExecutor();

        mGalleryRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerAdapter = new ImagesRecyclerAdapter(this);
        mGalleryRecycler.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.setItemClickListener(v -> {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            mPosition = viewHolder.getAdapterPosition();
            startActivity(ImageActivity.newIntent(this, new ArrayList<>(mUris), mPosition));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        } else {
            loadImages();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadImages();
                }
            }
        }
    }

    private void loadImages() {
        mService.execute(() -> {

            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);

            if (cursor != null) {
                try {
                    List<Uri> imagesUris = new ArrayList<>();

                    while (cursor.moveToNext()) {
                        int column = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        imagesUris.add(Uri.parse(cursor.getString(column)));
                    }
                    runOnUiThread(() -> setImages(imagesUris));
                } finally {
                    cursor.close();
                }
            }
        });
    }

    private void setImages(List<Uri> imagesUris) {
        mUris = imagesUris;
        mRecyclerAdapter.setData(mUris);
        mGalleryRecycler.scrollToPosition(mPosition);
    }
}
