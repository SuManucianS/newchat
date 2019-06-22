package com.example.chatapplication.services;

import android.content.Context;
import android.widget.Toast;

import com.example.chatapplication.model.userModel;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class userService {
    Context mContext;
    userModel userModel;
    DatabaseReference reference;

    public  userService(Context context){
        mContext = context;
    }

    public void registerToDBService(FirebaseUser user){
        userModel = new userModel();
        userModel.setId(user.getUid());
        userModel.setImageURL("default");
        userModel.setUsername(user.getDisplayName());
        userModel.setStatus("online");
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("user/" + user.getUid())) {
                    //User Exists , No Need To add new data.
                    Toast.makeText(mContext, "1", Toast.LENGTH_LONG).show();
                    //Get previous data from firebase. It will take previous data as soon as possible..
                    return;
                } else {
                    Toast.makeText(mContext, "2", Toast.LENGTH_LONG).show();
                    FirebaseDatabase.getInstance().getReference().child("user/" + user.getUid()).setValue(userModel);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(mContext, databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
