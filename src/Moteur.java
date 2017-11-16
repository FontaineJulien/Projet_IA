import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

/*
 * Moteur d'inférence Zero+
 * 
 * Utilisation:
 * 	On lui passe le nom du fichier de règles placé à la racine du projet.
 * 	Ensuite, on lui passe la base de connaissance sur laquelle on veut partir
 * 	pour inférer.
 * 	Ex : Moteur m = new Moteur("regles.txt","Serie","Gore");
 * 		 Moteur m = new Moteur("regles.txt","Film","SF","Bataille","Magie","Chevalier");
 * 
 * 	On choisit la méthode d'inférence que l'on veut :
 * 		- Chaînage avant : m.forward(String goal);
 * 		- Chaînage arrière : m.backward(String goal_1, String goal_2, ... ,goal_N );
 * 
 *  Resultat : true si le but est atteint, false sinon
 * 
 * 	On peut afficher :
 * 		- l'historique des inférences du chaînage avant : m.displayHistory();
 * 		- l'ensemble des données (base de règles, base de faits, demandables, historique) : m.displayAll();
 *      - les données individuellement : m.displayDemandables(); m.displayBaseDeRegles(); m.displayBaseDeFaits(); m.displayHistory();
 * 
 * Note : "m.displayHistory();" n'affichera rien dans le cas d'un chaînage arrière;
 * 
 * Syntaxe du fichier de la base de règle : <antecedent> & <antecedent> & ... & <antecedent> -> <consequence>
 * 
 * Evolutions :
 * 		- Gestion de la valeur des attributs (affirmation ou négation d'un attribut)
 * 		- "Multi-conséquence" càd possibilité d'avoir : <antecedents> -> <consequence> & ... & <consequence>
 * 		- Modulariser les séparateurs dans la syntaxe des règles
 * 		- Apprendre des conclusions finales càd si un chainage fonctionne, premisses initiales -> goal (ajout dans fichier de règles)
 * 
 * TODO : Implémenter l'inférence par paquet de règles
 * TODO : Implémenter les fonctionnalités du moteur (voir sujet du projet)
 * TODO : Documenter et commenter le code
 * TODO : Interface graphique
 * TODO : Cohérence
 * TODO : Chainage avant largeur
 * TODO : Convertire le fichier de règles au format XML
 * 
 */

public class Moteur {
	
	private BaseDeRegles BR;
	private BaseDeFaits BF;
	private Demandables demandables;
	private History history;
	
	public Moteur(String rules_file, String... knowledge){
		this.BF = new BaseDeFaits(knowledge);
		this.BR = new BaseDeRegles();
		this.demandables = new Demandables();
		demandables = BR.read(rules_file);
		history = new History();
	}

	/*
	 * Chainage avant profondeur
	 */
	public boolean forward(String... goals){
		history.clear();
		List<String> goals_list = Arrays.asList(goals);
		goals_list = Util.toLowerCaseList(goals_list);
		return forward(goals_list);
	}
	
	private boolean forward(List<String> goals){
		ArrayList<Boolean> activable = initActivable(); // Permet de savoir si la regle i a deja été activée => Une règle se déclenche au plus une fois
		boolean inf = true; // Permet de savoir si une inférence a été effectuée
		boolean dec = true; // Permet de savoir si la règle courrante est déclenchable
		boolean goal_reached=false; // Permet de savoir de savoir si le but donné à été atteint
		int nb_inf = 0; // Nombre d'inférence effectuées
		
		Iterator<Entry<Integer, Rule>> it_rule; // Iterateur sur les règles
		Entry<Integer, Rule> rule;
		
		Iterator<String> it_antecedents; // Iterateur sur les antécédents
		int rule_index;
		String antecedent;
		String consequence;
		
		Iterator<String> it_goals;
		String goal;
		
		while(inf && !goal_reached){ // Tant qu'on infère et que le but donné n'est pas atteint
			
			inf = false;
			it_rule = BR.iterator();
			
			while(it_rule.hasNext()){ // Itération sur les règles
				
				dec = true;
				rule = it_rule.next();
				
				if(activable.get(rule.getKey())){ // La règles a-t-elle dejà été validée ?
					it_antecedents = rule.getValue().iterator();
					
					while(it_antecedents.hasNext() && dec){ // Vérification des antécédents de la règle dans la  base de faits
						antecedent = it_antecedents.next();
							if(!BF.contains(antecedent)) // Si l'antécédent n'appartient pas à la base de faits
								dec = false; // La règle n'est pas déclenchable
					}
					
					if(dec){ // Si la règle est déclenchable
						consequence = rule.getValue().getconsequence();
						rule_index = rule.getKey();
						BF.add(consequence); // On ajoute la conséquence de la règles dans la base de faits
						activable.set(rule_index, false); // On bloque l'activation de cette règle dans les (éventuelles) prochaines inférences
						inf = true;
						nb_inf++;
						history.put(nb_inf, rule.getValue()); // On ajoute la règle dans l'historique des inférences

						it_goals =  goals.iterator();
						goal_reached = true;
						while(goal_reached && it_goals.hasNext()){
							goal = it_goals.next();
							if(!BF.contains(goal))
								goal_reached = false;
						}
					}
				}
			}
		}
		
		return goal_reached;
	}
	
