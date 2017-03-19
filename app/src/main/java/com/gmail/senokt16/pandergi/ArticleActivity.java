package com.gmail.senokt16.pandergi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.gmail.senokt16.pandergi.data.Article;
import com.google.firebase.storage.FirebaseStorage;

public class ArticleActivity extends AppCompatActivity {

    Article article;
    TextView author, title, description, text;
    ImageView image;
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        author = (TextView) findViewById(R.id.author);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        text = (TextView) findViewById(R.id.text);
        image = (ImageView) findViewById(R.id.article_image);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        article = (Article) getIntent().getSerializableExtra("article");
        if (article == null) {
            Log.e("ArticleActivity", "Did not receive Article from intent, returning to MagazineListActivity.");
            startActivity(new Intent(this, MagazineListActivity.class));
            finish();
            return;
        }

        if (article.category != null)
            getSupportActionBar().setTitle(article.category);
        else
            getSupportActionBar().setTitle(article.title);

        if (article.author != null)
            author.setText(article.author);
        else
            author.setVisibility(View.GONE);

        if (article.title != null)
            title.setText(article.title);
        else
            title.setVisibility(View.GONE);

        if (article.description != null)
            description.setText(article.description);
        else
            description.setVisibility(View.GONE);

        if (article.body != null)
            text.setText(article.body);
        else
            text.setVisibility(View.GONE);

        if (article.imageUrl != null) {
            Glide.with(this)
                    .using(new FirebaseImageLoader())
                    .load(FirebaseStorage.getInstance().getReferenceFromUrl(article.imageUrl))
                    .centerCrop()
                    .into(image);
        } else {
            image.setVisibility(View.GONE);
/*            ViewGroup.LayoutParams params = appBarLayout.getLayoutParams();
            params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56+45, getResources().getDisplayMetrics());
            appBarLayout.setLayoutParams(params);*/
/*            appBarLayout.setExpanded(false, false);
            appBarLayout.setActivated(false);*/
            disableCollapse();
        }
    }

    public void disableCollapse() {
        appBarLayout.setActivated(false);
        //you will need to hide also all content inside CollapsingToolbarLayout
        //plus you will need to hide title of it
        //backdrop.setVisibility(View.GONE);
        //shadow.setVisibility(View.GONE);
        collapsingToolbar.setTitleEnabled(false);

        AppBarLayout.LayoutParams p = (AppBarLayout.LayoutParams) collapsingToolbar.getLayoutParams();
        p.setScrollFlags(0);
        collapsingToolbar.setLayoutParams(p);
        collapsingToolbar.setActivated(false);

        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        lp.height = getResources().getDimensionPixelSize(R.dimen.toolbar_height) + (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? getStatusBarHeight() : 0);
        appBarLayout.requestLayout();

        //you also have to setTitle for toolbar
        if (article.category != null)
            getSupportActionBar().setTitle(article.category);
        else
            getSupportActionBar().setTitle(article.title);
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
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
