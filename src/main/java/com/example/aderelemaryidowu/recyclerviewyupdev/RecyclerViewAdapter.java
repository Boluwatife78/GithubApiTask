package com.example.aderelemaryidowu.recyclerviewyupdev;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ADERELE MARY IDOWU on 7/13/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    public ArrayList<UserContact> arrayList = new ArrayList<UserContact>();
    public Context context;
    public RecyclerViewAdapter(ArrayList<UserContact> mArrayList, Context mContext)
    {
        arrayList = mArrayList;
        context = mContext;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view, context, arrayList);
        return recyclerViewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        UserContact userContact = arrayList.get(position);
        Picasso.with(context).load(userContact.getAvatar()).into(RecyclerViewHolder.UserImageView);
        holder.UserLoginTextView.setText("Login: " + userContact.getLogin());
        holder.UserIdTextView.setText("ID: " + userContact.getId());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public  ArrayList<UserContact> arrayList = new ArrayList<UserContact>();
        Context context;
        static ImageView UserImageView;
        TextView UserLoginTextView;
        TextView UserIdTextView;
        public RecyclerViewHolder(View itemView, Context mContext, ArrayList<UserContact> mArrayList) {
            super(itemView);
            UserImageView = (ImageView)itemView.findViewById(R.id.user_image);
            UserLoginTextView = (TextView)itemView.findViewById(R.id.user_login);
            UserIdTextView = (TextView)itemView.findViewById(R.id.userId);
            arrayList = mArrayList;
            context = mContext;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            UserContact userContact = arrayList.get(position);
            Intent intent = new Intent(this.context, FilmDetails.class);
            intent.putExtra("image", userContact.getAvatar());
            intent.putExtra("Username", userContact.getLogin());
            intent.putExtra("Id", userContact.getId());
            context.startActivity(intent);

        }

    }
    public void searchFilter(ArrayList<UserContact> mArrayList)
    {
        arrayList = new ArrayList<>();
        arrayList.addAll(mArrayList);
        notifyDataSetChanged();
    }
}
