package com.example.chatapplication.system;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapplication.R;
import com.example.chatapplication.services.userService;


public class loginActivity extends AppCompatActivity {
    EditText etusername, etpassword;
    Button btnsignin;
    TextView tvforgot, tvregister;
    userService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_login);
        getView();
        bindEvent();
    }

    private void getView() {
        etusername = (EditText) findViewById(R.id.etLoginUsername);
        etpassword = (EditText) findViewById(R.id.etLoginPassword);
        btnsignin = (Button) findViewById(R.id.btnLoginSignIn);
        tvforgot = (TextView) findViewById(R.id.tvLoginForgot);
        tvregister = (TextView) findViewById(R.id.tvLoginRegister);
        userService = new userService(getApplicationContext());
    }

    private void bindEvent() {
        btnsignin.setOnClickListener(v -> singin());
        tvregister.setOnClickListener(v -> toRegister());

    }

    private void singin() {
    }

    private void toRegister() {
        Intent intent = new Intent(this, registerActivity.class);
        startActivity(intent);
    }
}
