import Controller_package.Controller;
import Motor_package.Fait;
import Motor_package.Moteur;



public class Main {

	public static void main(String[] args) {
		Moteur m;
		Fait f;
		
		/**
		 * - Vérifier avant tout algorithme et pendant l'algo, que les goals que l'ont testent n'appartiennent pas déjà à la base de faits.
		 * 		! Déjà le cas pour le chaînage arrière.
		 * 
		 * - Prendre en compte les catégories lors de l'ajout d'une nouvelle règle, dans le chargement d'un fichier de sauvegarde, dans la sauvegarde, ET SURTOUT dzns le moteur.
		 */
		
		
		//*
		new Controller();

		
		/*/
		Moteur m = new Moteur("regles.esf",new Fait("Magie",true),new Fait("Bataille",true),new Fait("Chevalier",true),new Fait("Vaisseau",true),new Fait("Film",true));
		System.out.println(m.deepForward(new Fait("Magie",true)));
		m.displayAll();
		
		m = new Moteur("regles.esf",new Fait("MagIE",true),new Fait("BaTaille",true),new Fait("ChEvalier",true),new Fait("VaissEau",true),new Fait("FiLm",true));
		System.out.println(m.wideForward(new Fait("Star_WaRs",true)));
		m.displayAll();
		
		m = new Moteur("regles.esf", new Fait("Chevalier",true),new Fait("Magie",true));
		System.out.println(m.backward(new Fait("Jedi",true)));
		m.displayDemandables();
		m.displayBaseDeFaits();
		//*/
	}
	
}
