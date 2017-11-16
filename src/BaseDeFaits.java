import java.util.HashSet;
import java.util.Iterator;

/*
 * 
 * Base de faits :
 * 
 * Permet de stocker les faits que l'on va accumuler au cours des inférences du moteur
 * 
 */

public class BaseDeFaits extends HashSet<String> {
	
	private static final long serialVersionUID = 1L;
	
	public BaseDeFaits(String... knowledge){
		super();
		for(String elem:knowledge)
			this.add(elem.toLowerCase());
	}
	
	/*public boolean contains(String antecedent){
		return this.contains(antecedent.toLowerCase());
	}*/
	
	public boolean add(String knowledge){
		return super.add(knowledge.toLowerCase());
	}
	
	
	/*
	 * Affichage de la base de fait
	 */
	public void display(){
		Iterator<String> it = this.iterator();
		
		if(it.hasNext())
			System.out.print(it.next());
		
		while(it.hasNext()){
			System.out.print(", "+it.next().replace('_', ' '));
		}
		
		System.out.println();
	}

}
