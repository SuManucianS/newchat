package com.example.chatapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


import com.example.chatapplication.system.loginActivity;

public class MainActivity extends Activity {
    Button btnsend, btnout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow();
            w.requestFeature(Window.FEATURE_NO_TITLE);
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_main);
        btnsend = (Button) findViewById(R.id.btnLogin);
        btnout = (Button) findViewById(R.id.btnOut);
        btnsend.setOnClickListener(v -> toLogin());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnout.setOnClickListener(v -> finishAndRemoveTask());
        } else {
            btnout.setOnClickListener(v -> finish());
        }
    }

    private void toLogin() {
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
    }

    public void blah() {

    }

}
