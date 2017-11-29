package Motor_package;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/*
 * Base de règles :
 * 
 * Permet la lecture d'un fichier de règles et le stockage de ses données
 * 
 */

public class BaseDeRegles extends HashMap<Integer,Rule> {
	
	private static final long serialVersionUID = 1L;
	private static final String NODENAME_RULES = "regles";
	private static final String NODENAME_ANTECEDENT = "antecedent";
	private static final String NODENAME_CONSEQUENCE = "consequence";
	private static final String NODEATTRIBUTE_VALUE = "value";
	private static final String NODEATTRIBUTE_VALUE_TRUE = "true";
	
	public BaseDeRegles(){
		super();
	}
	
	
	
	public Demandables read(String file){
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		boolean current_valuation = false;
		int current_rule_number = 0;
		String current_categorie;
		
		Demandables demandables = new Demandables();
		
		Fait current_fait;
		
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File(file));
			
			Element root = document.getDocumentElement();
			NodeList root_regles = root.getElementsByTagName(NODENAME_RULES);
			
			
			NodeList categories = ((Element)root_regles.item(0)).getChildNodes();
			int nbCategories = categories.getLength();
			
			for(int i=0; i<nbCategories; i++){
				if(categories.item(i).getNodeType() == Node.ELEMENT_NODE){
					Element categorie = (Element) categories.item(i);
					current_categorie = categorie.getAttribute(NODEATTRIBUTE_VALUE);
					
					NodeList reglesCategorie = categorie.getChildNodes();
					int nbReglesCategorie = reglesCategorie.getLength();
					for(int j=0; j<nbReglesCategorie; j++){
						if(reglesCategorie.item(j).getNodeType() == Node.ELEMENT_NODE){
							Element regle = (Element) reglesCategorie.item(j);
							
							ArrayList<Fait> current_antecedents = new ArrayList<Fait>();
							ArrayList<Fait> current_consequences = new ArrayList<Fait>();
							
							NodeList termes = regle.getChildNodes(); // Note : Un terme corresponds aux antecedents OU aux conclusions de la règle
							int nbTermes = termes.getLength();
							for(int k = 0; k<nbTermes; k++){
								if(termes.item(k).getNodeType() == Node.ELEMENT_NODE){
									Element terme = (Element) termes.item(k);
									
									NodeList termeElements = terme.getChildNodes();
									int nbTermeElements = termeElements.getLength();
									for(int m = 0; m<nbTermeElements; m++){
										if(termeElements.item(m).getNodeType() == Node.ELEMENT_NODE){
											Element termeElement = (Element) termeElements.item(m);
											
											if(termeElement.getAttribute("value").toLowerCase().equals(NODEATTRIBUTE_VALUE_TRUE)){
												current_valuation = true;
											} else {
												current_valuation = false;
											}
											
											current_fait = new Fait(termeElement.getTextContent(), current_valuation);
											demandables.add(current_fait);
											
											if(termeElement.getNodeName().equals(NODENAME_ANTECEDENT)){
												current_antecedents.add(current_fait);
											}
											else if(termeElement.getNodeName().equals(NODENAME_CONSEQUENCE))
												current_consequences.add(current_fait);
										}
									}
								}	
							}
							current_antecedents.trimToSize();
							current_consequences.trimToSize();
							this.put(current_rule_number, new Rule(current_antecedents,current_consequences, current_categorie));
							current_rule_number++;
						}
					}
				}
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return demandables;

	}
	
	
	/**
	 * Ajoute une nouvelle Règle.
	 * @param current_antecedents -> Liste des Antecedent
	 * @param current_consequences -> Liste des Consequences
	 * @warning PENSEZ à RAJOUTER les catégories
	 */
	public Rule AddRule(ArrayList<Fait> current_antecedents,ArrayList<Fait> current_consequences,ArrayList<String> Categorie){
		Rule rule = new Rule(current_antecedents,current_consequences, "");
		this.put(this.size(), rule );
		return rule;
	}
	
	
	/*
	 * Affiche les règles
	 */
	public void display(){
		Iterator<Entry<Integer, Rule>> it = this.iterator();
		int rule_number;
		
		Entry<Integer, Rule> rule;
		
		while(it.hasNext()){
			rule = it.next();
			rule_number = rule.getKey()+1;
			System.out.println("Règle n°"+rule_number+" : "+rule.getValue().toString().replace('_', ' '));
		}
	}
	
	public int getNumberOfRules(){
		return this.size();
	}
	
	public Iterator<Entry<Integer, Rule>> iterator(){
		Set<Entry<Integer, Rule>> set = this.entrySet();
		return set.iterator();
	}
	
	/**
	 * Obtenir une arraylist de toutes les règles.
	 * @return
	 */
	public ArrayList<Rule> getAll(){
		ArrayList<Rule> liste = new ArrayList<Rule>();
		Iterator<Entry<Integer, Rule>> it = this.iterator();
		Entry<Integer, Rule> rule;
		while(it.hasNext()){
			rule = it.next();
			liste.add(rule.getValue());
		}
		return liste;
	}
	
	
	/**
	 * Obtenir une arraylist de toutes les règles sous forme d'une String
	 * @return
	 */
	public ArrayList<String> getRegles_ToListString(){
		ArrayList<String> liste = new ArrayList<String>();
		Iterator<Entry<Integer, Rule>> it = this.iterator();
		Entry<Integer, Rule> rule;
		while(it.hasNext()){
			rule = it.next();
			liste.add(rule.getValue().toString());
		}
		return liste;
	}

	
}
