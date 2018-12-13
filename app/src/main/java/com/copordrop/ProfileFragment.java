package com.copordrop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by MJMJ2 on 20/05/17.
 */
public class ProfileFragment extends Fragment {

    private TextView mProfileName;
    private TextView mProfileEmail;

    private DatabaseReference mDatabaseUser;

    private Button mEditProfile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.activity_profile, container, false);

        mProfileName = (TextView) v.findViewById(R.id.profileName);
        mProfileEmail = (TextView) v.findViewById(R.id.profileEmail);

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users");

        mEditProfile = (Button) v.findViewById(R.id.editButton);

        mEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(mainIntent);
            }
        });


        return v;
    }

    @Override
    public void onStart(){
        super.onStart();

        mDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    Users users = dataSnapshot.child(MainActivity.user_id).getValue(Users.class);

                    mProfileName.setText(users.getFirst_name() + " " + users.getLast_name());
                    mProfileEmail.setText(users.getEmail());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
