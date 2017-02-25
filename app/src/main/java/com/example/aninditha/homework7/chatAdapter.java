package com.example.aninditha.homework7;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Aninditha on 11/23/2016.
 */

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.ViewHolder>{

    public  interface OnItemClickListener {
        public void onItemClicked(int position);
    }


    OnItemClickListener listener;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView msgView;
        public LinearLayout msgContainer;

        public View v;
        public ViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            msgView = (TextView) itemView.findViewById(R.id.msgView);
            msgContainer = (LinearLayout) itemView.findViewById(R.id.msgContainer);
        }
    }

    // Store a member variable for the contacts
    private List<Msg> mMsgs;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public chatAdapter(Context context, List<Msg> msgs, OnItemClickListener listener) {
        mMsgs = msgs;
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
        View dayView = inflater.inflate(R.layout.adapter_chat, parent, false);
        ViewHolder viewHolder = new ViewHolder(dayView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(chatAdapter.ViewHolder viewHolder, final int position) {
        Msg msg = mMsgs.get(position);

        TextView contaNameV = viewHolder.msgView;
        contaNameV.setText(msg.getText());

        LinearLayout msgContainerV = viewHolder.msgContainer;


        if(msg.getAuthorID().equals(EmailPasswordActivity.curUser)) {
            msgContainerV.setGravity(Gravity.RIGHT);
            contaNameV.setBackgroundColor(Color.GREEN);
            contaNameV.setTextColor(Color.DKGRAY);

        }
        else {
            msgContainerV.setGravity(Gravity.LEFT);
            contaNameV.setBackgroundColor(Color.BLUE);
            contaNameV.setTextColor(Color.WHITE);
        }

        viewHolder.v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onItemClicked(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMsgs.size();
    }

}
