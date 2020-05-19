package com.example.lunaticat.application_pentodroid;

import android.content.ClipData;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import android.pentodroid.model.*;

/**
 * Created by Lunaticat on 13/03/2017.
 */

public class PentoView extends ImageView implements View.OnClickListener, View.OnLongClickListener {
    TheApplication app;
    Pentomino pento;

    public PentoView(Context c, AttributeSet as)
    {
        super(c,as);
        app = (TheApplication)c.getApplicationContext();
    }

    public boolean onLongClick(View v)
    {
        System.out.println("ON CLICK LONG");
        app.setSelectedPento(pento);
        // --------- drag
        ClipData data = ClipData.newPlainText("","");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        v.startDrag(data, shadowBuilder, null, 0);
        // ---------
        return true;
    }
    //USELESS
    void setPento(Pentomino id)
    {
        pento = id;
    }

    //USELESS
    Pentomino getPento()
    {
        return pento;
    }

    @Override public void onClick(View v)
    {
        System.out.println("ONCLICK pento selectionne : " + pento.name());
        app.setSelectedPento(pento);
    }
}
