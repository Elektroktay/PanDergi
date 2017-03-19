package com.gmail.senokt16.pandergi;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.gmail.senokt16.pandergi.data.Article;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ArticleViewHolder extends RecyclerView.ViewHolder{
    CardView container;
    TextView category;
    ImageView image;
    TextView author;
    TextView title;
    TextView description;
    public ArticleViewHolder(View view) {
        super(view);
        container = (CardView) view.findViewById(R.id.article_container);
        category = (TextView) view.findViewById(R.id.category);
        image = (ImageView) view.findViewById(R.id.image);
        author = (TextView) view.findViewById(R.id.author);
        title = (TextView) view.findViewById(R.id.title);
        description = (TextView) view.findViewById(R.id.description);
    }

    public void populateViewHolder(final Activity activity, final Article model, int position) {
        if (model.title != null) {
            title.setVisibility(View.VISIBLE);
            title.setText(model.title);
        } else {
            title.setVisibility(View.GONE);
        }
        if (model.description != null) {
            description.setVisibility(View.VISIBLE);
            description.setText(model.description);
        } else {
            description.setVisibility(View.GONE);
        }
        if (model.category != null) {
            category.setVisibility(View.VISIBLE);
            category.setText(model.category);
        } else {
            category.setVisibility(View.GONE);
        }
        if (model.author != null) {
            author.setVisibility(View.VISIBLE);
            author.setText(model.author);
        } else {
            author.setVisibility(View.GONE);
        }
        if (model.imageUrl != null && model.imageUrl.length() > 0) {
            image.setVisibility(View.VISIBLE);
            StorageReference sRef = FirebaseStorage.getInstance().getReferenceFromUrl(model.imageUrl);
            Glide.with(activity)
                    .using(new FirebaseImageLoader())
                    .load(sRef)
                    .into(image);
        } else {
            image.setVisibility(View.GONE);
        }
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, ArticleActivity.class);
                i.putExtra("article", model);
                activity.startActivity(i);
            }
        });
    }
}
