
public class Main {

	public static void main(String[] args) {
		
		/*Moteur m = new Moteur("regles.txt","Film","SF","Bataille","Magie","Chevalier");
		System.out.println(m.forward("Star_WARS"));
		m.displayAll();*/
		
		Moteur m = new Moteur("regles.txt","MedievAl");	
		System.out.println(m.backward("JeDI"));	
		m.displayBaseDeFaits();
		
	}
	
}
