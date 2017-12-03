package Controller_package;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import Motor_package.Categorie;
import Motor_package.Fait;
import Motor_package.Moteur;
import Motor_package.Rule;
import UI_package.MonFiltre;
import UI_package.UI_Display.PANEL;
import UI_package.UI_Expert_System;




/**
 * 
 * @author Louis
 */

public class Controller implements ActionListener,WindowListener {
	Moteur moteur;
	boolean moteurIsSaved;
	UI_Expert_System ui;
	
	public final String DEEPFORWARD = "Avant en profondeur";
	public final String WIDEFORWARD = "Avant en largeur";
	public final String BACKWARD = "Arrière";
	
	
	/**
	 * Constructeur 
	 */
	public Controller(){
		moteur = new Moteur();				//Instance Moteur
		moteurIsSaved=true;					//etat de sauvegarde du moteur à true
		ui = new UI_Expert_System();		//Instance Vue
		this.Configure();					//Configuration Controller
	}
	
	

	/**
	 * AJOUT DES LISTENERS SUR LES ELEMENTS DE L'INTERFACE GRAPHIQUE
	 */
	private void Configure(){
		this.ui.panel_Add_Fact.getButton_Add().addActionListener(this);	//bouton ajouter du module "panel_Add_Fact"
		this.ui.panel_Add_Rule.getButton_Add().addActionListener(this);	//bouton ajouter du module "panel_Add_Rule"
		this.ui.panel_Add_Rule.getButton_Del().addActionListener(this);	//bouton effacer du module "panel_Add_Rule"
		this.ui.panel_Add_Category.getButton_Add().addActionListener(this);//bouton effacer du module "panel_Add_Category"
		this.ui.panel_Test.getButton_Enter().addActionListener(this);	//bouton TESTER du module "panel_Input"
		
		this.ui.getMenuItemLoadSystem().addActionListener(this);		//ajout du listener sur le bouton load systeme du menu
		this.ui.getMenuItemNewSystem().addActionListener(this);			//ajout du listener sur le bouton Nouveau Systeme
		this.ui.getMenuItemSave().addActionListener(this);				//ajout du listener sur le bouton Enregistrer
		this.ui.getMenuItemSaveAs().addActionListener(this);			//ajout du listener sur le bouton Enregistrer
		this.ui.getMenuItemQuitApp().addActionListener(this);			//ajout du listener sur le bouton Fermer du menu
		
		this.ui.addWindowListener(this);								//ajout du listener Window sur l'interface graphique
	}
	/**
	 * GESTION DES ÉVENNEMENTS.
	 * On lie les actions des boutons de l'iterface(sauf barre de menu) avec les bonnes methodes du controller
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
			//MODULE AJOUT FAIT
		if(arg0.getSource()==this.ui.panel_Add_Fact.getButton_Add())	//bouton ajouter du module "panel_Add_Fact"
			this.AddFactButtonPressed();
			//MODULE AJOUT CATEGORIE
		if(arg0.getSource()==this.ui.panel_Add_Category.getButton_Add())//bouton ajouter du module "panel_Add_Category"
			this.AddCategoryButtonPressed();
		
			//MODULE AJOUT RÈGLE
		if(arg0.getSource()==this.ui.panel_Add_Rule.getButton_Add())	//bouton ajouter du module "panel_Add_Rule"
			this.AddRuleButtonPressed();
		/*
		if(arg0.getSource()==this.ui.panel_Add_Rule.getButton_Del())	//efface les tableaux du module
			this.ui.panel_Add_Rule.clearAll();
		 */
			//MODULE INPUT
		if(arg0.getSource()==this.ui.panel_Test.getButton_Enter())		//bouton TESTER du module "panel_Input"
			this.TestButtonPressed();
		
