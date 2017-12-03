package Motor_package;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class BaseDeCategory extends HashSet<Categorie> {
	private static final long serialVersionUID = -6680859139142191003L;

    private static final String NODENAME_RULES           		= "regles";
    private static final String NODEATTRIBUTE_VALUE      		= "value";
	
	
	/**
	 * Constructeur
	 * @param categories
	 */
	public BaseDeCategory(String... categories){
		for(String cat : categories)
			if(cat.trim().length()!=0)
				this.add(new Categorie(cat.trim()));
	}
	
	
	
	public boolean read(String file){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //lecture du fichier
        try {

            DocumentBuilder db = dbf.newDocumentBuilder(); // on créé un
                                                           // document
            Document document = db.parse( new File( file ) ); // on lit le
                                                              // fichier de
                                                              // sauvegarde
            
            Element root = document.getDocumentElement();
            NodeList root_regles = root.getElementsByTagName( NODENAME_RULES );
            
            NodeList categories = ( (Element) root_regles.item( 0 ) ).getChildNodes();
            
            for ( int i = 0; i < categories.getLength(); i++ ) {
            	if ( categories.item( i ).getNodeType() == Node.ELEMENT_NODE ) {
            		Element categorie = (Element) categories.item(i);
            		this.add(new Categorie( categorie.getAttribute( NODEATTRIBUTE_VALUE )));
            	}
            }

        } catch ( ParserConfigurationException e ) {
            e.printStackTrace(); return false;
        } catch ( SAXException e ) {
            e.printStackTrace(); return false;
        } catch ( IOException e ) {
            e.printStackTrace(); return false;
        }
        return true; // on renvoit la liste des nouveaux faits.
	}
	
	
	/**
	 * Renvoyer les catégories sous forme d'une liste
	 * @return
	 */
	protected List<Categorie> getAll(){
		Iterator<Categorie> it = this.iterator();
		List<Categorie> list_cat = new ArrayList<Categorie>();
		while(it.hasNext()){
			list_cat.add(it.next());
		}
		return list_cat;
	}
	
	
	/**
	 * Ajoute une Categorie dans la base de Categorie.
	 * @param cat -> la catégorie à ajouter.
	 */
	public boolean add(Categorie cat){
		if(!this.contains(cat))
			return super.add(cat);
		else
			return false;
	}
	
	
	public boolean contains( Categorie cat ) {
        Iterator<Categorie> it = this.iterator();
        Categorie curr_cat;

        while ( it.hasNext() ) {
            curr_cat = it.next();
            if ( curr_cat.getCategorie().equals(cat.getCategorie()) )
                return true;
        }
        return false;
    }
	/**
	 * Affiche l'intégralité de la base de categorie dans la sortie Standard
	 */
	public void display(){
		Iterator<Categorie> it = this.iterator();
		if(it.hasNext()){
			System.out.print(it.next().getCategorie().replace('_',' '));
		}
		while(it.hasNext()){
			System.out.print(","+it.next().getCategorie().replace('_',' '));
		}
		System.out.println();
	}
	
	
	/**
	 * renvoyer la liste des Categories de la Base de Categorie sous forme d'une List de Chaîne
	 * @return
	 */
	public List<String> toListString(){
		List<String> liste = new ArrayList<String>();
		Iterator<Categorie> it = this.iterator();
		while(it.hasNext()){
			liste.add(it.next().getCategorie().replace('_', ' '));
		}
		return liste;
	}
	
	
	/**
	 * Renvoyer la Catégorie correspondant à la chaîne 'str_categorie' si elle appartiet à la Base de Catégorie.
	 * Renvoyer null sinon.
	 * @param str_categorie
	 * @return
	 */
	protected Categorie getCategorie(String str_categorie){
		Iterator<Categorie> it = this.iterator();
		while(it.hasNext()){
			Categorie elmt = it.next();
			if(elmt.getCategorie().equals(str_categorie))
				return elmt; 
		}
		return null;
	}

}
