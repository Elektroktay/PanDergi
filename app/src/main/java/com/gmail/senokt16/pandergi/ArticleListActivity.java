package com.gmail.senokt16.pandergi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.gmail.senokt16.pandergi.data.Article;
import com.gmail.senokt16.pandergi.data.Magazine;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ArticleListActivity extends AppCompatActivity {

    public static final String EXTRA_MAGAZINE = "extra_magazine";
    Magazine magazine;
    ImageView image;
    RecyclerView recyclerView;
    FirebaseIndexRecyclerAdapter<Article, ArticleViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        image = (ImageView) findViewById(R.id.issue_list_image);
        magazine = (Magazine) getIntent().getSerializableExtra(EXTRA_MAGAZINE);
        recyclerView = (RecyclerView) findViewById(R.id.article_list_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (magazine.imageUrl != null && magazine.imageUrl.length() > 0) {
            supportPostponeEnterTransition();
            Glide.with(ArticleListActivity.this)
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

        if (magazine != null && magazine.key != null) {
            Query refKeys = FirebaseDatabase.getInstance().getReference("sayilar").child(magazine.key).child("articles").orderByValue();
            Query refVals = FirebaseDatabase.getInstance().getReference("yazilar").orderByChild("category");

            adapter = new FirebaseIndexRecyclerAdapter<Article, ArticleViewHolder>(Article.class, R.layout.fragment_article, ArticleViewHolder.class, refKeys, refVals) {
                @Override
                protected void populateViewHolder(ArticleViewHolder viewHolder, Article model, int position) {
                    if (model.title != null) {
                        viewHolder.title.setVisibility(View.VISIBLE);
                        viewHolder.title.setText(model.title);
                    } else {
                        viewHolder.title.setVisibility(View.GONE);
                    }
                    if (model.description != null) {
                        viewHolder.description.setVisibility(View.VISIBLE);
                        viewHolder.description.setText(model.description);
                    } else {
                        viewHolder.description.setVisibility(View.GONE);
                    }
                    if (model.category != null) {
                        viewHolder.category.setVisibility(View.VISIBLE);
                        viewHolder.category.setText(model.category);
                    } else {
                        viewHolder.category.setVisibility(View.GONE);
                    }
                    if (model.author != null) {
                        viewHolder.author.setVisibility(View.VISIBLE);
                        viewHolder.author.setText(model.author);
                    } else {
                        viewHolder.author.setVisibility(View.GONE);
                    }
                    if (model.imageUrl != null && model.imageUrl.length() > 0) {
                        viewHolder.image.setVisibility(View.VISIBLE);
                        StorageReference sRef = FirebaseStorage.getInstance().getReferenceFromUrl(model.imageUrl);
                        Glide.with(ArticleListActivity.this)
                                .using(new FirebaseImageLoader())
                                .load(sRef)
                                .into(viewHolder.image);
                    } else {
                        viewHolder.image.setVisibility(View.GONE);
                    }
                    viewHolder.container.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO: Open Article Activity.
                        }
                    });
                }
            };
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Yazılar yüklenemedi!", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null)
            adapter.cleanup();
    }
}
