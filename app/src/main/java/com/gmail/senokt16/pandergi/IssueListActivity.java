package com.gmail.senokt16.pandergi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.gmail.senokt16.pandergi.data.Magazine;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class IssueListActivity extends AppCompatActivity {

    public static final String EXTRA_MAGAZINE = "extra_magazine";
    Magazine magazine;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        image = (ImageView) findViewById(R.id.issue_list_image);
        magazine = (Magazine) getIntent().getSerializableExtra(EXTRA_MAGAZINE);

        if (magazine.imageUrl != null && magazine.imageUrl.length() > 0) {
            supportPostponeEnterTransition();
            Glide.with(IssueListActivity.this)
                    .using(new FirebaseImageLoader())
                    .load(FirebaseStorage.getInstance().getReferenceFromUrl(magazine.imageUrl))
                    .listener(new RequestListener<StorageReference, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, StorageReference model, Target<GlideDrawable> target, boolean isFirstResource) {
                            supportStartPostponedEnterTransition();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, StorageReference model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            supportStartPostponedEnterTransition();
                            return false;
                        }
                    })
                    .into(image);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }
}
