package com.example.chatrealtime;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chatrealtime.Adapter.MessageAdapter;
import com.example.chatrealtime.Model.Chats;
import com.example.chatrealtime.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {
    private CircleImageView circleImageView;
    private TextView tv_username;
    private Toolbar toolbars;
    private ImageButton imgbtnsend;
    private EditText edtmessage;
    private RecyclerView recycler_message;
    private String useridd;
    private MessageAdapter messageAdapter;
    private List<Chats> chatlist;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        addControls();
        addEvents();
    }

    private void addEvents() {
        toolbars.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //startActivity(new Intent(MessageActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users").child(useridd);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                tv_username.setText(user.getUsername());
                if (user.getImageURL().equals("default")){
                    circleImageView.setImageResource(R.drawable.dogemeeme);
                }else
                    Glide.with(MessageActivity.this).load(user.getImageURL()).into(circleImageView);
                readMessage(firebaseUser.getUid(), useridd, user.getImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        imgbtnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = edtmessage.getText().toString();
                if (!message.equals("")){
                    sendMessage(firebaseUser.getUid(), useridd, message);  // useridd nguoi nhan
                }else {

                    edtmessage.findFocus();
                    Toast.makeText(MessageActivity.this, "pls enter a message", Toast.LENGTH_SHORT).show();
                }
                edtmessage.setText("");
            }
        });
    }

    private void addControls() {
        toolbars = findViewById(R.id.toolbarmessage);
        setSupportActionBar(toolbars);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        circleImageView = findViewById(R.id.message_profile_user);
        tv_username = findViewById(R.id.tvusername_message);
        imgbtnsend = findViewById(R.id.img_btnsend);
        edtmessage = findViewById(R.id.edt_entermessage);
        recycler_message = findViewById(R.id.recycler_message);
        recycler_message.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setStackFromEnd(true);
        recycler_message.setLayoutManager(manager);
        intent = getIntent();
        useridd = intent.getStringExtra("userid");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }
    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        reference.child("Chats").setValue(hashMap);
    }

    private void readMessage(final  String myID, final  String userID,final String imageurl){
        chatlist = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatlist.clear();

                    Chats chat = dataSnapshot.getValue(Chats.class);
                    if (chat.getReceiver().equals(userID) && chat.getSender().equals(myID) ||
                    chat.getReceiver().equals(myID) && chat.getSender().equals(userID)){
                        chatlist.add(chat);
                    }
                    messageAdapter = new MessageAdapter(MessageActivity.this, chatlist, imageurl);
                    recycler_message.setAdapter(messageAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void status(String status ){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> putmap = new HashMap<>();
        putmap.put("status", status);
        reference.updateChildren(putmap);
    }
    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }

}
