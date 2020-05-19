package com.example.lunaticat.application_pentodroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.pentodroid.model.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Lunaticat on 13/03/2017.
 */

public class SelectLevel extends Activity{
    TheApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_level);
        app = (TheApplication) (this.getApplication());
    }

    public void onClickExit(View v){
        finish();
    }

    private void launch_game()
    {
        Intent play = new Intent(this, PlayActivity.class);
        startActivity(play);
    }

    public void onClickEasyLevel(View v){
        InputStream is = getResources().openRawResource(R.raw.pento_easy);
        ISParser p = new ISParser();
        app.setParties(p.generePartie(is));
        app.init_score("score_easy");
        launch_game();
    }

    public void onClickMediumLevel(View v){
        InputStream is = getResources().openRawResource(R.raw.pento_medium);
        ISParser p = new ISParser();
        app.setParties(p.generePartie(is));
        app.init_score("score_medium");
        launch_game();
    }

    public void onClickHardLevel(View v){
        InputStream is = getResources().openRawResource(R.raw.pento_hard);
        ISParser p = new ISParser();
        app.setParties(p.generePartie(is));
        app.init_score("score_hard");
        launch_game();
    }
}
