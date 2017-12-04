package Motor_package;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
//import java.util.Scanner;



public class Moteur {

    private BaseDeRegles BR;				//LA BASE DE REGLES
    private BaseDeFaits  BF;				//LA BASE DE FAITS
    private BaseDeCategory BC;				//LA BASE DE CATEGORIE
    private Demandables  demandables;		//LES DEMANDABLES
    private History      history;			//L'HISTORIQUE DES INFÉRENCES
    private File         SystemFile;		//le fichier de sauvegarde
	private List<Fait> FactsRequested;		//LA LISTE DES FAITS MANQUANTS (utilisée par le chaînage arrière)

    /**
     * Constructeur.
     */
    public Moteur() {
        this.BF = new BaseDeFaits();
        this.BR = new BaseDeRegles();
        this.BC = new BaseDeCategory();
        this.demandables = new Demandables();
        this.history = new History();
        this.FactsRequested = new ArrayList<Fait>();
    }

    /**
     * Constructeur
     * 
     * @param rules_file
     * @param knowledge
     */
    public Moteur( String rules_file, Fait... knowledge ) {
        this.BF = new BaseDeFaits( knowledge );
        this.BR = new BaseDeRegles();
        this.BC = new BaseDeCategory();
        BC.read(rules_file);
        this.demandables = BR.read( rules_file );
        this.history = new History();
        this.FactsRequested = new ArrayList<Fait>();
    }

    /*
     * Chainage avant profondeur
     */
    public boolean deepForward( List<Categorie> categories, List<Fait> goals ) {
        ArrayList<Boolean> activable = initActivable(); // Permet de savoir si
                                                        // la regle i a deja été
                                                        // activée => Une règle
                                                        // se déclenche au plus
                                                        // une fois
        boolean inf = true; // Permet de savoir si une inférence a été effectuée
        boolean dec; // Permet de savoir si la règle courrante est déclenchable
        boolean goal_reached = false; // Permet de savoir de savoir si le but
                                      // donné à été atteint
        int nb_inf = 1; // Nombre d'inférence effectuées

        Iterator<Entry<Integer, Rule>> it_rule; // Iterateur sur les règles
        Entry<Integer, Rule> rule;

        Iterator<Fait> it_antecedents; // Iterateur sur les antécédents
        int rule_index;
        Fait antecedent;

        Iterator<Fait> it_consequences;
        Fait consequence;

        Iterator<Fait> it_goals = goals.iterator();
        Fait goal;
        
        Iterator<Categorie> it_categories;
        Categorie categ;

        if ( BF.containsAllFaits( goals ) ) {
            return true; 
        }
        
        history.clear();

        while ( inf && !goal_reached ) { // Tant qu'on infère et que le but
                                         // donné n'est pas atteint
            inf = false;
            dec = false;
            it_rule = BR.iterator();

            while ( it_rule.hasNext() && dec == false ) { // Itération sur les
                                                          // règles

                rule = it_rule.next();
                // La règle a-t-elle deja été validée et fait elle partie de la
                // bonne categorie
                if ( activable.get( rule.getKey() ) ) {
                    dec = true;
                    it_antecedents = rule.getValue().iteratorAntecedents();
                    

                    if(categories!= null){						//Y a t-il des catégories à respecter ?
	                    it_categories = categories.iterator();
	                    while(it_categories.hasNext()){
	                    	dec = false;
	                    	categ = it_categories.next();
	                    	if( categ.getCategorie().equals(rule.getValue().getCategorie().getCategorie())){
	                    		dec = true;
	                    	}
	                    }
                    }

                    while ( it_antecedents.hasNext() && dec ) { // Vérification
                        // des
                        // antécédents
                        // de la règle
                        // dans la base
                        // de faits
                        antecedent = it_antecedents.next();
                        if ( !BF.contains( antecedent ) ) // Si l'antécédent n'appartient pas à la base de faits
                            dec = false; // La règle n'est pas déclenchable
                    }

                    if ( dec ) { // Si la règle est déclenchable

                        it_consequences = rule.getValue().iteratorConsequences();
                        while ( it_consequences.hasNext() ) {
                            consequence = it_consequences.next();
                            BF.add( consequence ); // On ajoute la conséquence
                                                   // de la règles dans la base
                                                   // de faits
                        }

                        rule_index = rule.getKey();
                        activable.set( rule_index, false ); // On bloque
                                                            // l'activation de
                                                            // cette règle dans
                                                            // les (éventuelles)
                                                            // prochaines
                                                            // inférences
                        inf = true;
                        history.add( new Inference( nb_inf, rule.getValue() ) ); // On
                                                                                 // ajoute
                                                                                 // la
                                                                                 // règle
                                                                                 // dans
                                                                                 // l'historique
                                                                                 // des
                                                                                 // inférences

                        it_goals = goals.iterator();
                        goal_reached = true;
                        while ( goal_reached && it_goals.hasNext() ) {
                            goal = it_goals.next();
                            if ( !BF.contains( goal ) )
                                goal_reached = false;
                        }
                    }
                }
            }

            nb_inf++;
        }

        return goal_reached;
    }

