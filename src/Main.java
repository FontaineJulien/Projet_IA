
public class Main {

	public static void main(String[] args) {
		
		/*Moteur m = new Moteur("regles.txt","Serie","Gore");
		System.out.println(m.deepForward("THE_WALKING_Dead"));
		m.displayHistory();*/
		
		Moteur m = new Moteur("regles.txt","Film","SF","Bataille","Magie","Chevalier");
		System.out.println(m.wideForward("Star_WARS"));
		m.displayHistory();
		
		/*Moteur m = new Moteur("regles.txt","MedievAl");	
		System.out.println(m.backward("JeDI"));	
		m.displayBaseDeFaits();*/
		
	}
	
}
