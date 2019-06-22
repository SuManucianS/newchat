package com.example.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapplication.services.googleSignInService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    TextView tvresult;
    Button btnsend;
    EditText txtemail;
    SignInButton signInButton;
    googleSignInService gsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gsis = new googleSignInService(this, getString(R.string.default_web_client_id));
        tvresult = (TextView) findViewById(R.id.tvResult);
        btnsend = (Button) findViewById(R.id.btnSend);
        txtemail = (EditText) findViewById(R.id.txtEmail);
        signInButton = (SignInButton) findViewById(R.id.btnGoogleButton);
        signInButton.setOnClickListener(v -> blah());
    }

    public void blah() {
        Intent intent = new Intent(gsis.mGoogleSignInClient.getSignInIntent());
        startActivityForResult(intent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gsis.handleResult(requestCode,resultCode,data,this);
    }
}