    /*
     * Chaînage avant largeur
     */

    public boolean wideForward( List<Categorie> categories, List<Fait> goals ) {
        ArrayList<Boolean> activable = initActivable(); // Permet de savoir si
                                                        // la regle i a deja été
                                                        // activée => Une règle
                                                        // se déclenche au plus
                                                        // une fois
        boolean inf = true; // Permet de savoir si une inférence a été effectuée
        boolean dec; // Permet de savoir si la règle courrante est déclenchable
        boolean goal_reached = false; // Permet de savoir de savoir si le but
                                      // donné à été atteint
        int nb_inf = 1; // Nombre d'inférence effectuées

        Iterator<Entry<Integer, Rule>> it_rule; // Iterateur sur les règles
        Entry<Integer, Rule> rule;

        Iterator<Fait> it_antecedents; // Iterateur sur les antécédents
        int rule_index;
        Fait antecedent;

        Iterator<Fait> it_consequences;
        Fait consequence;

        Iterator<Fait> it_goals;
        Fait goal;
        
        Iterator<Categorie> it_categories;
        Categorie categ;

        if ( BF.containsAllFaits( goals ) ) {
            return true;
        }

        history.clear();

        while ( inf && !goal_reached ) { // Tant qu'on infère et que le but
                                         // donné n'est pas atteint

            inf = false;
            it_rule = BR.iterator();

            while ( it_rule.hasNext() ) { // Itération sur les règles

                rule = it_rule.next();

                // La règles a-t-elle dejà été validée ? Ou fait-elle partie de
                // la bonne categorie ?
                if ( activable.get( rule.getKey() ) ) {
                    dec = true;
                    it_antecedents = rule.getValue().iteratorAntecedents();
                    
                    if(categories!= null){						//Y a t-il des catégories à respecter ?
	                    it_categories = categories.iterator();
	                    while(it_categories.hasNext()){
	                    	dec = false;
	                    	categ = it_categories.next();
	                    	if( categ.getCategorie().equals(rule.getValue().getCategorie().getCategorie())){
	                    		dec = true;
	                    	}
	                    }
                    }

                    while ( it_antecedents.hasNext() && dec ) { // Vérification
                                                                // des
                                                                // antécédents
                                                                // de la règle
                                                                // dans la base
                                                                // de faits
                        antecedent = it_antecedents.next();
                        if ( !BF.contains( antecedent ) ) // Si l'antécédent
                                                          // n'appartient pas à
                                                          // la base de faits
                            dec = false; // La règle n'est pas déclenchable
                    }

                    if ( dec ) { // Si la règle est déclenchable
                        it_consequences = rule.getValue().iteratorConsequences();
                        while ( it_consequences.hasNext() ) {
                            consequence = it_consequences.next();
                            BF.add( consequence ); // On ajoute la conséquence
                                                   // de la règles dans la base
                                                   // de faits
                        }

                        rule_index = rule.getKey();
                        activable.set( rule_index, false ); // On bloque
                                                            // l'activation de
                                                            // cette règle dans
                                                            // les (éventuelles)
                                                            // prochaines
                                                            // inférences
                        inf = true;
                        history.add( new Inference( nb_inf, rule.getValue() ) ); // On
                                                                                 // ajoute
                                                                                 // la
                                                                                 // règle
                                                                                 // dans
                                                                                 // l'historique
                                                                                 // des
                                                                                 // inférences

                        it_goals = goals.iterator();
                        goal_reached = true;
                        while ( goal_reached && it_goals.hasNext() ) {
                            goal = it_goals.next();
                            if ( !BF.contains( goal ) )
                                goal_reached = false;
                        }
                    }
                }
            }

            nb_inf++;
        }

        return goal_reached;
    }

