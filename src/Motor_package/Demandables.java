package Motor_package;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


public class Demandables extends HashSet<Fait> {
	
	private static final long serialVersionUID = 1L;
	
	public Demandables(){
		super();
	}
	
	public void display(){
		Iterator<Fait> it = this.iterator();
		Fait f;
		
		if(it.hasNext()){
			f = it.next();
			if(f.getValuation())
				System.out.print(f.getLabel());
			else
				System.out.print("!"+f.getLabel().replace('_', ' '));
		}
		
		while(it.hasNext()){
			f = it.next();
			if(f.getValuation())
				System.out.print(", "+f.getLabel().replace('_', ' '));
			else
				System.out.print(", !"+f.getLabel().replace('_', ' '));
		}
		
		System.out.println();
	}
	
	public boolean contains(Fait f){
		Iterator<Fait> it = this.iterator();
		Fait current_f;
		
		while(it.hasNext()){
			current_f = it.next();
			if(current_f.equals(f))
				return true;
		}
		
		return false;
	}
	
	public boolean add(Fait f){
		Iterator<Fait> it = this.iterator();
		Fait current_f;
		
		while(it.hasNext()){
			current_f = it.next();
			if(current_f.equals(f))
				return false;
		}
		return super.add(f);
	}
	
	/**
	 * renvoyer la liste des demandables sous forme d'une liste de string
	 * @return
	 */
	public List<String> getDemandables_ToListString(){
		Iterator<Fait> it = this.iterator();
		
		List<String> liste = new ArrayList<String>();
		
		while(it.hasNext()){
			liste.add(it.next().toString());
		}
		return liste;
	}
	

}