	/*
	 * Initialisation des règles activables à true (i.e. savoir si la règle a deja été activée ou non)
	 */
	private ArrayList<Boolean> initActivable(){
		int number_of_rules = BR.getNumberOfRules();
		ArrayList<Boolean> activable = new ArrayList<Boolean>(number_of_rules);
		
		for(int i = 0; i<number_of_rules; i++)
			activable.add(true);
		
		return activable;
	}
	
	/* 
	 * Chaînage arrière
	 */
	public boolean backward(String... goals){
		history.clear();
		List<String> goals_list = Arrays.asList(goals);
		goals_list = Util.toLowerCaseList(goals_list);
		return backward(goals_list);
	}
	
	private boolean backward(List<String> goals){
		boolean verif = true;
		
		Iterator<String> it_goals = goals.iterator();
		String goal;
		
		while(it_goals.hasNext() && verif){
			goal = it_goals.next();
			verif = verification(goal);
		}
		
		return verif;
	}
	
	private boolean ask(String goal){
		boolean keep_asking = true;
		String answer;
		boolean bool_answer = true;
		@SuppressWarnings("resource")
		/*
		 *  Note :
		 *  Fermer le Scanner ferme aussi son flux entrant (ie System.in) ce qui lève un NoSuchElementException si on redemande
		 *  un entrant sur ce flux qui n'existe plus.
		 *  
		 *  => On ne ferme pas le scanner pour assurer le bon fonctionnement du processus
		 *  
		 */
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Voulez vous faire entrer le but \""+goal+"\" dans la base de faits ? (Oui/Non)");
		
		while(keep_asking){
			answer = scan.next();
			if(answer.toLowerCase().equals("oui") || answer.toLowerCase().startsWith("o")){
				keep_asking = false;
				bool_answer = true;
			} else {
				if(answer.toLowerCase().equals("non") || answer.toLowerCase().startsWith("n")){
					keep_asking = false;
					bool_answer = false;
				} else {
					System.out.println("La réponse n'est pas valide. Voulez vous faire entrer le but \""+goal+"\" dans la base de faits ? (Oui/Non)");
				}
			}
				
		}
		
		return bool_answer;
	}
	
	private boolean verification(String goal){
		boolean dem = false;
		
		// 1er cas : goal appartient dejà à la base de fait
		if(BF.contains(goal)){
			dem = true;
			return dem;
		}
		
		// 2em cas : rechercher si goal déductible à partir de BR U BF
		Iterator<Entry<Integer, Rule>> it_rule = BR.iterator(); // Iterateur sur les règles
		Entry<Integer, Rule> rule;
		String consequence;
		
		while(it_rule.hasNext() && dem == false){
			rule = it_rule.next();
			consequence = rule.getValue().getconsequence();
			if(consequence.equals(goal)){
				dem = backward(rule.getValue().getAntecedents());
			}
		}
		
		// 3em cas : Sinon voir si b est demandable
		if(dem == false && demandables.contains(goal)){
			dem = ask(goal);
		}
		
		if(dem){
			BF.add(goal);
		}
		
		return dem;
	}
	
	/*
	 * Methodes d'affichage
	 */	
	public void displayBaseDeFaits(){
		System.out.println("Base de faits : ");
		BF.display();
		System.out.println();
	}
	
	public void displayBaseDeRegles(){
		System.out.println("Base de règles : ");
		BR.display();
		System.out.println();
	}
	
	public void displayDemandables(){
		System.out.println("Demandables : ");
		demandables.display();
		System.out.println();
	}
	
	public void displayHistory(){
		System.out.println("Historique des inférences : ");
		history.display();
		System.out.println();
	}
	
	public void displayAll(){
		displayDemandables();
		displayBaseDeFaits();
		displayBaseDeRegles();
		displayHistory();
	}
	
}
