package com.gmail.senokt16.pandergi;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.gmail.senokt16.pandergi.data.Magazine;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;

import java.io.IOException;

public class MagazineListActivity extends AppCompatActivity {

    RecyclerView magazineListContent;
    FirebaseRecyclerAdapter<Magazine, MagazineViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        magazineListContent = (RecyclerView) findViewById(R.id.magazine_list_content);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        GridLayoutManager lManager = new GridLayoutManager(this, 2);
        magazineListContent.setLayoutManager(lManager);

        Query ref = FirebaseDatabase.getInstance().getReference("/sayilar/").orderByChild("publish").startAt(true).endAt(true);
        adapter = new FirebaseRecyclerAdapter<Magazine, MagazineViewHolder>(Magazine.class, R.layout.fragment_magazine, MagazineViewHolder.class, ref) {
            @Override
            protected void populateViewHolder(final MagazineViewHolder viewHolder, final Magazine model, int position) {
                viewHolder.title.setText(model.title);
                viewHolder.description.setText(model.description);
                if (model.imageUrl != null && model.imageUrl.length() > 0) {
                    Glide.with(MagazineListActivity.this)
                            .using(new FirebaseImageLoader())
                            .load(FirebaseStorage.getInstance().getReferenceFromUrl(model.imageUrl))
                            .into(viewHolder.image);
                }
                viewHolder.container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MagazineListActivity.this, IssueListActivity.class);
                        intent.putExtra(IssueListActivity.EXTRA_MAGAZINE, model);
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(MagazineListActivity.this, viewHolder.image, "magazineImage");
                        MagazineListActivity.this.startActivity(intent, options.toBundle());
                    }
                });
            }
        };
        magazineListContent.setAdapter(adapter);

/*        String url = "https://firebasestorage.googleapis.com/v0/b/pan-dergi.appspot.com/o/We%20Are%20Number%20One%20-%20LazyTown-%20The%20Video%20Game%20(320%20%20kbps).mp3?alt=media&token=fe3f9658-18dc-4b81-a0f3-b59a6a0f74cb";
        MediaPlayer player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setLooping(false);
        player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(final MediaPlayer mediaPlayer, int i) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mediaPlayer.start();
                    }
                }, 100);
            }
        });
        try {
            player.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.prepareAsync();
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_magazine_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
