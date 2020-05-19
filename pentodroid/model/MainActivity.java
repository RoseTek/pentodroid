package com.example.lunaticat.application_pentodroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    TheApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (TheApplication)(this.getApplication());
    }

    public void onClickSelectLevel(View v){
        Intent selectLevelIntent = new Intent(this, SelectLevel.class);
        startActivity(selectLevelIntent);
    }

    public void onClickGalerie(View v){
        Intent selectLevelIntent = new Intent(this, Galerie.class);
        startActivity(selectLevelIntent);
    }

    public void onClickReset(View v){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setTitle("Confirmation");
        alertDialog.setMessage("Are you sure to reset?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        app.resetScores("score_easy");
                        app.resetScores("score_medium");
                        app.resetScores("score_hard");
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NOPE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }

    public void onClickExit(View v)
    {
        finish();
    }
}
