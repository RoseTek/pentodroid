package android.pentodroid.model;

class Coordinate extends Pair<Integer,Integer> {

    
    Coordinate(int lig, int col) {
	super(lig,col);
    }
    
    public int getLig() { return this.fst(); }
    public int getCol() { return this.snd(); }
    
}
