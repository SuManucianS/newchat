package com.example.chatrealtime;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtusername, edtmail, edtpassword;
    private Button btncreate;
    FirebaseAuth auth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_mail = edtmail.getText().toString();
                String txt_username = edtusername.getText().toString();
                String txt_password = edtpassword.getText().toString();
                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_mail) || TextUtils.isEmpty(txt_password)){  // kiem tra
                    Toast.makeText(RegisterActivity.this, "all fileds are required", Toast.LENGTH_SHORT).show();
                }
                else if(txt_password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "password lon hon 6 ky tu", Toast.LENGTH_SHORT).show();
                }else
                    Register(txt_username, txt_mail, txt_password);
            }
        });
    }

    private void addControls() {
        edtmail = findViewById(R.id.edtmail);
        edtusername = findViewById(R.id.edtusername);
        edtpassword = findViewById(R.id.edtpassword);
        btncreate = findViewById(R.id.btncreate);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth = FirebaseAuth.getInstance();

    }
    private void Register (final String username, String mail, String password){
        // create new user
        auth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser(); // get currenuser
                    assert firebaseUser != null;
                    String userid = firebaseUser.getUid(); // get id
                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid); // create user
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("username", username);
                    hashMap.put("imageURL", "default");
                    hashMap.put("status", "online");
                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "ok", Toast.LENGTH_SHORT).show();
                                //Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                //startActivity(intent);
                                //finish();
                            }
                        }
                    });
                }else
                    Toast.makeText(RegisterActivity.this, "Mail or Password incorrect", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
