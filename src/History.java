import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public class History extends HashMap<Integer, Rule> {

	private static final long serialVersionUID = 1L;
	
	public History(){
		super();
	}
	
	public void display(){
		Set<Entry<Integer, Rule>> set = this.entrySet();
		Iterator<Entry<Integer, Rule>> it = set.iterator();
		
		Entry<Integer, Rule> rule;
		
		while(it.hasNext()){
			rule = it.next();
			System.out.println("Inférence n°"+rule.getKey()+" : "+rule.getValue().toString().replace('_', ' '));
		}
	}
}
