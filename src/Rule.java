import java.util.Iterator;
import java.util.List;

/*
 * Règle :
 * 
 * Permet de stocker une règles, càd ses antécédents et la conséquence
 * 
 */

public class Rule {
	
	private List<String> antecedents;
	private String consequence;
	
	public Rule(List<String> antecedents, String consequence){
		
		this.antecedents = Util.toLowerCaseList(antecedents);
		this.consequence = consequence.toLowerCase();
	}
	
	public String getconsequence(){
		return consequence;
	}
	
	public List<String> getAntecedents(){
		return antecedents;
	}
	
	public String toString(){
		
		String s = antecedents.toString()+" => "+consequence.replace("_", " ");
		return s;
	}
	
	public Iterator<String> iterator(){
		return antecedents.iterator();
	}
	
	
}
