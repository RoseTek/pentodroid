package android.pentodroid.model;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.ArrayList;


public class Parser {
	public Parser(){}
	
	public ArrayList<Pair<Model,ArrayList<Pentomino>>> generePartie(String file)
	{
		InputStream is;
		//ouvrir le fichier en InputStream
		try {
		is = new FileInputStream(file);
		} catch (Exception e) {
			System.out.println("File couldn't be open");
			return null;
		}
		
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
		
		ArrayList<Pair<Model,ArrayList<Pentomino>>> tab = null;
		for (int i = 0; i<listeNoeud.getLength() ; i++)
		{
			Node noeud = listeNoeud.item(i);
			//recupere la liste de couples attribut - valeur
			NamedNodeMap attrs = noeud.getAttributes();
			//recupere les attributs width et height dans attrs			
			int width = Integer.parseInt(attrs.getNamedItem("width").getNodeValue());
			int height = Integer.parseInt(attrs.getNamedItem("height").getNodeValue());
			//genere Model
			//Model m = new Model(width, height);
			System.out.println("width " + width + "height " + height);
			
			//recupere la liste de nodes qui contient les pentominos
			NodeList pento = noeud.getChildNodes();
			//pour chaque node
			for (int n = 0; n<pento.getLength() ; n++)
			{
				//SI c'est un pentomino valide => ajoute le dans un arraylist de pentomino
				Node sousNoeud = pento.item(n);
				//si sousNoeud a pour nom pento
				if (sousNoeud.getNodeName().equals("pento"))
					System.out.println("Pentomino " + sousNoeud.getNodeValue());//a travailler avec un getAttribute
				//add un pentomino du bon nom dans l'arrayList
					
			}
			//ajoute la paire dans l'arrayList
		}
		return tab;
	}
}
