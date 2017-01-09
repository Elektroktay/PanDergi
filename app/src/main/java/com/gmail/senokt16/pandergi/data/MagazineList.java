package com.gmail.senokt16.pandergi.data;

import android.support.v7.widget.RecyclerView;

import com.gmail.senokt16.pandergi.data.Magazine;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MagazineList {

    private static DatabaseReference ref;
    private static ArrayList<Magazine> data = new ArrayList<>();
    private static ArrayList<RecyclerView.Adapter> adapters = new ArrayList<>();

    static {
        if (ref == null) {
            ref = FirebaseDatabase.getInstance().getReference("/sayilar/");
            ref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                        Magazine mag = (Magazine) dataSnapshot.getValue();
                        mag.key = dataSnapshot.getKey();
                        data.add(mag);
                        for (RecyclerView.Adapter adapter : adapters) {
                            adapter.notifyItemInserted(size()-1);
                        }
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                        for (int i=0; i<data.size(); i++) {
                            if (data.get(i).key.equals(dataSnapshot.getKey())) {
                                data.set(i, (Magazine) dataSnapshot.getValue());
                                for (RecyclerView.Adapter adapter : adapters) {
                                    adapter.notifyItemChanged(i);
                                }
                                break;
                            }
                        }
                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                        for (int i=0; i<data.size(); i++) {
                            if (data.get(i).key.equals(dataSnapshot.getKey())) {
                                data.remove(i);
                                for (RecyclerView.Adapter adapter : adapters) {
                                    adapter.notifyItemRemoved(i);
                                }
                                break;
                            }
                        }
                    }
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public static int size() {
        return data.size();
    }

    public static Magazine get(int i) {
        return data.get(i);
    }

    public static void addAdapter(RecyclerView.Adapter adapter) {
        adapter.notifyDataSetChanged();
        adapters.add(adapter);
    }

    public static void removeAdapter(RecyclerView.Adapter adapter) {
        adapters.remove(adapter);
    }


}
