# Projet_IA

Utilisation:
  	On lui passe le nom du fichier de règles placé à la racine du projet.
  	Ensuite, on lui passe la base de connaissance sur laquelle on veut partir
  	pour inférer.
  	Ex : Moteur m1 = new Moteur("regles.txt","Serie","Gore");
  		 Moteur m2 = new Moteur("regles.txt","Film","SF","Bataille","Magie","Chevalier");
  		 Moteur m3 = new Moteur("regles.txt","Medieval");
  
  	On choisit la méthode d'inférence que l'on veut :
  		- Chaînage avant en profondeur : m.deepForward(String goal_1, String goal_2, ... ,goal_N);
  		- Chaînage avant en largeur : m.wideForward(String goal_1, String goal_2, ... ,goal_N);
  		- Chaînage arrière : m.backward(String goal_1, String goal_2, ... ,goal_N );
  Ex : m1.deepForward("The_Walking_Dead");
  		m2.wideForward("Star_Wars");
  		m3.backward("Jedi");
  
   Resultat : true si le but est atteint, false sinon
  
  	On peut afficher :
  		- l'historique des inférences du chaînage avant : m.displayHistory();
  		- l'ensemble des données (base de règles, base de faits, demandables, historique) : m.displayAll();
       - les données individuellement : m.displayDemandables(); m.displayBaseDeRegles(); m.displayBaseDeFaits(); m.displayHistory();
  
  Note : "m.displayHistory();" n'affichera rien dans le cas d'un chaînage arrière;
  
  Syntaxe du fichier de la base de règle : <antecedent> & <antecedent> & ... & <antecedent> -> <consequence>