    /*
     * Initialisation des règles activables à true (i.e. savoir si la règle a
     * deja été activée ou non)
     */
    private ArrayList<Boolean> initActivable() {
        int number_of_rules = BR.getNumberOfRules();
        ArrayList<Boolean> activable = new ArrayList<Boolean>( number_of_rules );

        for ( int i = 0; i < number_of_rules; i++ )
            activable.add( true );

        return activable;
    }

    /*
     * Chaînage arrière
     */
    public boolean backward( Fait... goals ) {
        history.clear();
        List<Fait> goals_list = Arrays.asList( goals );
        return runBackward( goals_list );
    }

    public boolean backward( List<Fait> goals ) {
        history.clear();
        return runBackward( goals );
    }

    private boolean runBackward( List<Fait> goals ) {
        boolean verif = true;

        Iterator<Fait> it_goals = goals.iterator();
        Fait goal;

        while ( it_goals.hasNext() && verif ) {
            goal = it_goals.next();
            verif = verification( goal );
        }

        return verif;
    }



    private boolean verification( Fait goal ) {
        boolean dem = false;

        // 1er cas : goal appartient dejà à la base de fait
        if ( BF.contains( goal ) ) {
            return true;
        }

        // 2em cas : rechercher si goal déductible à partir de BR U BF
        Iterator<Entry<Integer, Rule>> it_rule = BR.iterator(); // Iterateur sur
                                                                // les règles
        Entry<Integer, Rule> rule;

        Iterator<Fait> it_consequences;
        Fait consequence;

        while ( it_rule.hasNext() && dem == false ) {
            rule = it_rule.next();
            it_consequences = rule.getValue().iteratorConsequences();

            while ( it_consequences.hasNext() && dem == false ) {
                consequence = it_consequences.next();
                if ( consequence.equals( goal ) ) {
                    dem = runBackward( rule.getValue().getAntecedents() );
                }
            }
        }

        // 3em cas : Ajouter goal à la liste de faits Requis pour atteindre le but
        if ( dem == false && demandables.contains( goal ) ) {
            {
            	this.FactsRequested.add(goal);
            	return false;
            }
        }

        if ( dem ) {
            BF.add( goal );
        }

        return dem;
    }
    
    /* PREVIOUS METHOD
     *  
    private boolean ask( Fait goal ) {
        boolean keep_asking = true;
        String answer;
        boolean bool_answer = true;
        String goal_string;
        if ( goal.getValuation() )
            goal_string = goal.getLabel();
        else
            goal_string = "!" + goal.getLabel();

        @SuppressWarnings( "resource" )
        //
        // Note : Fermer le Scanner ferme aussi son flux entrant (ie System.in)
        // ce qui lève un NoSuchElementException si on redemande un entrant sur
        // ce flux qui n'existe plus.
        // 
        // => On ne ferme pas le scanner pour assurer le bon fonctionnement du
        // processus
        // 
        //
        Scanner scan = new Scanner( System.in );

        System.out
                .println( "Voulez vous faire entrer le but \"" + goal_string + "\" dans la base de faits ? (Oui/Non)" );

        while ( keep_asking ) {
            answer = scan.next();
            if ( answer.toLowerCase().equals( "oui" ) || answer.toLowerCase().startsWith( "o" ) ) {
                keep_asking = false;
                bool_answer = true;
            } else {
                if ( answer.toLowerCase().equals( "non" ) || answer.toLowerCase().startsWith( "n" ) ) {
                    keep_asking = false;
                    bool_answer = false;
                } else {
                    System.out.println( "La réponse n'est pas valide. Voulez vous faire entrer le but \"" + goal_string
                            + "\" dans la base de faits ? (Oui/Non)" );
                }
            }

        }

        return bool_answer;
    }
    */

