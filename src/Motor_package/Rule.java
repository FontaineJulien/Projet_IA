package Motor_package;
import java.util.Iterator;
import java.util.List;

/*
 * Permet de stocker une règles, càd ses antécédents et les conséquences
 */

public class Rule {
	
	private List<Fait> antecedents;
	private List<Fait> consequences;
	private String categorie;
	
	public Rule(List<Fait> antecedents, List<Fait> consequences, String categorie){
		
		this.antecedents = antecedents;
		this.consequences = consequences;
		this.categorie = categorie;
	}
	
	public String getCategorie(){
		return categorie;
	}
	
	/**
	 * Renoyer une Regle sous forme d'une chaine.
	 */
	public String toString(){
		String s = "";
		Iterator<Fait> it_antecedents = antecedents.iterator();
		Iterator<Fait> it_consequences = consequences.iterator();
		
		if(it_antecedents.hasNext())
			s += it_antecedents.next().getLabel();
		while(it_antecedents.hasNext()){
			s += " & "+it_antecedents.next().getLabel();
		}
		if(it_consequences.hasNext())
			s += " => "+it_consequences.next().getLabel();
		while(it_consequences.hasNext()){
			s += " & "+it_consequences.next().getLabel();
		}
		return s;
	}
	
	public Iterator<Fait> iteratorAntecedents(){
		return antecedents.iterator();
	}
	
	public Iterator<Fait> iteratorConsequences(){
		return consequences.iterator();
	}
	
	public List<Fait> getAntecedents(){
		return antecedents;
	}
	public List<Fait> getConsequences(){
		return consequences;
	}
	
	
}
