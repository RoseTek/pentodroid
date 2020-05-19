package com.example.lunaticat.application_pentodroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import android.pentodroid.model.*;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Lunaticat on 13/03/2017.
 */

public class GameBoard extends SurfaceView implements SurfaceHolder.Callback, View.OnDragListener {
    TheApplication app;
    int height;
    int width;
    int box = 140;

    public GameBoard(Context c){
        super(c);
        getHolder().addCallback(this);
        this.setOnDragListener(this);
        app = (TheApplication)(c.getApplicationContext());
        app.setSurface(this);
    }

    public GameBoard(Context c, AttributeSet as){
        super(c,as);
        getHolder().addCallback(this);
        this.setOnDragListener(this);
        app = (TheApplication)(c.getApplicationContext());
        app.setSurface(this);
    }


    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh)
    {
        width = w;
        height = h;
        box = (Math.min(h,w))/7;
    }

    @Override
    public boolean onDrag(View v, DragEvent event)
    {
        int x = (int)event.getX();
        int y = (int)event.getY();
        int action = event.getAction();

        switch (action) {
            case DragEvent.ACTION_DROP:
                System.out.println("ACTION DROP");
                if (app.getSelectedPento() != null)
                {
                    app.getModel().put(app.getSelectedPento(), new Coordinate(y / box, x / box));
                    app.unsetSelectedPento();
                    if (app.getModel().achieved() == true)
                        app.victoire();
                    this.reDraw();
                }

                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                app.unsetSelectedPento();
                return true;
            case DragEvent.ACTION_DRAG_STARTED: // continue le drag
                return true;
            default:
                return false;
        }
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

    protected void drawPento(Pentomino pento, ArrayList<Coordinate> coord, Canvas canvas, Paint paint) {
        Couleur color = Couleur.valueOf(pento.name().substring(0,1));
        paint.setColor(Color.rgb(color.red(),color.green(),color.blue()));

        for (Coordinate c : coord)
        {
            //System.out.println("PENTO" + c.getCol() + " " + c.getLig());
            paint.setStrokeWidth(20);
            canvas.drawRect(c.getCol()*box, c.getLig()*box, (c.getCol()+1)*box, (c.getLig() + 1)*box, paint);
        }
    }

    //FONCTION QUI DESSINE
    @Override
    public void onDraw(Canvas c){
        c.drawColor(Color.LTGRAY);
        Paint p = new Paint();

        Iterator<Pair<Pentomino, ArrayList<Coordinate>>> it = app.getModel().placedPentominos().iterator();
        while(it.hasNext()) {
            Pair<Pentomino, ArrayList<Coordinate>> tmp = it.next();
            drawPento(tmp.fst(), tmp.snd(), c, p);
        }

        p.setStrokeWidth(3);
        p.setColor(Color.DKGRAY);
        //dessin de la grille
        int size_x = app.getModel().width();
        int size_y = app.getModel().height();
        for (int i = 0 ; i <= size_x ; i++)
            c.drawLine(i*box, 0, i*box, box*size_y, p);
        for (int i = 0 ; i <= size_y ; i++)
            c.drawLine(0, i*box, box*size_x, i*box, p);

        if (app.getScore() != 0) {
            //choisit l'image a afficher => easy/medium/hard
            String finished = "finished_" + (app.scorefile).substring(6);
            System.out.println(finished);
            Bitmap myicon = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(finished, "drawable", app.getPackageName()));
            c.drawBitmap(Bitmap.createScaledBitmap(myicon,width,height,false), 0, 0, p); // a definir en fonction de la taille de l'ecran
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
                if (app.getSelectedPento() == null)
                {
                    try {
                        Pentomino p = app.getModel().get(new Coordinate(y/box, x/box));
                        app.getModel().remove(p);
                        this.reDraw();
                    }
                    catch (NotFound e){
                        //pentomino non trouvé
                    }
                }
                else
                {
                    app.getModel().put(app.getSelectedPento(), new Coordinate(y / box, x / box));
                    app.unsetSelectedPento();
                    if (app.getModel().achieved() == true)
                        app.victoire();
                    this.reDraw();
                }
                return true;
            }
            default:
                return false;
        }
    }
}