    /******************************************
     * METHODES POUR LE CONTROLLER
     */
    
    /**
     * renvoyer les faits requested. (Nécessaire dans le cas du chaînage arrière lors d'un conflit).
     * @return
     */
    public List<Fait> getRequestedFacts(){
    	return this.FactsRequested;
    }
    
   
    /**
     * Vide la list des faits demandés pour le chaineage arrière
     */
    public void clearRequestedFacts(){
    	this.FactsRequested.clear();
    }
    
    

    /**
     * Ajouter un fait dans la base de Faits. Si la premiere lettre de fait est
     * ! alors le fait ajouté aura la valuation false.
     * 
     * @param fait
     *            -> la chaine du fait à ajouter
     * @return boolean -> true si l'ajout est un succès
     */
    public boolean AddFact( String fait ) {
        try {
            String negation = fait.substring( 0, 1 );
            boolean value = true;
            if ( negation.equals( "!" ) ) {
                value = false;
                return this.BF.add( new Fait( fait.substring( 1, fait.length() ), value ) );
            } else
                return this.BF.add( new Fait( fait, value ) );
        } catch ( StringIndexOutOfBoundsException e ) {
            return false;
        } catch ( Exception e ) {
            return false;
        }
    }
    
    /**
     * Ajouter un catégorie dans la base de catégorie.
     * @param category -> la chaine de la catégorie à ajouter
     * @return boolean -> true si l'ajout est un succès
     */
    public boolean AddCategory( String category ) {
        try {
        	return this.BC.add(new Categorie(category));
        } catch ( StringIndexOutOfBoundsException e ) {
            return false;
        } catch ( Exception e ) {
            return false;
        }
    }
    
    

    /**
     * Ajouter une nouvelle Règle dans la base de Règles.
     * 
     * @param current_antecedents
     *            -> liste de String des antécédents
     * @param current_consequences
     *            -> liste de String des conséquences
     * @param Categorie
     *            -> liste de String des catégories.
     * @return boolean -> true si succès.
     */
    public Rule AddRule( String[] antecedents, String[] consequences, Categorie categorie ) {
        try {
            ArrayList<Fait> liste_ant = new ArrayList<Fait>();
            ArrayList<Fait> liste_cons = new ArrayList<Fait>();

            for ( int i = 0; i < antecedents.length; i++ )
                liste_ant.add( Fait.String_toFact( antecedents[i].trim() ) );
            for ( int i = 0; i < consequences.length; i++ )
                liste_cons.add( Fait.String_toFact( consequences[i].trim() ) );
            Rule added_rule = this.BR.AddRule( liste_ant, liste_cons, categorie );
            if ( added_rule != null ) {
                for ( int i = 0; i < liste_ant.size(); i++ )
                    this.demandables.add( liste_ant.get( i ) );
                for ( int i = 0; i < liste_cons.size(); i++ )
                    this.demandables.add( liste_cons.get( i ) );
                
                return added_rule;
            } else
                return null;
        } catch ( Exception e ) {
            return null;
        }
    }

    /**
     * Efface l'historique
     */
    public void clearHistory() {
        this.history = new History();
    }

    /**
     * Revoyer la ArrayList de toutes les règles de la base de règles.
     * 
     * @return
     */
    public ArrayList<String> getAllRules_StringFormat() {
        return this.BR.toListString();
    }

    /**
     * Renvoyer la liste des faits de la base de faits sous forme d'une liste de
     * string
     * 
     * @return
     */
    public List<String> getAllFacts_StringFormat() {
        return this.BF.toListString();
    }

    /**
     * Renvoyer la liste des actégories de la base de catgories sous forme d'une liste de
     * string
     * 
     * @return
     */
    public List<String> getAllCategories_StringFormat(){
    	return this.BC.toListString();
    }
    
