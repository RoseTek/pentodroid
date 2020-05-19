package com.example.lunaticat.application_pentodroid;

import android.pentodroid.model.*;

/**
 * Created by Lunaticat on 22/03/2017.
 */

public enum Couleur {
    F(252, 220, 18),
    I(150, 0, 24),
    L(218,112,214),
    N(254, 191, 210),
    P(255,228,196),
    T(135,89,26),
    U(119,181,254),
    V(84,249,141),
    W(0,51,153),
    X(22,184,78),
    Y(199,44,72),
    Z(248,142,85);

    private int r, g, b;

    private Couleur(int re,int gr, int bl)
    {
        r = re;
        g = gr;
        b = bl;
    }

    public int red()
    {
        return r;
    }

    public int green()
    {
        return g;
    }

    public int blue()
    {
        return b;
    }

    }