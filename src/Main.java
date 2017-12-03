import Controller_package.Controller;

public class Main {

    public static void main( String[] args ) {

        /**
         * -DOUBLONS
     	 * 	Toutes les bases gèrent l'interdiction des doublons, y compris la base de règle
     	 *
     	 * -Cohérence des FAITS
         * 		La base de Fait gère la cohérence. ( si FAIT1 appartient alors !FAIT1 ne peut pas appartenir à la base)
     	 * 
     	 * À AJOUTER:
         * -Ajouter la cohérence des règles.
     	 *		Actuellement on peut ajouter des règles contradictoires.
     	 * 
         */

        //*
         new Controller();

         
         
        /*/
        Moteur m = new Moteur( "regles.esf", new Fait( "Magie", true ));
        
        ArrayList<Fait> goals = new ArrayList<Fait>();
        ArrayList<Categorie> categories = new ArrayList<Categorie>();
        
        goals.add(new Fait( "Force", true ));
        
        categories.add(new Categorie("Aventure"));
        
        System.out.println( m.deepForward( categories, goals ) );
        m.displayAll();

        /*
         * m = new Moteur( "regles.esf", new Fait( "MagIE", true ), new Fait(
         * "BaTaille", true ), new Fait( "ChEvalier", true ), new Fait(
         * "VaissEau", true ), new Fait( "FiLm", true ) ); System.out.println(
         * m.wideForward( new Fait( "Star_WaRs", true ) ) ); m.displayAll();
         * 
         * m = new Moteur( "regles.esf", new Fait( "Chevalier", true ), new
         * Fait( "Magie", true ) ); System.out.println( m.backward( new Fait(
         * "Jedi", true ) ) ); m.displayDemandables(); m.displayBaseDeFaits();
         * //
         */
    }

}