    /**
     * Renvoyer la Categorie de la base de Categorie, correspondant à la chaîne str_categorie.
     * @param str_categorie
     * @return
     */
    public Categorie getCategorie(String str_categorie){
    	return this.BC.getCategorie(str_categorie);
    }
    /**
     * renvoyer l'historique sous forme d'une liste de String
     * 
     * @return
     */
    public List<String> getHistory() {
        return this.history.toStringList();
    }

    /**
     * Charge un fichier de sauvegarde de base de règles.
     * 
     * @param file
     * @return
     */
    public boolean LoadSystem( File file ) {
        if ( this.SystemFile == null || !this.SystemFile.exists() )
            this.SystemFile = file;
        if ( this.BR == null )
            this.BR = new BaseDeRegles();
        if ( this.BF == null )
            this.BF = new BaseDeFaits();
        if ( this.BC == null )
        	this.BC = new BaseDeCategory();
        try {
            this.demandables = this.BR.read( SystemFile.getCanonicalPath() );
            List<Fait> tmp = this.BF.read( SystemFile.getCanonicalPath() );
            this.demandables.addAll( tmp );
            this.BC.read(SystemFile.getCanonicalPath());
            return true;
        } catch ( Exception e ) {
            return false;
        }
    }

    /**
     * renoyer la base des demandables, faits, règles, et l'historique.
     * 
     * @return
     */
    public List<String> getAllInfo() {
        List<String> completeDisplay = this.demandables.getDemandables_ToListString();
        completeDisplay.addAll( this.BF.toListString() );
        completeDisplay.addAll( this.BR.toListString() );
        completeDisplay.addAll( this.BC.toListString() );
        completeDisplay.addAll( this.history.toStringList() );

        return completeDisplay;
    }

    /**
     * Renvoyer les demandables sou forme d'une liste de String.
     * 
     * @return
     */
    public List<String> getDemandables_ToListString() {
        return this.demandables.getDemandables_ToListString();
    }

    /**
     * Renvoyer les faits sou forme d'une liste de String.
     * 
     * @return
     */
    public List<String> getFacts_ToListString() {
        return this.BF.toListString();
    }
    

    /**
     * Renvoyer les règles sou forme d'une liste de String.
     * 
     * @return
     */
    public List<String> getRules_ToListString() {
        return this.BR.toListString();
    }
    
    /**
     * Renvoyer les règles sous forme d'une liste de String et triées par catégories.
     * Exemple : 'catégorie1 : SF; rule1; rule2; Rule3 ...'
     * @return
     */
    public List<String> getRules_ToListString_WithCategories(){
    	List<String> list_rule = new ArrayList<String>();
    	for(Categorie cat : this.BC.getAll()){
    		list_rule.add("Catégorie : "+cat.getCategorie());
    		for(Rule rule : this.BR.getAll()){
    			if(rule.getCategorie().getCategorie().equals(cat.getCategorie()))
    				list_rule.add("	"+rule.toString());
    			}
    	}
    	return list_rule;
    }
    
    /**
     * Renvoyer les catégories sous forme d'une liste de String.
     * 
     * @return
     */
    public List<String> getCategorie_ToListString() {
        return this.BC.toListString();
    }
    

    /**
     * Renvoyer les history sou forme d'une liste de String.
     * 
     * @return
     */
    public List<String> getHistory_ToListString() {
        return this.history.toStringList();
    }

    /**
     * Sauvegarde le systeme
     */
    public Integer saveSystem() {
        try {
            Integer res = this.clearSystemFile();
            if ( res == -1 ) {
                return -1;
            }
            if ( res == 0 ) {
                this.writeSystem();
                return 0;
            } else // cas d'erreur
                return 1;
        } catch ( Exception e ) {
            e.printStackTrace();
            return 1;
        }
    }

    private Integer clearSystemFile() {
        if ( this.SystemFile != null && this.SystemFile.exists() ) {
            try {
                this.SystemFile.delete();
                this.SystemFile.createNewFile();
                return 0;
            } catch ( IOException e ) {
                e.printStackTrace();
                return 1;
            }
        } else {
            return -1; // le fichier n'existe pas encore, il faut le créer.
        }
    }

