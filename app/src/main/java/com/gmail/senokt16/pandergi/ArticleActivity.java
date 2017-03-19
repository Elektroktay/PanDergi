package com.gmail.senokt16.pandergi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gmail.senokt16.pandergi.data.Article;

public class ArticleActivity extends AppCompatActivity {

    Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        article = (Article) getIntent().getSerializableExtra("article");
        if (article == null) {
            Log.e("ArticleActivity", "Did not receive Article from intent, returning to MagazineListActivity.");
            startActivity(new Intent(this, MagazineListActivity.class));
            finish();
            return;
        }

        getSupportActionBar().setTitle(article.title);

    }
}
