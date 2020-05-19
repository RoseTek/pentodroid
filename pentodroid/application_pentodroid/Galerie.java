package com.example.lunaticat.application_pentodroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Galerie extends Activity {
    TheApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galerie);
        app = (TheApplication) (this.getApplication());
    }

    public void onClickExit(View v) {
        finish();
    }
}
