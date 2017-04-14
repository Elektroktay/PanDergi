package com.gmail.senokt16.pandergi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.gmail.senokt16.pandergi.data.Article;
import com.gmail.senokt16.pandergi.data.Magazine;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AuthorArticleListActivity extends AppCompatActivity {

    public static final String EXTRA_AUTHOR = "extra_author";
    String author;
    RecyclerView recyclerView;
    FirebaseIndexRecyclerAdapter<Article, ArticleViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_article_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        author = getIntent().getStringExtra(EXTRA_AUTHOR);

        getSupportActionBar().setTitle(author);
        recyclerView = (RecyclerView) findViewById(R.id.article_list_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);

        if (author != null) {
            Query refKeys = FirebaseDatabase.getInstance().getReference("yazarlar").child(author);
            Query refVals = FirebaseDatabase.getInstance().getReference("yazilar").orderByChild("category");

            adapter = new FirebaseIndexRecyclerAdapter<Article, ArticleViewHolder>(Article.class, R.layout.fragment_article, ArticleViewHolder.class, refKeys, refVals) {
                @Override
                protected void populateViewHolder(ArticleViewHolder viewHolder, Article model, int position) {
                    viewHolder.populateViewHolder(AuthorArticleListActivity.this, model, position);
                }
            };
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Yazılar yüklenemedi!", Toast.LENGTH_SHORT).show();
        }

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null)
            adapter.cleanup();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

/*        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
