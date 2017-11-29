package Motor_package;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class History extends ArrayList<Inference> {

	private static final long serialVersionUID = 1L;
	
	public History(){
		super();
	}
	
	/**
	 * Affiche l'historique sur la sortie standard.
	 */
	public void display(){
		Iterator<Inference> it_inference = this.iterator();
		
		while(it_inference.hasNext()){
			it_inference.next().display();
		}
	}
	
	/**
	 * Renvoit l'historique sous forme d'une liste de String
	 */
	public List<String> toStringList(){
		Iterator<Inference> it_inference = this.iterator();
		List<String> history = new ArrayList<String>();
		while(it_inference.hasNext()){
			history.add(it_inference.next().ToString());
		}
		return history;
	}
	
}
