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

/*
 * 
 * Base de faits :
 * 
 * Permet de stocker les faits que l'on va accumuler au cours des inférences du moteur
 * 
 */

public class BaseDeFaits extends HashSet<Fait> {
	
	private static final long serialVersionUID = 1L;
	private static final String NODEATTRIBUTE_LES_FAITS = "faits";
	private static final String NODEATTRIBUTE_VALUE_FALSE = "false";
	
	public BaseDeFaits(Fait... knowledge){
		super();
		for(Fait elem:knowledge)
			this.add(elem);
	}
	
	
	
	/**
	 * Lecture d'un fichier de sauvegarde pour récupérer la liste des faits.
	 * @param file
	 * @return
	 */
	public List<Fait> read(String file){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();	//le document reader
		boolean current_valuation = true;									//la valuation courrante
		Fait current_fait;													//le fait courrant
		List<Fait> les_nouveaux_faits = new ArrayList<Fait>();				//la liste des nouveaux fait

		//lecture du fichier
		try {
			
			DocumentBuilder db = dbf.newDocumentBuilder();	//on créé un document
			Document document = db.parse(new File(file));	//on lit le fichier de sauvegarde
			
			
			Element root = document.getDocumentElement();
			NodeList root_faits = root.getElementsByTagName(NODEATTRIBUTE_LES_FAITS);	//on récupère l'element root des faits.
			
			
			NodeList faits = ((Element)root_faits.item(0)).getChildNodes();	//on récupère la liste des faits
			for(int i=0;i<faits.getLength();i++){
				//pour chaque fait
				if(faits.item(i).getNodeType() == Node.ELEMENT_NODE){
					if( ((Element)faits.item(i)).getAttribute("value").toLowerCase().equals(NODEATTRIBUTE_VALUE_FALSE))
						current_valuation=false;	//si la valuation est fausse on indique false pour la valuation du nouveau fait.
					else
						current_valuation=true;		//sinon la valuation est vraie, on indique true
					current_fait = new Fait(		//le nouveau Fait construit
							((Element)faits.item(i)).getTextContent(),
							current_valuation);
					this.add(current_fait);
					les_nouveaux_faits.add(current_fait);	//on ajoute le nouveau fait à la liste des demandables.
				}
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return les_nouveaux_faits;		//on renvoit la liste des nouveaux faits.
	}
	
	
	
	
	
	
	
	
	
	public boolean add(Fait f){
		if(!this.contains(f))
			return super.add(f);
		else
			return false;
	}
	
	public boolean contains(Fait f){
		Iterator<Fait> it = this.iterator();
		Fait current_f;
		
		while(it.hasNext()){
			current_f = it.next();
			if(current_f.equals(f))
				return true;
		}
		
		return false;
	}
	
	
	
	
	
	
	/*
	 * Affichage de la base de fait
	 */
	public void display(){
		Iterator<Fait> it = this.iterator();
		Fait f;
		
		if(it.hasNext()){
			f = it.next();
			if(f.getValuation())
				System.out.print(f.getLabel());
			else
				System.out.print("!"+f.getLabel().replace('_', ' '));
		}
		
		while(it.hasNext()){
			f = it.next();
			if(f.getValuation())
				System.out.print(", "+f.getLabel().replace('_', ' '));
			else
				System.out.print(", !"+f.getLabel().replace('_', ' '));
		}
		
		System.out.println();
	}
	
	/**
	 * renvoyer tous les faits de la base sous forme d'une liste de String
	 * @return
	 */
	public List<String> getFaits_ToListString(){
		List<String> liste = new ArrayList<String>();
		Iterator<Fait> it = this.iterator();		
		while(it.hasNext()){
			liste.add(it.next().toString());
		}
		return liste;
	}

	/**
	 * Renvoyer tous les faits sous forme d'une liste de Fait
	 * @return
	 */
	public List<Fait> getAll(){
		List<Fait> liste = new ArrayList<Fait>();
		Iterator<Fait> it = this.iterator();		
		while(it.hasNext()){
			liste.add(it.next());
		}
		return liste;
	}
		
}