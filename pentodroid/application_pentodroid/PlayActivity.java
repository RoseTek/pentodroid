package com.example.lunaticat.application_pentodroid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.pentodroid.model.*;

import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by Lunaticat on 13/03/2017.
 */

public class PlayActivity extends Activity {
    TheApplication app;

    protected void loadPuzzle()
    {
        ArrayList<Pentomino> p = app.getPieces();

        app.unsetSelectedPento();

        //---------affichage map en cours
        //System.out.println("height : " + m.height() + " width : " + m.width());
        //for (Pentomino pt : p)
        //   System.out.println("Piece : " + pt.name());
        //---------

        LinearLayout sv = (LinearLayout)findViewById(R.id.pento_list_view);
        sv.removeAllViews();
        Context cx = this.getApplicationContext();

        //recupere les images + set listener
        for (Pentomino pento : p)
        {
            String name = ""+pento.name().toLowerCase();
            int img = getResources().getIdentifier(name, "drawable", getPackageName());
            PentoView iv = new PentoView(cx, null);
            iv.setImageResource(img);
            iv.setPento(pento);
            iv.setOnClickListener(iv);
            iv.setOnLongClickListener(iv);
            sv.addView(iv);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        app = (TheApplication) (this.getApplication());
        //charge la partie
        loadPuzzle();
    }

    public void onClickReset(View v){
        app.reset();
        loadPuzzle();
        //force a refresh
        app.refresh();
   }

    public void onClickNext(View v){
        app.nextLevel();
        //charge la partie
        loadPuzzle();
        app.refresh();
    }

    public void onClickPrev(View v){
        app.prevLevel();
        //charge la partie
        loadPuzzle();
        app.refresh();
    }

    public void onClickExit(View v)
    {
        //sauvegarde le score
        app.saveScore();
        finish();
    }
}
