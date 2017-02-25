package com.example.aninditha.homework7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ContackProfileView extends AppCompatActivity {

    Button msg,viewAlbum;
    TextView contacttesttextView;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contack_profile_view);
        msg = (Button) findViewById(R.id.button3);
        viewAlbum = (Button) findViewById(R.id.button4);
        contacttesttextView = (TextView) findViewById(R.id.contactTestTextView);

        if(getIntent().getExtras()!=null){
            user = getIntent().getExtras().getString("user");
            contacttesttextView.setText(FindContacts.usenames.get(user).getFullName()+"'s Profile");
        } else{
            contacttesttextView.setText("Sorry, User Not Found");
        }

        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContackProfileView.this,ChatWindow.class);
                intent.putExtra("usertoconv",user);
                startActivityForResult(intent,003);
            }
        });

        viewAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContackProfileView.this,ViewAlbum.class);
                startActivityForResult(intent,004);
            }
        });
    }
}
