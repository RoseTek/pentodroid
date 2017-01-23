package android.pentodroid.model;

import java.util.ArrayList;

public interface IModel {

    /* Largeur du rectangle à construire */
    public int width();
    /* Hauteur du rectangle à construire */
    public int height();

    /* Nom de la pièce posée aux coordonnées passées en argument.
       Si la position ne correspond à aucune pièce, la méthode
       déclenche l'exception NotFound */
    public Pentomino get(Coordinate pos) throws NotFound;

    /* Donne true si la position passée en argument est valide
       dans le rectangle à construire, qu'elle soit vide ou non */
    public boolean valid(Coordinate pos);

    /* Donne true si la position donnée en argument est une position 
       valide du rectangle à construire non couverte par une pièce. 
       On dit alors que la position est libre. */
    public boolean free(Coordinate pos);

    /* Donne true si le rectangle à construire est achevé
       et false sinon. */
    public boolean achieved();

    /* Donne true si la pièce donnée en argument est placée sur le
       rectangle à construire et false sinon. */
    public boolean isPlaced(Pentomino id);

    /* Donne true si la pièce passée en argument peut être placée à
       la position donnée en second argument et false sinon. 
       La position donnée en second argument est celle du premier carré 
       de la pièce. 
       Une pièce peut être placée si et seulement si:
        a) elle n'est pas déjà placée;
        b) la position est valide;
        c) toutes les positions que recouvrira la pièce sont libres. */
    public boolean canPut(Pentomino id, Coordinate pos);

    /* Place sur le rectangle à construire la pièce donnée en argument
       à la position donnée en second argument. On peut faire l'hypothèse 
       que canPut(id,pos) donne true. */
    public void put(Pentomino id, Coordinate pos);

    /* Retire la pièce donnée en argument du rectangle en construction.
       Ne fait rien si la pièce n'est pas placée. */
    public void remove(Pentomino id);

    /* Donne la liste des pièces placées associées, chacune, à la liste 
       des positions qu'elles couvrent. */
    public ArrayList<Pair<Pentomino,ArrayList<Coordinate>>>placedPentominos();
    
    
}
