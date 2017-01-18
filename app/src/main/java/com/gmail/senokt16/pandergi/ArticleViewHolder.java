package com.gmail.senokt16.pandergi;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by senok on 12/1/2017.
 */

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
}
