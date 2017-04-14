package com.gmail.senokt16.pandergi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder> {

    private static final int TYPE_AUTHOR = 0;
    private static final int TYPE_COMMA = 1;
    private String[] authors;
    Context context;

    public AuthorAdapter(String names) {
        authors = names.split(",");
        for (int i=0; i<authors.length; i++) {
            while (authors[i].charAt(0) == ' ')
                authors[i] = authors[i].substring(1);
            while (authors[i].length() > 0 && authors[i].charAt(authors[i].length()-1) == ' ')
                authors[i] = authors[i].substring(0,authors[i].length()-1);
        }
    }

    @Override
    public AuthorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;
        if (viewType == TYPE_AUTHOR) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_author, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_comma, parent, false);
        }
        return new AuthorViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position%2==0?TYPE_AUTHOR:TYPE_COMMA;
    }

    @Override
    public void onBindViewHolder(final AuthorViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_AUTHOR) {
            holder.author.setText(authors[position/2]);
            holder.wrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, AuthorArticleListActivity.class);
                    i.putExtra(AuthorArticleListActivity.EXTRA_AUTHOR, authors[holder.getAdapterPosition()/2]);
                    context.startActivity(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return authors.length*2-1;
    }

    class AuthorViewHolder extends RecyclerView.ViewHolder {
        TextView author;
        CardView wrapper;
        public AuthorViewHolder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.author);
            wrapper = (CardView) itemView.findViewById(R.id.author_wrapper);
        }
    }
}
