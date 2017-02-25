package com.example.aninditha.homework7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class InBox extends AppCompatActivity {



    ArrayList<Conversation> conversations = new ArrayList<>();
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    RecyclerView inBocChats;
    TextView noMsgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_box);
        Toast.makeText(this,"THIS IS USER INBOX",Toast.LENGTH_LONG).show();
        inBocChats = (RecyclerView) findViewById(R.id.inboxChats);
        noMsgs = (TextView) findViewById(R.id.noMsgs);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menuopts, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
            case R.id.profileView :
                //Toast.makeText(this,"View Profile Activity Call.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InBox.this,UserHome.class);
                startActivityForResult(intent,123);
                break;
            case R.id.findContacts :
                Intent intent1 = new Intent(InBox.this,FindContacts.class);
                startActivityForResult(intent1,109);
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onStart() {
        super.onStart();



        final DatabaseReference myConvRef = mDatabase.getReference("conversations");

        myConvRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                conversations.clear();

                for(DataSnapshot postSnapShot: dataSnapshot.getChildren()){
                    Log.d("test", postSnapShot.toString());
                    if (postSnapShot.getKey().equals(EmailPasswordActivity.curUser)) {
                        for (DataSnapshot msgofConv : postSnapShot.getChildren()) {
                            Log.d("test1", msgofConv.toString());
                            String name;

                            try {
                                name = EmailPasswordActivity.userArrayList.get(msgofConv.getKey()).getFullName();
                            }catch (Exception e){
                                name = "not Loaded";
                                e.printStackTrace();
                            }

                            Conversation con = new Conversation(msgofConv.getKey(),name);
                            long temp = 0;
                            for(DataSnapshot msgsfromThisUser : msgofConv.getChildren()){
                                Msg msg = msgsfromThisUser.getValue(Msg.class);
                                long time =  msg.getTime();
                                if(time>temp) con.setLatestMessaage(msg);
                                if(!msg.isRead) con.incUnRead();
                            }
                            conversations.add(con);
                        }
                        break;
                    }
                }


                if(conversations.size()!=0) {
                    noMsgs.setVisibility(View.GONE);
                    InboxAdapter adapter = new InboxAdapter(InBox.this, conversations, new InboxAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClicked(int position) {
                            Intent intent3 = new Intent(InBox.this,ChatWindow.class);
                            intent3.putExtra("usertoconv",conversations.get(position).authorID);
                            startActivityForResult(intent3,007);


                        }
                    });

                    inBocChats.setAdapter(adapter);
                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(InBox.this, LinearLayoutManager.VERTICAL, false);
                    inBocChats.setLayoutManager(layoutManager1);
                }else{
                    noMsgs.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}

