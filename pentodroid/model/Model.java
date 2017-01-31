package android.pentodroid.model;

import java.util.ArrayList;

public class Model implements IModel{
	private int width;
	private int height;
	private boolean grille[][];//case libre = true
    private ArrayList<Pair<Pentomino,ArrayList<Coordinate>>> pento;
    
    //gestion taille invalide
	public Model(int width, int height){
		this.width = width;
		this.height = height;
		grille = new boolean[height][width];
		for (int i=0 ; i<height ; i++)
			for (int n=0; n<width ; n++)
				grille[i][n] = true;
		pento = new ArrayList<Pair<Pentomino,ArrayList<Coordinate>>>();
	}
	
    public int width(){
    	return width;
    }
    
    public int height(){
    	return height;
    }
    
    public boolean valid(Coordinate pos){
    	if (pos.getCol() < 0 || pos.getCol() >= width || pos.getLig() < 0 || pos.getLig() >= height)
    		return false;
    	return true;
    }
    
    public boolean free(Coordinate pos){
    	if (valid(pos) == true)
    		return grille[pos.getLig()][pos.getCol()];
    	return false;
    }
    
    public boolean achieved(){ //autre methode avec le nb de pieces placees
		for (int i=0 ; i<height ; i++)
			for (int n=0; n<width ; n++)
				if (grille[i][n] == true)
					return false;
		return true;
    }
    
    public boolean canPut(Pentomino id, Coordinate pos){
    	//trouver le décalage (x, y)
    	int lin,col;
    	lin = pos.getLig() - (id.shapeOf().get(0)).getLig();
       	col = pos.getCol() - (id.shapeOf().get(0)).getCol();

    	//generer coord_grille avec le decalage a partir de coord
    	for (Coordinate c : id.shapeOf())
    		if (free(new Coordinate(c.getLig() + lin, c.getCol() + col)) == false)
    			return false;
    	return true;
    }
    
    public void put(Pentomino id, Coordinate pos) //mettre toutes les cases occupées a false
    {
    	if (canPut(id, pos) == false)
    		return;

    	ArrayList<Coordinate> coord_grille = new ArrayList<Coordinate>();
 
    	//trouver le décalage (x, y)
    	int lin,col;
    	lin = pos.getLig() - (id.shapeOf().get(0)).getLig();
    	col = pos.getCol() - (id.shapeOf().get(0)).getCol();

    	//generer coord_grille avec le decalage a partir de coord
    	for (Coordinate c : id.shapeOf())
    		coord_grille.add(new Coordinate(c.getLig() + lin, c.getCol() + col));
    	
    	for (Coordinate c : coord_grille)
       		grille[c.getLig()][c.getCol()] = false;
    	Pair<Pentomino,ArrayList<Coordinate>> paire = new Pair<Pentomino,ArrayList<Coordinate>>(id, coord_grille);
    	pento.add(paire);
    }

    public boolean isPlaced(Pentomino id){
    	for (Pair<Pentomino,ArrayList<Coordinate>> paire : pento)
    		if (paire.fst() == id)
    			return true;	    	
    	return false;
    }
    
    public void remove(Pentomino id)
    {
    	for (Pair<Pentomino,ArrayList<Coordinate>> paire : pento)
    		if (paire.fst() == id)
    		{	
    	    	for (Coordinate c : paire.snd())
    	       		grille[c.getLig()][c.getCol()] = true;
    			pento.remove(paire);
    			return;
    		}
    }

    public Pentomino get(Coordinate pos) throws NotFound {
        if (free(pos) == true)
    		throw new NotFound();
    	Pentomino id = Pentomino.Ia; //initialisation sinon compilo pas content
    	for (Pair<Pentomino,ArrayList<Coordinate>> paire : pento)
    	{
    		for(Coordinate coord : paire.snd())
    			if ((coord.getCol() == pos.getCol()) && (coord.getLig() == pos.getLig()))
    				id = paire.fst();
    	}
    	return id;
    }
	
    public ArrayList<Pair<Pentomino,ArrayList<Coordinate>>>placedPentominos(){
    	return pento;
    }
    
	public String toString(){
		String str = " ";
		for (int i=0 ; i<height ; i++)
			str+="_";
		str+="\n";
		for (int i=0 ; i<height ; i++)
		{
			str += "|";
			for (int n=0; n<width ; n++)
				if (grille[i][n] == true)
					str += ".";
				else
					str += "*";
			str += "|\n";
		}
		str += "|";
		for (int i=0 ; i<height ; i++)
			str+="_";
		str += "|";
		return str;
	}
	
	// ----------------------------------------- main de test
	public static void main(String []av){
		// ----------------------- Test class Parser
		Parser parser = new Parser();
		
		ArrayList<Pair<Model,ArrayList<Pentomino>>> parties;
		
		parties = parser.generePartie("/users/nfs/Etu4/3602844/workspace/pentodroid/src/android/pentodroid/model/puzzles.xml");
		if (parties == null)
		{
			System.out.println("parsing failure");
		}
		else
		{
			System.out.println("parsing ok");
			for (Pair<Model,ArrayList<Pentomino>> game : parties)
			{
				Model m1 = game.fst();
				ArrayList<Pentomino> pieces1 = game.snd();
				System.out.println("height : " + m1.height() + " width : " + m1.width());
				for (Pentomino p : pieces1)
					System.out.println("Piece : " + p.name());
			}
		}
		// ----------------------- test fin de partie

		Model fin = new Model(5,3);
				
		System.out.println("Test pentodroid pas fini : " +fin.achieved());//false
		fin.put(Pentomino.Ub, new Coordinate(0,0));
		fin.put(Pentomino.P2b, new Coordinate(0,2));
		fin.put(Pentomino.Vd, new Coordinate(0,4));
		System.out.println("Test pentodroid fini : " + fin.achieved());//true

		// ----------------------- Test methods
		
		Model mod = new Model(10,10);
		
		System.out.println(mod.width());
		System.out.println(mod.height());
		
		System.out.println(mod.free(new Coordinate(0,1))); //true
		System.out.println(mod.free(new Coordinate(0,6))); //true
		System.out.println(mod.free(new Coordinate(-1,1))); //false
		
		System.out.println(mod);
		
		mod.put(Pentomino.Ib, new Coordinate(4,0));
		mod.put(Pentomino.Wd, new Coordinate(6,6));
		
		System.out.println(mod.isPlaced(Pentomino.Ia)); //false
		System.out.println(mod.isPlaced(Pentomino.Ib)); //true
		
		System.out.println(mod);
	
		mod.remove(Pentomino.Ib);
		System.out.println(mod.isPlaced(Pentomino.Ib)); //false
		
		System.out.println(mod);
		
		try{
			System.out.println(mod.get(new Coordinate(6,6))); //Wd
			System.out.println(mod.get(new Coordinate(0,0))); //throw exception
		}
		catch (NotFound e)
		{
			System.out.println("Exception not found catch");
		}
	}
}
