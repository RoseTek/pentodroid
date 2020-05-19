package com.example.lunaticat.application_pentodroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Lunaticat on 23/04/2017.
 */

public class ImageG extends SurfaceView implements SurfaceHolder.Callback{
    private TheApplication app;
    private int height;
    private int width;
    private String easy = "galerie_easy";
    private String medium = "galerie_medium";
    private String hard = "galerie_hard";
    private String bonus = "galerie_bonus";
    private String selected = null;

    private void init_score(){
        app.init_score("score_easy");
        if (!app.levelVictory())
            easy += "_none";
        app.init_score("score_medium");
        if (!app.levelVictory())
            medium += "_none";
        app.init_score("score_hard");
        if (!app.levelVictory())
            hard += "_none";

        if (easy.contains("none") || medium.contains("none") || hard.contains("none"))
            bonus += "_none";
    }

    public ImageG(Context c){
        super(c);
        getHolder().addCallback(this);
        app = (TheApplication)(c.getApplicationContext());
        init_score();
    }

    public ImageG(Context c, AttributeSet as){
        super(c,as);
        getHolder().addCallback(this);
        app = (TheApplication)(c.getApplicationContext());
        init_score();
    }

    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh) {
        //System.out.println("SIZE CHANGED " + w + " " + h + "old : " + oldw + " "+ oldh);
        width = w;
        height = h;
    }


    @SuppressLint("WrongCall")
    public void reDraw(){
        Canvas c = getHolder().lockCanvas();
        if (c != null)
        {
            this.onDraw(c);
            getHolder().unlockCanvasAndPost(c);
        }
    }

    //FONCTION QUI DESSINE
    @Override
    public void onDraw(Canvas c){
        c.drawColor(Color.LTGRAY);
        Paint p = new Paint();

        Bitmap myicon = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(easy, "drawable", app.getPackageName()));
        c.drawBitmap(Bitmap.createScaledBitmap(myicon,width/2,height/2,false), 0, 0, p);
        myicon = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(medium, "drawable", app.getPackageName()));
        c.drawBitmap(Bitmap.createScaledBitmap(myicon,width/2,height/2,false), width/2, 0, p);
        myicon = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(hard, "drawable", app.getPackageName()));
        c.drawBitmap(Bitmap.createScaledBitmap(myicon,width/2,height/2,false), 0, height/2, p);
        myicon = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(bonus, "drawable", app.getPackageName()));
        c.drawBitmap(Bitmap.createScaledBitmap(myicon,width/2,height/2,false), width/2, height/2, p);

        if (selected != null)
        {
            myicon = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(selected, "drawable", app.getPackageName()));
            c.drawBitmap(Bitmap.createScaledBitmap(myicon,width,height,false), 0, 0, p);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){}

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        reDraw();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){}

    @Override
    public boolean onTouchEvent(MotionEvent event){

        int x = (int)event.getX();
        int y = (int)event.getY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            {
                if (selected == null) {
                    if (x < height / 2 && y < width / 2 && !easy.contains("none"))
                        selected = easy;
                    else if (x > height / 2 && y < width / 2 && !medium.contains("none"))
                        selected = medium;
                    else if (x > height / 2 && y > width / 2 && !bonus.contains("none"))
                        selected = bonus;
                    else if (x < height / 2 && y > width / 2 && !hard.contains("none"))
                        selected = hard;
                    this.reDraw();
                    return true;
                }
                else {
                    selected = null;
                    this.reDraw();
                    return true;
                }
            }
            default:
                return false;
        }
    }
}
