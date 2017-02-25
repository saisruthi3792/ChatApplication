package com.example.aninditha.homework7;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Aninditha on 11/23/2016.
 */

public class contactsAdapter extends RecyclerView.Adapter<contactsAdapter.ViewHolder> {

    public  interface OnItemClickListener {
        public void onItemClicked(int position);
    }


    OnItemClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView contactName;

        public View v;
        public ViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            contactName = (TextView) itemView.findViewById(R.id.contactName);
        }
    }

    // Store a member variable for the contacts
    private List<User> mUsers;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public contactsAdapter(Context context, List<User> users, OnItemClickListener listener) {
        mUsers = users;
        mContext = context;
        this.listener = listener;

    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View dayView = inflater.inflate(R.layout.adapter_contacts, parent, false);
        ViewHolder viewHolder = new ViewHolder(dayView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(contactsAdapter.ViewHolder viewHolder, final int position) {
        User usr = mUsers.get(position);

        TextView contaNameV = viewHolder.contactName;
        contaNameV.setText(usr.getFullName());

        viewHolder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}