			//BOUTON MENU
		if(arg0.getSource()==this.ui.getMenuItemLoadSystem())			//bouton load system
			this.loadSystem();
		if(arg0.getSource()==this.ui.getMenuItemNewSystem())			//bouton nouveau system
			this.newSystem();
		if(arg0.getSource()==this.ui.getMenuItemSave())					//bouton Save system
			this.saveSystem();
		if(arg0.getSource()==this.ui.getMenuItemSaveAs())				//bouton Save As System
			this.saveAsSystem();
		if(arg0.getSource()==this.ui.getMenuItemQuitApp())				//bouton Fermer application
			this.CloseApp();
	}
	
	
	/**
	 * Gère l'ajout d'un NOUVEAU FAIT
	 */
	private void AddFactButtonPressed(){
		this.moteurIsSaved=false;							//l'état de sauvegarde du moteur passe à faux
		
		String new_fact = this.ui.panel_Add_Fact.getText();	//on récupère le fait à ajouter depuis l'interface
		if(new_fact==null || new_fact.trim().length()==0)
			return;
		boolean resultat = this.addNewFact(new_fact.trim());			//on effectue l'ajout du fait
		if(! resultat)							//selon le résultat de l'ajout
			this.ui.panel_Display.addText_Panel("Une erreur s'est produite lors de l'ajout du nouveau fait.",PANEL.RESULT);
		else
			this.ui.panel_Display.addText_Panel(new_fact,PANEL.FACT);
		
		this.ui.panel_Display.setActivePanel(PANEL.FACT);
	}
	
	
	private void AddCategoryButtonPressed(){
		this.moteurIsSaved=false;									//l'état de sauvegarde du moteur passe à faux
		
		String new_Category = this.ui.panel_Add_Category.getText();	//on récupère le nom de ltexte depuis l'interface
		if(new_Category==null || new_Category.trim().length()==0)
			return;
		boolean resultat = this.addNewCategory(new_Category.trim());
		if(resultat==false)
			this.ui.panel_Display.addText_Panel("Une erreur s'est produite lors de l'ajout d'une nouvelle catégorie.",PANEL.RESULT);
		else
			this.ui.panel_Display.addText_Panel(new_Category,PANEL.CATEGORY);
		
		this.ui.panel_Display.setActivePanel(PANEL.CATEGORY);
	}
	
	/**
	 * Gèrer l'ajout d'une nouvelle règle
	 */
	private void AddRuleButtonPressed(){
		this.moteurIsSaved=false;									//l'état de sauvegarde du moteur passe à faux
		
		String premisses = this.ui.panel_Add_Rule.getPremisses();	//les prémisses
		String consequences = this.ui.panel_Add_Rule.getConsequences();//les conséquences
		if(premisses==null || premisses.length()==0 				//vérification des données des chaines des conséquences et prémisses
				|| consequences==null || consequences.length()==0)
			return;						
		String str_categorie = this.ui.panel_Add_Rule.getCategory();
		if(str_categorie==null || 									//vérification de la catégorie
				str_categorie.toLowerCase().trim().equals(new String("aucune").toLowerCase().trim()))
			{														//on sort si la chaine de la catégorie est invalide
			 this.ui.panel_Add_Rule.jta_premisse.setText(premisses);
			 this.ui.panel_Add_Rule.jta_consequence.setText(consequences);
			 return;
			}
	
		
															
		boolean isAdded=false;
		isAdded = this.addNewRule(premisses,consequences,str_categorie);//traitement de l'ajout de la règle
		if(isAdded)
			this.ui.panel_Display.updateText_Panel(this.moteur.getRules_ToListString_WithCategories(),PANEL.RULE);
		else{
		}
		
		this.ui.panel_Display.setActivePanel(PANEL.RULE);			//sélection du panel actif
	}
	
	
	/**
	 * Lancer le Test Moteur avec l
	 * A MODIFER 
	 */
	private void TestButtonPressed(){
		this.moteurIsSaved=false;									//l'état de sauvegarde du moteur passe à faux
		
		String str_goals = this.ui.panel_Test.getInputText();		//la chaine des MOTS à Tester.
		if(str_goals == null || str_goals.trim().length()==0)		//Vérification de la chaine
			return;
				
		String[] goals = this.splitAndTrim(str_goals);				//Découpage de la chaine en liste et trim de chaque element
		List<Fait> liste_goals = Fait.ArrayString_toFact(goals);	//Transcription de la liste de MOTS en liste de FAITS
		
		List<String> liste_cat = this.ui.panel_Test.getCategoryList();//la liste des chaines, réprésentant les catégories, sélectionnés pour le test.
		List<Categorie> liste_category = Categorie.listString_toCategorie(liste_cat);//Transcription de la liste de chaines en liste de Categorie
		
		
		
		this.printContext(liste_goals);
		boolean res_test = this.testGoals(liste_goals,liste_category);//TEST MOTEUR	
		this.printResult(res_test);						//Affcihage des résultats
		if(res_test)
			this.ui.panel_Display.updateText_Panel(this.moteur.getAllFacts_StringFormat(), PANEL.FACT);
	}
	
	/**
	 * Ouvre une boite de dialogue pour choisir un fichier de config.
	 * Puis envoie le chemin du fichier au controller pour le charger.
	 */
	private void loadSystem(){
		try{
			File currDir = null;									//répertoire courant
			currDir = new File(".").getCanonicalFile();
			
			JFileChooser dialogue = new JFileChooser(currDir);		//pop-up
			dialogue.setDialogTitle("Ouvrir un System Expert :");	//nom de la pop-up
			dialogue.setFileSelectionMode(JFileChooser.FILES_ONLY);	//files only
			dialogue.setAcceptAllFileFilterUsed(false);				
			dialogue.setFileFilter(									//filtre sur les extensios de fichier
					new MonFiltre(new String[]{"esf"},"Expert System File(*.esf)"));
			dialogue.showOpenDialog(null);							//ouverture de la pop-up
			
			this.loadSystem(dialogue.getSelectedFile());			//ouverture du fichier
		}
		catch(IOException e){
			}
	}
	
	/**
	 * Sauvegarde le system dans le fichier de sauvegarde
	 * @return
	 */
	private boolean saveSystem(){
		Integer res = this.moteur.saveSystem();
		if(res==-1)
			return this.saveAsSystem();
		this.moteurIsSaved=true;
		return true;
	}
	
	private boolean saveAsSystem(){
		try{
			//repertoire courant
			File currDir = null;
			currDir = new File(".").getCanonicalFile();
			//boite de dialogue
			JFileChooser dialogue = new JFileChooser(currDir);
			dialogue.setDialogTitle("Enregistrer un System Expert :");
			dialogue.setFileSelectionMode(JFileChooser.FILES_ONLY);//files only
			dialogue.setAcceptAllFileFilterUsed(false);
			dialogue.setFileFilter(//file like *.esf
					new MonFiltre(
							new String[]{"esf"},"Expert System File(*.esf)")
					);
			//ouverture de la pop up
			dialogue.showOpenDialog(null);
			
			if(dialogue.getSelectedFile()==null)		//si le fichier choisit est null
				{this.moteurIsSaved=false;return false;}//on renvoit faux
			
			File le_file = new File(dialogue.getSelectedFile()+".esf");
			this.moteur.setSystemFile(le_file);
			this.moteur.getSystemFile().createNewFile();
			//on appelle le controller avec le nom de fichier obtenu
			return saveSystem();
		}
		catch(IOException e){
				//on ne fait rien
			this.moteurIsSaved=false;
			return false;
			}
	}
	

	
	
	/**
	 * Charge un system expert à partir d'un fichier 
	 * @param systemFile
	 */
	public boolean loadSystem(File systemFile){
		if(systemFile==null)					//si le fichier est null
			return false;						//on ne fait rien
		else{
			this.moteur = new Moteur();			//nouvelle instance moteur
			this.ui.clearUI();					//on reset l'interface
			if(!this.moteur.LoadSystem(systemFile))//on charge le fichier
				return false;					//renoyer faux si erreur lors du chargement
			
			List<String> list_regles	=		//chargement des regles dans l'interface
					this.moteur.getRules_ToListString_WithCategories();
			for(String rule : list_regles)
				this.ui.panel_Display.addText_Panel(rule, PANEL.RULE);
			
			List<String> list_faits		=		//chargement des faits dans l'interface
					this.moteur.getAllFacts_StringFormat();
			for(String fact : list_faits)
				this.ui.panel_Display.addText_Panel(fact, PANEL.FACT);
			
			List<String> list_categories=new ArrayList<String>();		//chargement des catégories dans l'interface
			list_categories.add("toutes");
			list_categories.addAll(this.moteur.getAllCategories_StringFormat());
			for(String cat : list_categories)
				this.ui.panel_Display.addText_Panel(cat,PANEL.CATEGORY);
			this.ui.panel_Test.updateCategoryList(list_categories);		//mise à jour liste catégories panel_Test
			this.ui.panel_Add_Rule.updateCategoryList(this.moteur.getAllCategories_StringFormat()); //mise à jour liste catégories panel_New_Rule
			
			this.moteurIsSaved=true;			//état de sauvegarde à true			
			return true;
		}
	}
	
	/**
	 * Ouvre un nouveau system. Ferme le précédent si ouvert.
	 */
	public void newSystem(){
		int reponse = JOptionPane.NO_OPTION;
		if(!this.moteurIsSaved)
		{
			reponse = JOptionPane.showConfirmDialog(this.ui,
					"Des modifications sont en cours. Vos modifications seront\nperdues si vous ne sauvegarder pas." +
							"\n\nVoulez-vous les sauvegarder?\n",
					"Enregistrement",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
		}
		
		switch(reponse){
		case JOptionPane.YES_OPTION:
			this.ui.clearUI();			//on vide le contenu des interfaces
			this.saveSystem();			//on sauvegarde le systeme
			this.moteur = new Moteur();	//on créer un nouveau systeme
			return;
		case JOptionPane.NO_OPTION:
			this.ui.clearUI();			//on vide le contenu des interfaces
			this.moteur = new Moteur();	//on créer un nouveau systeme
			this.moteurIsSaved=true;
			return;
		default:
			return;
		}
	}

	
	/**
	 * Ajoute un nouveau fait dans la base de connaissance
	 * @param newFact
	 * @return boolean -> true si succès
	 */
	public boolean addNewFact(String newFact){
		return (this.moteur.AddFact(newFact));		
	}
	
	/**
	 * Ajoute une catégorie dans le moteur.
	 * @param newCategory
	 * @return boolean -> true si succès
	 */
	public boolean addNewCategory(String newCategory){
		boolean done = this.moteur.AddCategory(newCategory);
		if(done)
		{
			List<String> list_cat = new ArrayList<String>();
			list_cat.add("toutes");
			list_cat.addAll(this.moteur.getAllCategories_StringFormat());
			this.ui.panel_Test.updateCategoryList(list_cat);
			this.ui.panel_Add_Rule.updateCategoryList(this.moteur.getAllCategories_StringFormat());
			return done;
		}
		else
			return done;
	}
	
	/**
	 * 
	 * Ajoute une nouvelle règle dans la base de règles.
	 * et renvoie la liste des faits mise à jour
	 * @param premisses		-> les prémisses
	 * @param consequences	-> les consequences
	 * @param str_categorie	-> la categorie
	 * @return List string	-> La chaine de la règle ajoutée.
	 */
	public boolean addNewRule(String premisses, String consequences,String str_categorie){
		if(premisses==null || consequences==null)
			return false;
		
		if(str_categorie.equals("aucune"))
			return false;
		
		Categorie categorie = this.moteur.getCategorie(str_categorie);//on récupère la catégorie auprès du moteur.
																	//C'est une sécurité supplémentaire pour être certain que la catégorie
																	//sélectionnée existe réellement.
		if(categorie==null)											//vérification supplémentaire. Si la categorie est null, alors elle 
		return false;												//n'existe pas en Base de Catégorie. on sort.
		
		//MESSAGE D'erreur pus explicite à mettre dans les panels de l'ajout de règle ou ailleurs.

		//on récupère les string des textarea du panneau newRule
		String[] premisses_array = premisses.split("\n");
		String[] consequence_array = consequences.split("\n");
		for(String str : premisses_array)
			str=str.trim();
		for(String str : consequence_array)
			str=str.trim();
		
		//traiement des lignes vides								//On retire les lignes blanches
		List<String> prem = new ArrayList<String>();
		List<String> cons = new ArrayList<String>();
		for(String str : premisses_array)
			if(!str.trim().equals(""))
				prem.add(str.trim());
		for(String str : consequence_array)
			if(!str.trim().equals(""))
				cons.add(str.trim());
		
		if(cons.size()==0 || prem.size()==0)						//si la règle ne contient que des lignes blanches
			return false;											//on sort
		
		premisses_array = new String[prem.size()];
		for(int i=0;i<prem.size();i++)
			premisses_array[i]=prem.get(i);
		consequence_array = new String[cons.size()];
		for(int i=0;i<cons.size();i++)
			consequence_array[i]=cons.get(i);
		
		//on demande au moteur d'ajouter la règle
		Rule added_rule = this.moteur.AddRule(premisses_array,consequence_array,categorie);
		if(added_rule!=null)
			return true;
		else
			return false;
		
	}
	
	
	

	/**
	 * Demande au moteur de tester la valeur.
	 * Le mode de fonctionnement du moteur est récupèré dans les paramètres du menu, sous l'onglet "MODE".
	 * @param str_goals
	 * @return boolean, true si le test est concluant, false sinon.
	 */
	public boolean testGoals(List<Fait> liste_goals,List<Categorie> liste_categories){
		//on vérifie les données testées.
		if(liste_goals==null)
			return false;
		
		boolean boole = false;
		
		for(Categorie cat : liste_categories)
			if(cat.getCategorie().equals("toutes"))	// si la liste des catégories contient le mot "toutes"
				{liste_categories=null;break;}		// alors on met à null la liste. Le moteur fera le test 
													// sur toutes les catégories
		
		//Selon le MODE choisi dans l'interface, on lance le test dans le moteur
		switch(this.ui.getSystemModeValue()){
			case "WIDEFORWARD" : boole=this.moteur.wideForward(liste_categories,liste_goals);break; 
			case "DEEPFORWARD" : boole= this.moteur.deepForward(liste_categories,liste_goals);break;
			case "BACKWARD" : boole=this.moteur.backward(liste_goals);break;
			default : boole=this.moteur.wideForward(null,liste_goals);break;
		}//switch end
		return boole;
	}//function end
	

	
	
	/**
	 * Affcihage du contexte de recherche, dans le panel RESULT.
	 * (Etat du moteur et des conaissances avant le test)
	 * @param liste_goals
	 */
	private void printContext(List<Fait> liste_goals){
		this.ui.panel_Display.updateText_Panel("",PANEL.RESULT);
		this.ui.panel_Display.addText_Panel("\n------------------------------",PANEL.RESULT);
		
		//les goals.
		this.ui.panel_Display.addText_Panel("\nMot(s) testé(s) : ",PANEL.RESULT);
		for(int i=0;i<liste_goals.size();i++)
			this.ui.panel_Display.addText_Panel_InLine(liste_goals.get(i).toString(), PANEL.RESULT);
		
		//les catégories de recherche
		this.ui.panel_Display.addText_Panel("\n\nCatégorie(s) de recherche : ",PANEL.RESULT);
		for(String str : this.ui.panel_Test.getCategoryList())
			this.ui.panel_Display.addText_Panel_InLine(str+"; ", PANEL.RESULT);
		
		//le mode de fonctionnement.
		this.ui.panel_Display.addText_Panel("\n\nMode de fonctionnement de l'agorithme : "+this.getMotorMode(),PANEL.RESULT);
				
		//les demandables
		this.ui.panel_Display.addText_Panel("\nDemandables : ",PANEL.RESULT);
		for(String str : this.moteur.getDemandables_ToListString())
			this.ui.panel_Display.addText_Panel_InLine("; "+str, PANEL.RESULT);
		
		//les faits
		this.ui.panel_Display.addText_Panel("\n\nFaits : ",PANEL.RESULT);
		List<String> liste_faits = this.moteur.getFacts_ToListString();
		for(int i=0;i<liste_faits.size();i++)
			this.ui.panel_Display.addText_Panel_InLine("; "+liste_faits.get(i).toString(), PANEL.RESULT);
	}
	
	/**
	 * Affiche les resultats d'un test dans le panel RESULT
	 * @param les_goals
	 */
	private void printResult(boolean res_test){
		//les règles
		this.ui.panel_Display.addText_Panel("\n\nRègles : ",PANEL.RESULT);
		List<String> liste_regles = this.moteur.getRules_ToListString_WithCategories();
		for(int i=0;i<liste_regles.size();i++)
			this.ui.panel_Display.addText_Panel(liste_regles.get(i).toString(), PANEL.RESULT);
		
		//historique
		this.ui.panel_Display.addText_Panel("\nHisorique : ",PANEL.RESULT);
		List<String> liste_historique = this.moteur.getHistory_ToListString();
		for(int i=0;i<liste_historique.size();i++)
			this.ui.panel_Display.addText_Panel(liste_historique.get(i).toString(), PANEL.RESULT);
		
		//le résultat du test
		String res = ""+res_test;
		this.ui.panel_Display.addText_Panel("\nResultat : "+res.toUpperCase()+"",PANEL.RESULT);
		if(res_test)
			this.ui.panel_Display.addText_Panel("\nLe(s) mot(s) ont/a été ajouté(s) dans la base de Faits.\n(Sauf si déjà présent(s))", PANEL.RESULT);
		
		this.ui.panel_Display.setActivePanel(PANEL.RESULT);
					
		this.moteur.clearHistory();
	}
	
	


	/**
	 * Convertir une chaine en un tableau de String.
	 * La chaine est trimée puis découpée sur le délimiteur "&".
	 * Puis chque element du tableau est à nouveau trimé.
	 * @param str_goals
	 * @return
	 */
	 private String[] splitAndTrim(String str_goals){
		 String[] les_goals=str_goals.trim().split("&");
		 for(int i=0;i<les_goals.length;i++){
			 les_goals[i]=les_goals[i].trim();
		 }
		 return les_goals;
	 }
	
	
	/**
	 * Sauvegarde le systeme en cours et ferme l'application.
	 */
	private void CloseApp(){
		this.saveSystem();
		if(this.moteurIsSaved)//si la sauvegarde est effectuée
			System.exit(0);
	}
	
	
	/**
	 * Renvoyer le nom du mode du moteur sous forme d'une chaîne.
	 * @return
	 */
	private String getMotorMode(){
		switch(this.ui.menuBar.getMotorMode()){
		case DEEPFORWARD: return "Chaînage avant en profondeur";
		case WIDEFORWARD : return "Chaînage avant en largeur";
		case BACKWARD : return "Chaînage arrière";
		default : return null;
		}
	}


	
	
	
	
	/**
	 * #########  WINDOWEVENT  ##########
	 */

	@Override
	public void windowActivated(WindowEvent arg0) {
	}
	@Override
	public void windowClosed(WindowEvent arg0) {
	}
	@Override
	public void windowClosing(WindowEvent arg0) {
		if(this.moteurIsSaved)		//si le moteur est sauvegardé, on quitte normalement
			System.exit(0);
		else{						//sinon on demande si on veut sauvegarder
			int reponse = JOptionPane.showConfirmDialog(this.ui,
				"Enregistrer les modifications  avant la fermeture ?\nVos modifications seront perdues si vous ne sauvegarder pas.",
				"Enregistrer avant de fermer",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
			if(reponse==JOptionPane.YES_OPTION)
			{
				if(this.saveSystem()){	//si succès sauvegarde
					System.exit(0);
				}
				else					//sinon on ne quitte pas
					return;
			}
			else if(reponse==JOptionPane.NO_OPTION){//sin réponse est non
				System.exit(0);			//on quitte sans sauvegarder.
			}else{
				return;
			}
		}
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}
	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}
	@Override
	public void windowIconified(WindowEvent arg0) {
	}
	@Override
	public void windowOpened(WindowEvent arg0) {
	}
	
}
