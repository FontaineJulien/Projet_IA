import java.util.HashSet;
import java.util.Iterator;


public class Demandables extends HashSet<String> {
	
	private static final long serialVersionUID = 1L;
	
	public Demandables(){
		super();
	}
	
	public void display(){
		Iterator<String> it = this.iterator();
		
		if(it.hasNext())
			System.out.print(it.next().replace("_", " "));
		
		while(it.hasNext()){
			System.out.print(", "+it.next().replace('_', ' '));
		}
		
		System.out.println();
	}

}
