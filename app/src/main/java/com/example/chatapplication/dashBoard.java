package com.example.chatapplication;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Window;
import android.view.WindowManager;

import com.example.chatapplication.services.userService;

public class dashBoard extends Activity {
    userService us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow();
            w.requestFeature(Window.FEATURE_NO_TITLE | Window.FEATURE_ACTIVITY_TRANSITIONS);
            w.setEnterTransition(new Slide());
            w.setExitTransition(new Slide());
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_dash_board);
        getView();
        bindEvent();
    }

    private void getView() {
        us = new userService(this, this);
    }

    private void bindEvent() {
        Intent intent = getIntent();
        String mId = intent.getStringExtra("id");
        us.validateUser(mId);
    }
}
