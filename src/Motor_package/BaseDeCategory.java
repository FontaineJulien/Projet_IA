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

public class BaseDeCategory extends HashSet<String> {
	private static final long serialVersionUID = -6680859139142191003L;
	
    private static final String NODEATTRIBUTE_LES_CATEGORIES   = "categories";
	
	
	/**
	 * Constructeur
	 * @param categories
	 */
	public BaseDeCategory(String... categories){
		for(String cat : categories)
			if(cat.trim().length()!=0)
				this.add(cat.trim());
	}
	
	
	
	public boolean read(String file){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
     // lecture du fichier
        try {

            DocumentBuilder db = dbf.newDocumentBuilder(); // on créé un
                                                           // document
            Document document = db.parse( new File( file ) ); // on lit le
                                                              // fichier de
                                                              // sauvegarde
            

            Element root = document.getDocumentElement();
            NodeList root_categories = root.getElementsByTagName( NODEATTRIBUTE_LES_CATEGORIES );
            
            NodeList categories = ( (Element) root_categories.item( 0 ) ).getChildNodes();
            
            for ( int i = 0; i < categories.getLength(); i++ ) {
            	if ( categories.item( i ).getNodeType() == Node.ELEMENT_NODE ) {
            		this.add( ( (Element) categories.item( i ) ).getTextContent().trim() );
            		System.out.println(( (Element) categories.item( i ) ).getTextContent());
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
	
	
	public boolean add(String cat){
		if(!this.contains(cat))
			return super.add(cat);
		else
			{System.out.println("deja");return false;}
	}
	
	
	
	public void display(){
		Iterator<String> it = this.iterator();
		if(it.hasNext()){
			System.out.print(it.next().replace('_',' '));
		}
		while(it.hasNext()){
			System.out.print(","+it.next().replace('_',' '));
		}
		System.out.println();
	}
	
	public List<String> toListString(){
		List<String> liste = new ArrayList<String>();
		Iterator<String> it = this.iterator();
		while(it.hasNext()){
			liste.add(it.next().replace('_', ' '));
		}
		return liste;
	}

}
