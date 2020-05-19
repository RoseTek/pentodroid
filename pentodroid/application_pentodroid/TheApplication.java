package com.example.lunaticat.application_pentodroid;

import android.app.Application;
import android.content.Context;
import android.view.SurfaceView;
import android.view.View;

import android.pentodroid.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Lunaticat on 13/03/2017.
 */

public class TheApplication extends Application {
    private ArrayList<Pair<Model,ArrayList<Pentomino>>> parties;
    private ArrayList<Integer> scores;
    private int partie_courante;
    private Pentomino selectedPento;
    private GameBoard board;
    public String scorefile = null;

    @Override
    public void onCreate(){
        super.onCreate();
        parties = new ArrayList<>();
        scores = new ArrayList<>();
        partie_courante = 0;
        selectedPento = null;
    }

    public void setSurface(GameBoard v){
        board = v;
    }

    public void reset()
    {
        ArrayList<Pair<Pentomino,ArrayList<Coordinate>>> places = getModel().placedPentominos();
        int nbpieces = places.size();
        for (int i=0 ; i<nbpieces ; i++)
        {
            Pentomino pento = places.get(0).fst();
            getModel().remove(pento);
        }
        scores.set(partie_courante, 0);
    }

    public void refresh()
    {
        board.reDraw();    //force le redraw
    }

    public void victoire()
    {
        scores.set(partie_courante, scores.get(partie_courante)+1);
    }

    public int getScore()
    {
        //return 1;
        return scores.get(partie_courante);
    }

    public void resetScores(String file)
    {
        FileOutputStream oc = null;
        try
        {
            oc = openFileOutput(file, Context.MODE_PRIVATE);
            //
            for (int i = 0 ; i<scores.size() ; i++) {
                String line = "0\n";
                oc.write(line.getBytes());
            }
            //
            oc.close();
        }
        catch (Exception e) {
        }

    }

    public void saveScore()
    {
        FileOutputStream oc = null;
        try
        {
            oc = openFileOutput(scorefile, Context.MODE_PRIVATE);
            //
            for (int i = 0 ; i<scores.size() ; i++) {
                String line = scores.get(i) + "\n";
                oc.write(line.getBytes());
                System.out.println("SCORES: " +i + "-" + line);
            }
            //
            oc.close();
        }
        catch (Exception e)
        {
            System.out.println("\tFAILED TO SAVE SCORE");
            try
            {
                oc.close();
            }
            catch (Exception ee) {}
        }
    }

    public void init_score(String file){
        scorefile = file;
        scores.clear();
        for (int i=0; i<5 ; i++)
            scores.add(0);

        System.out.println("\tSCORES: "+ scorefile);
        //-----------------------------------------------------
        try {
            FileInputStream fis = openFileInput(scorefile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader buff = new BufferedReader(isr);
            String line;
            int i = 0;
            while ((line = buff.readLine()) != null)
            {
                //ajoute le score
                scores.set(i, Integer.parseInt(line));
                System.out.println("SCORE puzle " + i + " = " + line);
                i++;
            }
            fis.close();
        }
        catch (Exception e){
            System.out.println("SCORE INIT FAILED");
        }
    }

    public Model getModel()
    {
        //System.out.println("Modele : " + partie_courante);
        return parties.get(partie_courante).fst();
    }

    public boolean levelVictory()
    {
        for (int i = 0 ; i<scores.size() ; i++) {
            if (scores.get(i) == 0)
                return false;
        }
        return true;
    }

    public ArrayList<Pentomino> getPieces()
    {
        return parties.get(partie_courante).snd();
    }

    public Pentomino getSelectedPento(){
        return selectedPento;
    }

    public void setSelectedPento(Pentomino p)
    {
        selectedPento = p;
    }

    public void unsetSelectedPento() { selectedPento = null; }

    public void nextLevel()
    {
        partie_courante++;
        if (partie_courante >= parties.size())
            partie_courante = 0;
    }

    public void prevLevel()
    {
        partie_courante--;
        if (partie_courante < 0)
            partie_courante = parties.size()-1;
    }

    public void setParties(ArrayList<Pair<Model,ArrayList<Pentomino>>> p)
    {
        parties = p;
        partie_courante = 0;
    }
}
