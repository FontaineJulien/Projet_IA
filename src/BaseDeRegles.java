import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/*
 * Base de règles :
 * 
 * Permet la lecture d'un fichier de règles et le stockage de ses données
 * 
 */

public class BaseDeRegles extends HashMap<Integer,Rule> {
	
	private static final long serialVersionUID = 1L;
	
	public BaseDeRegles(){
		super();
	}
	
	/*
	 *  Lecture du fichier rules_file passé en argument, et stockage des règles lues dans la base de règles
	 */
	public Demandables read(String rules_file){
		
		FileReader file_reader = null;
		BufferedReader buffer = null;
		
		Demandables demandables = new Demandables();
		
		
		try {
			file_reader = new FileReader(rules_file);
			buffer = new BufferedReader(file_reader);
			
			String ruleString;
			Rule rule;
			
			int ruleNumber = 0;
			
			while((ruleString = buffer.readLine()) != null){
				rule = parseRule(ruleString);
				demandables.addAll(rule.getAntecedents());
				this.put(ruleNumber++, rule);
			}
			
			buffer.close();
			file_reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return demandables;
		
	}
	
	/*
	 * Affiche les règles
	 */
	public void display(){
		Iterator<Entry<Integer, Rule>> it = this.iterator();
		
		Entry<Integer, Rule> rule;
		
		while(it.hasNext()){
			rule = it.next();
			System.out.println("Règle n°"+rule.getKey()+" : "+rule.getValue().toString().replace('_', ' '));
		}
	}
	
	/*
	 * Isole les prémisses et la conclusion d'une règle passée en paramètre pour les stocker
	 * dans une instance de Rule
	 */
	private Rule parseRule(String rule){
		String[] parts = rule.replace(" ", "").split("->");
		
		String[] antecedents_array = parts[0].split("&"); // Prémisses
		String consequence = parts[1]; // Consequence
		
		List<String> antecedents = Arrays.asList(antecedents_array);
		return new Rule(antecedents,consequence);
	}
	
	public int getNumberOfRules(){
		return this.size();
	}
	
	public Iterator<Entry<Integer, Rule>> iterator(){
		Set<Entry<Integer, Rule>> set = this.entrySet();
		return set.iterator();
	}
	
}
