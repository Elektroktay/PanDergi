package com.gmail.senokt16.pandergi;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MagazineViewHolder extends RecyclerView.ViewHolder {
    ImageView image;
    TextView title;
    TextView description;
    FrameLayout container;
    public MagazineViewHolder(View itemView) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.magazine_image);
        title = (TextView) itemView.findViewById(R.id.magazine_title);
        description = (TextView) itemView.findViewById(R.id.magazine_description);
        container = (FrameLayout) itemView.findViewById(R.id.magazine_container);
    }
}
