package com.example.aninditha.homework7;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

public class ChatWindow extends AppCompatActivity {

    static String user;
    Conversation conv;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    ImageView send;
    RecyclerView chatView;
    EditText msgText;
    TextView convWith;
    ArrayList<Msg> msgArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        send = (ImageView) findViewById(R.id.sendImageView);
        msgText = (EditText) findViewById(R.id.msgText);
        chatView = (RecyclerView) findViewById(R.id.chatView);
        convWith = (TextView) findViewById(R.id.convwithView);



        if(getIntent().getExtras()!=null){
            user = getIntent().getExtras().getString("usertoconv");

            Toast.makeText(ChatWindow.this,user,Toast.LENGTH_LONG).show();

            convWith.setText(EmailPasswordActivity.userArrayList.get(user).getFullName());
            convWith.setTextColor(Color.WHITE);
            convWith.setBackgroundColor(Color.DKGRAY);
        }




    }

    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference myConvRef = mDatabase.getReference("conversations");

        myConvRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                msgArrayList.clear();

                for(DataSnapshot postSnapShot: dataSnapshot.getChildren()){
                    Log.d("test", postSnapShot.toString());
                    if (postSnapShot.getKey().equals(EmailPasswordActivity.curUser)) {
                        for (DataSnapshot msgofConv : postSnapShot.getChildren()) {
                            Log.d("test1", msgofConv.toString());

                            if(msgofConv.getKey().equals(user)){
                                for(DataSnapshot msgsfromThisUser : msgofConv.getChildren()){
                                    Msg msg = msgsfromThisUser.getValue(Msg.class);
                                    if(!msg.isRead) {
                                        Msg newMsg = msg;
                                        newMsg.setRead(true);
                                        myConvRef.child(EmailPasswordActivity.curUser).child(user).child(msg.ID).setValue(newMsg);
                                    }
                                    msgArrayList.add(msg);
                                }
                            }
                        }
                        break;
                    }
                }

                if(msgArrayList.size()!=0) {

                    Collections.sort(msgArrayList, new Comparator<Msg>() {
                        @Override
                        public int compare(Msg msg, Msg t1) {
                            if(msg.getTime()>t1.getTime()) return 1;
                            else return -1;
                        }
                    });

                    chatAdapter adapter = new chatAdapter(ChatWindow.this, msgArrayList, new chatAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClicked(int position) {
                            myConvRef.child(EmailPasswordActivity.curUser).child(user).child(msgArrayList.get(position).ID).removeValue();

                        }
                    });

                    chatView.setAdapter(adapter);
                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(ChatWindow.this, LinearLayoutManager.VERTICAL, false);
                    chatView.setLayoutManager(layoutManager1);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String authorName = "";


                try{
                    authorName= EmailPasswordActivity.userArrayList.get(EmailPasswordActivity.curUser).getFullName();
                }catch (Exception e){
                    authorName= FindContacts.usenames.get(EmailPasswordActivity.curUser).getFullName();
                }

                Msg msg = new Msg(EmailPasswordActivity.curUser,authorName,msgText.getText().toString());
                String MsgID = UUID.randomUUID().toString();
                msg.ID = MsgID;
                msg.setRead(false);
                myConvRef.child(user).child(EmailPasswordActivity.curUser).child(MsgID).setValue(msg);
                myConvRef.child(EmailPasswordActivity.curUser).child(user).child(MsgID).setValue(msg);
                DatabaseReference readRef = mDatabase.getReference("ReadConversations");
                msgText.setText("");
            }
        });
    }
}
