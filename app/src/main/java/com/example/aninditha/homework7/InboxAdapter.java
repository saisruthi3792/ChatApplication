package com.example.aninditha.homework7;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Aninditha on 11/23/2016.
 */

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> {
    public  interface OnItemClickListener {
        public void onItemClicked(int position);
    }


    OnItemClickListener listener;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView inbox_contact;
        public TextView unreadMsgs;
        public TextView inbox_message;
        public ImageView inbox_dp;
        public LinearLayout inboxContainer;

        public View v;
        public ViewHolder(View itemView) {
            super(itemView);
            v = itemView;

            inbox_dp = (ImageView) itemView.findViewById(R.id.inbox_dp);
            inbox_message = (TextView) itemView.findViewById(R.id.inbox_msg);
            inbox_contact = (TextView) itemView.findViewById(R.id.inbox_contact);
            inboxContainer = (LinearLayout) itemView.findViewById(R.id.inboxContainer);
            unreadMsgs = (TextView) itemView.findViewById(R.id.unreadMsgs);
        }
    }

    // Store a member variable for the contacts
    private List<Conversation> mConvs;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public InboxAdapter(Context context, List<Conversation> convs, OnItemClickListener listener) {
        mConvs = convs;
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
        View dayView = inflater.inflate(R.layout.adapter_inbox, parent, false);
        ViewHolder viewHolder = new ViewHolder(dayView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(InboxAdapter.ViewHolder viewHolder, final int position) {
        Conversation conv = mConvs.get(position);


        TextView inbox_contactV = viewHolder.inbox_contact;
        inbox_contactV.setText(conv.getAuthorName());

        TextView inbox_messageV = viewHolder.inbox_message;
        if(conv.getLatestMessaage().getText().length()<10)  inbox_messageV.setText(conv.getLatestMessaage().getText());
        else inbox_messageV.setText(conv.getLatestMessaage().getText().substring(0,10)+"...");

        ImageView inbox_dpV = viewHolder.inbox_dp;

        TextView unreadV = viewHolder.unreadMsgs;

        LinearLayout inboxContainerV = viewHolder.inboxContainer;
        if(conv.getUnRead()>0){
            unreadV.setText(conv.unRead+" unread messages");
        }else{
            unreadV.setText("");
        }


        viewHolder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mConvs.size();
    }
}
