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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;

public class UserHome extends AppCompatActivity {

   // FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ArrayList<User> userList = new ArrayList<>();
    static HashMap<String,User> usenames =  new HashMap<>();
    RecyclerView contactsView;
    String currentUser;
    ImageView logout, profilePic;
    TextView fName, lName, fullName, gender;
    ArrayList<User> userL = new ArrayList<>();
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    DatabaseReference myRef = mDatabase.getReference("users");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        Toast.makeText(this,"THIS IS USER PROFILE",Toast.LENGTH_LONG).show();

        if (mAuth.getCurrentUser()!=null){
            currentUser = mAuth.getCurrentUser().getUid();
        }

        fName = (TextView) findViewById(R.id.firstNameText);
        lName = (TextView) findViewById(R.id.lastNameText);
        fullName = (TextView) findViewById(R.id.fullNameText);
        gender = (TextView) findViewById(R.id.genderText);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu1) {
        MenuInflater inflater = getMenuInflater();
       // inflater.inflate(R.menu.profilemenuopts, menu1);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editProfile :
                Toast.makeText(this,"View Profile Activity Call.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserHome.this,EditProfile.class);
                startActivityForResult(intent,456);
                break;
            case R.id.backtoInbox :
                Intent in = new Intent();
                setResult(RESULT_OK,in);
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference myRef = mDatabase.getReference("users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    userList.add(user);
                    usenames.put(postSnapshot.getKey(),postSnapshot.getValue(User.class));
                }

                for(User u:userList){
                    if(u.getId().equals(currentUser)){
                        fName.setText(u.getFistName());
                        lName.setText(u.getLastName());
                        fullName.setText(u.getFullName());
                        gender.setText(u.getGender());
                    }
                }




            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
