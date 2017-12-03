package Motor_package;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*
 * Base de règles :
 * 
 * Permet la lecture d'un fichier de règles et le stockage de ses données
 * 
 */

public class BaseDeRegles extends HashMap<Integer, Rule> {

    private static final long   serialVersionUID         = 1L;
    private static final String NODENAME_RULES           = "regles";
    private static final String NODENAME_ANTECEDENT      = "antecedent";
    private static final String NODENAME_CONSEQUENCE     = "consequence";
    private static final String NODEATTRIBUTE_VALUE      = "value";
    private static final String NODEATTRIBUTE_VALUE_TRUE = "true";

    public BaseDeRegles() {
        super();
    }

    public Demandables read( String file ) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        boolean current_valuation = false;
        int current_rule_number = 0;
        

        Demandables demandables = new Demandables();

        Fait current_fait;

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse( new File( file ) );

            Element root = document.getDocumentElement();
            NodeList root_regles = root.getElementsByTagName( NODENAME_RULES );

            NodeList categories = ( (Element) root_regles.item( 0 ) ).getChildNodes();
            int nbCategories = categories.getLength();

            for ( int i = 0; i < nbCategories; i++ ) {
                if ( categories.item( i ).getNodeType() == Node.ELEMENT_NODE ) {
                    Element categorie = (Element) categories.item( i );
                    Categorie current_Categorie = new Categorie(categorie.getAttribute( NODEATTRIBUTE_VALUE ));

                    NodeList reglesCategorie = categorie.getChildNodes();
                    int nbReglesCategorie = reglesCategorie.getLength();
                    for ( int j = 0; j < nbReglesCategorie; j++ ) {
                        if ( reglesCategorie.item( j ).getNodeType() == Node.ELEMENT_NODE ) {
                            Element regle = (Element) reglesCategorie.item( j );

                            ArrayList<Fait> current_antecedents = new ArrayList<Fait>();
                            ArrayList<Fait> current_consequences = new ArrayList<Fait>();

                            NodeList termes = regle.getChildNodes(); // Note :
                                                                     // Un terme
                                                                     // corresponds
                                                                     // aux
                                                                     // antecedents
                                                                     // OU aux
                                                                     // conclusions
                                                                     // de la
                                                                     // règle
                            int nbTermes = termes.getLength();
                            for ( int k = 0; k < nbTermes; k++ ) {
                                if ( termes.item( k ).getNodeType() == Node.ELEMENT_NODE ) {
                                    Element terme = (Element) termes.item( k );

                                    NodeList termeElements = terme.getChildNodes();
                                    int nbTermeElements = termeElements.getLength();
                                    for ( int m = 0; m < nbTermeElements; m++ ) {
                                        if ( termeElements.item( m ).getNodeType() == Node.ELEMENT_NODE ) {
                                            Element termeElement = (Element) termeElements.item( m );

                                            if ( termeElement.getAttribute( "value" ).toLowerCase()
                                                    .equals( NODEATTRIBUTE_VALUE_TRUE ) ) {
                                                current_valuation = true;
                                            } else {
                                                current_valuation = false;
                                            }

                                            current_fait = new Fait( termeElement.getTextContent(), current_valuation );
                                            demandables.add( current_fait );

                                            if ( termeElement.getNodeName().equals( NODENAME_ANTECEDENT ) ) {
                                                current_antecedents.add( current_fait );
                                            } else if ( termeElement.getNodeName().equals( NODENAME_CONSEQUENCE ) )
                                                current_consequences.add( current_fait );
                                        }
                                    }
                                }
                            }
                            current_antecedents.trimToSize();
                            current_consequences.trimToSize();
                            this.put( current_rule_number,
                                    new Rule( current_antecedents, current_consequences, current_Categorie ) );
                            current_rule_number++;
                        }
                    }
                }
            }

        } catch ( ParserConfigurationException e ) {
            e.printStackTrace();
        } catch ( SAXException e ) {
            e.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        return demandables;

    }

    /**
     * Ajoute une nouvelle Règle.
     * 
     * @param current_antecedents
     *            -> Liste des Antecedent
     * @param current_consequences
     *            -> Liste des Consequences
     * @warning PENSEZ à RAJOUTER les catégories
     */
    public Rule AddRule( ArrayList<Fait> current_antecedents, ArrayList<Fait> current_consequences, Categorie categorie) {
        Rule rule = new Rule( current_antecedents, current_consequences, categorie );
        if(! this.contains(rule))
        	{
        	this.put( this.size(), rule );
        	return rule;
        	}
        else{
        	return null;
        	}
    }
    
    /**
     * Test si la regle 'rule' appartient déjà à la base de règle.
     * Une regle appartient à la base si pour une regle de la base :
     * 	-elle a le même nombre d'antécédents et de conséquence	ET
     *  -elle a les même antécédents peut importe l'ordre		ET
     *  -elle a les mêmes conséquences peut importe l'ordre		ET
     *  -elle a la même catégorie.
     *  
     *  Dans tous les autres cas, la règle n'appartient pas à la base.
     * 	
     * @param rule
     * @return TRUE si la règle apparient à la base
     */
    public boolean contains(Rule rule){
        Iterator<Entry<Integer, Rule>> it = this.iterator();		//l'iterator su la base
        List<Fait> antes = rule.getAntecedents();					//les antecedents de la regle à tester
        List<Fait> cons = rule.getConsequences();					//les consequences de la règle à tester
        Categorie cat = rule.getCategorie();						//la catégorie de la règle à tester
        
        List<Fait> curr_antes = null;								//les antecedents de la regle courante
        List<Fait> curr_cons = null;								//les consequence de la regle courante
        Categorie curr_cat =null; 									//la catégorie de la règle courante
                
        Entry<Integer, Rule> rule_curr;								//la règle courante dans la base
        
        while ( it.hasNext() ) {									//POUR chaque REGLE
        	rule_curr = it.next();
            curr_antes = rule_curr.getValue().getAntecedents();
            curr_cons = rule_curr.getValue().getConsequences();
            curr_cat = rule_curr.getValue().getCategorie();
            
            if((curr_antes.size()==antes.size()) && (curr_cons.size()==cons.size()))	//si il y a pas autant d'antécédants et/ou de conséquences
            {																			//alors on teste l'égalité des règles
            	boolean antes_egaux = true;
            	for(int i=0;i<antes.size();i++){										//pour chaque antecedents de la règle à tester.
            		boolean ante_present = false;
            		for(int j=0; j<curr_antes.size();j++)								//On vérifie qu'il appartient à la liste des antécdents 
            			if(curr_antes.get(i).equalsToFact(antes.get(i)))//de la règle courante de la base
            				ante_present=true;
            		if(!ante_present)													//si l'antécédents de la règle à tester n'appartient pas
            			antes_egaux=false;												//à la liste des antécédents de la règles courante de la base.
            																			//alors les antécédents des deux règles ne sont pas les mêmes.
            	}//fin test sur antécédents
            	
            	boolean cons_egales=true;
            	for(int i=0;i<cons.size();i++){											//pour chaque conséquence de la règle à tester.
            		boolean cons_present = false;
            		for(int j=0; j<curr_cons.size();j++)								//On vérifie qu'il appartient à la liste des conséquences 
            			if(curr_cons.get(i).equalsToFact(cons.get(i)))//de la règle courante de la base
            				cons_present=true;
            		if(!cons_present)													//si conséquence de la règle à tester n'appartient pas
            			cons_egales= false;												//à la liste des conséquence de la règles courante de la base.
            																			//alors les consequences des deux règles ne sont pas les mêmes.
            	}//fin test sur conséquences
            	
            	if(cons_egales&&antes_egaux)											//si les listes des antecedents et des consequences sont égales
            		if(curr_cat.getCategorie().equals(cat.getCategorie()))				//et que les catégories sont les mêmes
            			return true;													//alors il s'agit de la même règle
            }
        }
        return false;																	//si on arrive là, c'est que la règle à tester n'appartient pas à la base de règles.
    }

    /**
     * Affiche les règles
     */
    public void display() {
        Iterator<Entry<Integer, Rule>> it = this.iterator();
        int rule_number;

        Entry<Integer, Rule> rule;

        while ( it.hasNext() ) {
            rule = it.next();
            rule_number = rule.getKey() + 1;
            System.out.println( "Règle n°" + rule_number + " : " + rule.getValue().toString().replace( '_', ' ' ) );
        }
    }

    public int getNumberOfRules() {
        return this.size();
    }

    public Iterator<Entry<Integer, Rule>> iterator() {
        Set<Entry<Integer, Rule>> set = this.entrySet();
        return set.iterator();
    }

    /**
     * Obtenir une arraylist de toutes les règles.
     * 
     * @return
     */
    public ArrayList<Rule> getAll() {
        ArrayList<Rule> liste = new ArrayList<Rule>();
        Iterator<Entry<Integer, Rule>> it = this.iterator();
        Entry<Integer, Rule> rule;
        while ( it.hasNext() ) {
            rule = it.next();
            liste.add( rule.getValue() );
        }
        return liste;
    }

    /**
     * Obtenir une arraylist de toutes les règles sous forme d'une String
     * 
     * @return
     */
    public ArrayList<String> toListString() {
        ArrayList<String> liste = new ArrayList<String>();
        Iterator<Entry<Integer, Rule>> it = this.iterator();
        Entry<Integer, Rule> rule;
        while ( it.hasNext() ) {
            rule = it.next();
            liste.add( rule.getValue().toString() );
        }
        return liste;
    }

}
