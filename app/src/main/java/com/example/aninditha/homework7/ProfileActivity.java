package com.example.aninditha.homework7;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    GoogleApiClient mGoogleApiClient;
    ImageView logout, profilePic;
    TextView fName, lName, fullName, gender;
    ArrayList<User> userL = new ArrayList<>();
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    DatabaseReference myRef = mDatabase.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        logout = (ImageView) findViewById(R.id.signOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        FirebaseAuth.getInstance().signOut();
                    }

                });
                Intent i = new Intent(ProfileActivity.this, EmailPasswordActivity.class);
                startActivity(i);
            }


        });

        fName = (TextView) findViewById(R.id.firstNameText);
        lName = (TextView) findViewById(R.id.lastNameText);
        fullName = (TextView) findViewById(R.id.fullNameText);
        gender = (TextView) findViewById(R.id.genderText);


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userL.clear();
                Log.d("demo123", dataSnapshot.toString());
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    Log.d("demo123", postSnapshot.toString());
                    //if(postSnapshot.getKey().equals(EmailPasswordActivity.curUser)){
                    for (DataSnapshot expenses : postSnapshot.getChildren()) {
                        Log.d("demo1234", expenses.getValue().toString());
                        userL.add(expenses.getValue(User.class));
                    }
                    //}
                }
                disp(userL);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    void disp(ArrayList<User>uList){
        for(User u:uList){
            if(EmailPasswordActivity.curUser.equals(u.getEmail())){
                fName.setText(u.getFistName());
                lName.setText(u.getLastName());
                fullName.setText(u.getFullName());
                gender.setText(u.getGender());

            }
        }

    }
}