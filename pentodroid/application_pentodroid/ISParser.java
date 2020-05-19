package com.example.lunaticat.application_pentodroid;




import android.pentodroid.model.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import java.io.InputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by Lunaticat on 29/03/2017.
 */

public class ISParser {
    public ISParser(){}

    public ArrayList<Pair<Model,ArrayList<Pentomino>>> generePartie(InputStream is)
    {
        //build le document
        Document doc;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
        } catch (Exception ex) {
            System.out.println("Document creation fail");
            return null;
        }

        //recuperer la racine
        Element racine = doc.getDocumentElement();

        //recuperer la liste de Node
        NodeList listeNoeud = racine.getElementsByTagName("puzzle");

        ArrayList<Pair<Model,ArrayList<Pentomino>>> tab = new ArrayList<Pair<Model,ArrayList<Pentomino>>>();
        //pour chaque puzzle
        for (int i = 0; i<listeNoeud.getLength() ; i++)
        {
            Node noeud = listeNoeud.item(i);
            //recupere la liste de couples attribut - valeur
            NamedNodeMap attrs = noeud.getAttributes();
            //recupere les attributs width et height dans attrs
            int width = Integer.parseInt(attrs.getNamedItem("width").getNodeValue());
            int height = Integer.parseInt(attrs.getNamedItem("height").getNodeValue());
            //genere Model
            Model m = new Model(width, height);
            //System.out.println("width " + width + "height " + height);

            //recupere la liste de nodes qui contient les pentominos
            NodeList pento = noeud.getChildNodes();
            //pour chaque node
            ArrayList<Pentomino> pp = new ArrayList<Pentomino>();
            for (int n = 0; n<pento.getLength() ; n++)
            {
                //SI c'est un pentomino valide => ajoute le dans un arraylist de pentomino
                Node sousNoeud = pento.item(n);
                //si sousNoeud a pour nom pento
                if ((sousNoeud.getNodeName()).equals("pento"))
                {
                    //System.out.println(sousNoeud.getAttributes().getNamedItem("id").getNodeValue());
                    //add un pentomino du bon nom dans l'arrayList pp
                    pp.add(Pentomino.valueOf(sousNoeud.getAttributes().getNamedItem("id").getNodeValue()));
                }
            }
            //ajoute la paire m, pp dans l'arrayList
            //Pair<Model,ArrayList<Pentomino>> paire = new Pair<Model,ArrayList<Pentomino>>(m, pp);
            Pair<Model,ArrayList<Pentomino>> paire = new Pair<Model, ArrayList<Pentomino>>(m, pp);
            tab.add(paire);
        }
        return tab;
    }
}
