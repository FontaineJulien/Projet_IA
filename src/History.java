import java.util.ArrayList;
import java.util.Iterator;


public class History extends ArrayList<Inference> {

	private static final long serialVersionUID = 1L;
	
	public History(){
		super();
	}
	
	public void display(){
		Iterator<Inference> it_inference = this.iterator();
		
		while(it_inference.hasNext()){
			it_inference.next().display();
		}
	}
}