    /**
     * écrit le fichier de sauvegarde du system.
     */
    private void writeSystem() {
        DataOutputStream dos;
        try {
            dos = new DataOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream( this.SystemFile ) ) );

            
            List<Categorie> category_list = this.BC.getAll();									//les catégories
            List<Rule> rule_list = this.BR.getAll();											//les règles
            List<Fait> fact_list = this.BF.getAll();											//les faits
            
            
            // ECRITURE DU FICHIER
            dos.writeBytes( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n" );	// l'entête du fichier
            
            dos.writeBytes( "<System>\n" );															//ELEMENT ROOT
            dos.writeBytes( "	<regles>\n" ); 														//	RÈGLES
	            for(Categorie cat : category_list){													//		CATEGORIE
		            dos.writeBytes( "		<categorie value=\""+cat.getCategorie()+"\">\n" );
		            for ( Rule rule : rule_list) { 													//pour chaque règle
		            	if(rule.getCategorie().getCategorie().equals(cat.getCategorie())){			//si elle appartient à la catégorie			
		                dos.writeBytes( "			<regle>\n" );									//			REGLE
		                
		                	List<Fait> antecedents = rule.getAntecedents();
			                dos.writeBytes( "			<antecedents>\n" );							//				ANTECEDENTS
			                for ( Fait fait : antecedents ) 
			                    dos.writeBytes														//					ANTECEDENT
			                    ("					<antecedent value=\""+fait.getValuation()+"\">" 	
			                    +fait.getLabel()+"</antecedent>\n");
			                dos.writeBytes("				</antecedents>\n");
		                
			                List<Fait> consequences = rule.getConsequences();
			                dos.writeBytes( "				<consequences>\n" );					//				CONSEQUENCES
			                for ( Fait fait : consequences )
			                    dos.writeBytes
			                    ("					<consequence value=\""+fait.getValuation()+"\">"//					CONSEQUENCE
			                    +fait.getLabel()+"</consequence>\n");
			                dos.writeBytes( "				</consequences>\n" );
		                dos.writeBytes( "			</regle>\n" );
		            	}//end if RULE in CATEGORIE
		            }//end for RULES
		            dos.writeBytes( "		</categorie>\n" );
	            }//end for CATEGORIES
	            dos.writeBytes( "	</regles>\n" );
	
	            dos.writeBytes( "	<faits>\n" ); 													//	FAITS
	            for ( Fait fait : fact_list )
	                dos.writeBytes( "		<fait value=\"" + fait.getValuation() + "\">"			//		FAIT
	                        + fait.getLabel()+"</fait>\n" );
	            dos.writeBytes( "	</faits>\n" );
            
            dos.writeBytes( "</System>" );
            dos.close(); // FERMETURE DU FICHIER
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    /**
     * Renvoyer le fichier system du moteur
     */
    public final File getSystemFile() {
        return this.SystemFile;
    }

    public void setSystemFile( File le_file ) {
        if ( this.SystemFile != null )
            return;
        else
            this.SystemFile = le_file;
    }

    /******************************************
     * Methodes d'affichage
     */
    public void displayBaseDeFaits() {
        System.out.println( "Base de faits : " );
        BF.display();
        System.out.println();
    }

    public void displayBaseDeRegles() {
        System.out.println( "Base de règles : " );
        BR.display();
        System.out.println();
    }
    
    public void displayBaseDeCategories() {
        System.out.println( "Base de catégories : " );
        BC.display();
        System.out.println();
    }

    public void displayDemandables() {
        System.out.println( "Demandables : " );
        demandables.display();
        System.out.println();
    }

    public void displayHistory() {
        System.out.println( "Historique des inférences : " );
        history.display();
        System.out.println();
    }

    public void displayAll() {
        displayDemandables();
        displayBaseDeCategories();
        displayBaseDeFaits();       
        displayBaseDeRegles();
        displayHistory();
    }

}
