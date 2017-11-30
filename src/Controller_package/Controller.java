package Controller_package;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;

import Motor_package.Fait;
import Motor_package.Moteur;
import Motor_package.Rule;
import UI_package.MonFiltre;
import UI_package.UI_Expert_System;
import UI_package.UI_Display.PANEL;




/**
 *  A FAIRE:
 * 			-verifier que si on met lusieurs mot sur une ligne de ce label, il n'y a pas d'erreur.
 * 
 * AJouter des vérifications syntaxiques dans la saisie des goals, des faits et des règles.
 * 
 * 	
 * 
 * @author Louis
 *
 */

public class Controller implements ActionListener {
	Moteur moteur;
	UI_Expert_System ui;
	
	public final String DEEPFORWARD = "Avant en profondeur";
	public final String WIDEFORWARD = "Avant en largeur";
	public final String BACKWARD = "Arrière";
	
	
	public Controller(){
		//on instancie le moteur et la vue
		moteur = new Moteur();
		ui = new UI_Expert_System();
		this.configureUI_Expert_System();
	}
	
	

	/**
	 * AJOUT DES LISTENERS SUR LES ELEMENTS DE L'INTERFACE GRAPHIQUE
	 */
	private void configureUI_Expert_System(){
		this.ui.panel_Add_Fact.getButton_Add().addActionListener(this);	//bouton ajouter du module "panel_Add_Fact"
		this.ui.panel_Add_Rule.getButton_Add().addActionListener(this);	//bouton ajouter du module "panel_Add_Rule"
		this.ui.panel_Add_Rule.getButton_Del().addActionListener(this);	//bouton effacer du module "panel_Add_Rule"
		this.ui.panel_Add_Category.getButton_Add().addActionListener(this);	//bouton effacer du module "panel_Add_Category"
		this.ui.panel_Test.getButton_Enter().addActionListener(this);	//bouton TESTER du module "panel_Input"
		
		
		//ajout du listener sur le bouton load systeme du menu
		this.ui.getMenuItemLoadSystem().addActionListener(this);
		//ajout du listener sur le bouton Nouveau Systeme
		this.ui.getMenuItemNewSystem().addActionListener(this);
		//ajout du listener sur le bouton Enregistrer
		this.ui.getMenuItemSave().addActionListener(this);
		//ajout du listener sur le bouton Enregistrer
		this.ui.getMenuItemSaveAs().addActionListener(this);
		//ajout du listener sur le bouton Fermer du menu
		this.ui.getMenuItemQuitApp().addActionListener(this);
	}
	/**
	 * GESTION DES ÉVENNEMENTS.
	 * On lie les actions des boutons de l'iterface(sauf barre de menu) avec les bonnes methodes du controller
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//MODULE AJOUT FAIT
		if(arg0.getSource()==this.ui.panel_Add_Fact.getButton_Add())//bouton ajouter du module "panel_Add_Fact"
			this.AddFactButtonPressed();
		//MODULE AJOUT CATEGORIE
		if(arg0.getSource()==this.ui.panel_Add_Category.getButton_Add())//bouton ajouter du module "panel_Add_Category"
			this.AddCategoryButtonPressed();
		
	//MODULE AJOUT RÈGLE
		if(arg0.getSource()==this.ui.panel_Add_Rule.getButton_Add())//bouton ajouter du module "panel_Add_Rule"
			this.AddRuleButtonPressed();
		if(arg0.getSource()==this.ui.panel_Add_Rule.getButton_Del())//efface les tableaux du module
			this.ui.panel_Add_Rule.clearAllTexts();
	//MODULE INPUT
		if(arg0.getSource()==this.ui.panel_Test.getButton_Enter())//bouton TESTER du module "panel_Input"
			this.TestButtonPressed();
		
	//BOUTON MENU
		if(arg0.getSource()==this.ui.getMenuItemLoadSystem())//bouton load system
			this.loadSystem();
		if(arg0.getSource()==this.ui.getMenuItemNewSystem())//bouton nouveau system
			this.newSystem();
		if(arg0.getSource()==this.ui.getMenuItemSave())//bouton Save system
			this.saveSystem();
		if(arg0.getSource()==this.ui.getMenuItemSaveAs())//bouton Save As System
			this.saveAsSystem();
		if(arg0.getSource()==this.ui.getMenuItemQuitApp())//bouton Fermer application
			this.CloseApp();
	}
	
	/**
	 * Ouvre une boite de dialogue pour choisir un fichier de config.
	 * Puis envoie le chemin du fichier au controller pour le charger.
	 */
	private void loadSystem(){
		try{
			//repertoire courant
			File currDir = null;
			currDir = new File(".").getCanonicalFile();
			
			//boite de dialogue
			JFileChooser dialogue = new JFileChooser(currDir);
			dialogue.setDialogTitle("Ouvrir un System Expert :");
			dialogue.setFileSelectionMode(JFileChooser.FILES_ONLY);//files only
			dialogue.setAcceptAllFileFilterUsed(false);
			dialogue.setFileFilter(//file like *.esf
					new MonFiltre(
							new String[]{"esf"},"Expert System File(*.esf)")
					);
			//ouverture de la pop up
			dialogue.showOpenDialog(null);
			
			//on appelle le controller avec le nom de fichier obtenu			
			this.loadSystem(dialogue.getSelectedFile());
		}
		catch(IOException e){
				//on ne fait rien
			}
	}//loadSystem end
	
	/**
	 * Sauvegarde le system dans le fichier de sauvegarde
	 * @return
	 */
	private boolean saveSystem(){
		Integer res = this.moteur.saveSystem();
		if(res==-1)
			return this.saveAsSystem();
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
			
			File le_file = new File(dialogue.getSelectedFile()+".esf");
			this.moteur.setSystemFile(le_file);
			this.moteur.getSystemFile().createNewFile();
			//on appelle le controller avec le nom de fichier obtenu			
			return saveSystem();
		}
		catch(IOException e){
				//on ne fait rien
			return false;
			}
	}
	
	/**
	 * Ajoute un nouveau fait dans la base de connaissance
	 * et renvoie la liste des faits mise à jour
	 * @param newFact
	 * @return String -> le mot ajouté si succès, "erreur" sinon.
	 */
	public boolean addNewFact(String newFact){
		return (this.moteur.AddFact(newFact));		
	}
	
	public boolean addNewCategory(String newCategory){
		return this.moteur.AddCategory(newCategory);
	}
	
	/**
	 * Ajoute une nouvelle règle dans la base de règles.
	 * et renvoie la liste des faits mise à jour
	 * @param newFact
	 * @return List string -> la liste des faits à jour
	 */
	public String addNewRule(String premisses, String consequences){
		if(premisses==null || consequences==null)
			return null;
		
		//on récupère les string des textarea du panneau newRule
		String[] premisses_array = premisses.split("\n");
		String[] consequence_array = consequences.split("\n");
		for(String str : premisses_array)
			str=str.trim();
		for(String str : consequence_array)
			str=str.trim();
		//String[] categorie_array = null;
		
		//on demande au moteur d'ajouter la règle
		Rule added_rule = this.moteur.AddRule(premisses_array,consequence_array,null);
		if(added_rule==null)
			return null;
		else
			return added_rule.toString();
		
	}
	
	
	/**
	 * Charge un fichier de config d'un systeme expert.
	 * @param systemFile
	 */
	public boolean loadSystem(File systemFile){
		this.moteur = new Moteur();				//on charge un nouveau moteur. Sans sauvegarder le précédent.
		this.ui.clearUI();
		//on charge le fichier xml
		if(!this.moteur.LoadSystem(systemFile))
			return false;
		
		
		
		//ET on met à jour le panneau d'affichage des règles.
		List<String> list_regles = this.moteur.getAllRules_StringFormat();
		List<String> list_faits = this.moteur.getAllFacts_StringFormat();
		List<String> list_categories = this.moteur.getAllCategories_StringFormat();
		for(String rule : list_regles)
			this.ui.panel_Display.addText_Panel(rule, PANEL.RULE);
		for(String fact : list_faits)
			this.ui.panel_Display.addText_Panel(fact, PANEL.FACT);
		for(String cat : list_categories)
			this.ui.panel_Display.addText_Panel(cat,PANEL.CATEGORY);
		return true;
	}
	
	/**
	 * Ouvre un nouveau system. Ferme le précédent si ouvert.
	 */
	public void newSystem(){
		this.ui.clearUI();			//on vide le contenu des interfaces
		this.moteur.saveSystem();	//on sauvegarde le systeme
		this.moteur = new Moteur();	//on créer un nouveau systeme
	}

	
	
	
	

	/**
	 * Demande au moteur de tester la valeur.
	 * Le mode de fonctionnement du moteur est récupèré dans les paramètres du menu, sous l'onglet "MODE".
	 * @param str_goals
	 * @return boolean, true si le test est concluant, false sinon.
	 */
	public boolean testGoals(List<Fait> liste_goals){
		//on vérifie les données testées.
		if(liste_goals==null)
			return false;
		
		boolean boole = false;
		
		//Selon le MODE choisi dans l'interface, on lance le test dans le moteur
		switch(this.ui.getSystemModeValue()){
			case "WIDEFORWARD" : boole=this.moteur.wideForward(liste_goals);break; 
			case "DEEPFORWARD" : boole= this.moteur.deepForward(liste_goals);break;
			case "BACKWARD" : boole=this.moteur.backward(liste_goals);break;
			default : boole=this.moteur.wideForward(liste_goals);break;
		}//switch end
		
		return boole;
		
		
	}//function end


	
	
	/**
	 * Gère l'ajout d'un NOUVEAU FAIT
	 */
	private void AddFactButtonPressed(){
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
		String new_Category = this.ui.panel_Add_Category.getText();//on récupère le nom de ltexte depuis l'interface
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
		//les prémisses
		String premisses = this.ui.panel_Add_Rule.getPremisses();
		//les conséquences
		String consequences = this.ui.panel_Add_Rule.getConsequences();	
		//verification des données.
		if(premisses==null || premisses.length()==0 || consequences==null || consequences.length()==0)
			return;
		
		//on envoie les éléments au controller
		String added_Rule = this.addNewRule(premisses,consequences);
		if(added_Rule==null || added_Rule.equals(""))
			{
			this.ui.panel_Display.addText_Panel("Erreur lors de l'ajout de la nouvelle règle.",PANEL.RESULT);
			}
		else{
			this.ui.panel_Display.addText_Panel(added_Rule, PANEL.RULE);
		}
		this.ui.panel_Display.setActivePanel(PANEL.RULE);
	}
	
	
	/**
	 * lancer le test pour les valeurs indiqué dna le champ input du module "panel_Input"
	 */
	private void TestButtonPressed(){
		String str_goals = this.ui.panel_Test.getInputText();
		//VERIFICATIONS
		if(str_goals == null || str_goals.trim().length()==0)
			return;
		
		
		String[] goals = this.toGoals_transcription(str_goals);		//la liste des goals à tester
		List<Fait> liste_goals = Fait.ArrayString_toFact(goals);	//on convertit en une liste de Fait.
		boolean res_test = this.testGoals(liste_goals);				//on teste les goals grâce au moteur.	
		
		
		/**
		 * AFFICHAGE DES RÉSULTATS DANS LE PANEL "RESULT"
		 */
		this.ui.panel_Display.updateText_Panel(PANEL.RESULT,"");
		this.ui.panel_Display.addText_Panel("\n------------------------------\n",PANEL.RESULT);
		
		//les goals.
		this.ui.panel_Display.addText_Panel("Mot(s) testé(s) : ",PANEL.RESULT);
		for(int i=0;i<liste_goals.size();i++)
			this.ui.panel_Display.addText_Panel_InLine(liste_goals.get(i).toString(), PANEL.RESULT);
		
		//le mode de fonctionnement.
		this.ui.panel_Display.addText_Panel("\nMode de fonctionnement de l'agorithme : "+this.getMotorMode()+"\n",PANEL.RESULT);
		
		//le résultat du test
		this.ui.panel_Display.addText_Panel("\nResultat : "+res_test+"\n",PANEL.RESULT);
		
		//les demandables
		this.ui.panel_Display.addText_Panel("\nDemandables : ",PANEL.RESULT);
		List<String> liste_demandables = this.moteur.getDemandables_ToListString();
		for(int i=0;i<liste_demandables.size();i++)
			this.ui.panel_Display.addText_Panel_InLine("; "+liste_demandables.get(i).toString(), PANEL.RESULT);
		
		//les faits
		this.ui.panel_Display.addText_Panel("\nFaits : ",PANEL.RESULT);
		List<String> liste_faits = this.moteur.getFacts_ToListString();
		for(int i=0;i<liste_faits.size();i++)
			this.ui.panel_Display.addText_Panel_InLine("; "+liste_faits.get(i).toString(), PANEL.RESULT);
		
		//les règles
		this.ui.panel_Display.addText_Panel("\nRègles : ",PANEL.RESULT);
		List<String> liste_regles = this.moteur.getRules_ToListString();
		for(int i=0;i<liste_regles.size();i++)
			this.ui.panel_Display.addText_Panel(liste_regles.get(i).toString(), PANEL.RESULT);
		
		//historique
		this.ui.panel_Display.addText_Panel("\nHisorique : ",PANEL.RESULT);
		List<String> liste_historique = this.moteur.getHistory_ToListString();
		for(int i=0;i<liste_historique.size();i++)
			this.ui.panel_Display.addText_Panel(liste_historique.get(i).toString(), PANEL.RESULT);
		
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
	 private String[] toGoals_transcription(String str_goals){
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
		this.moteur.saveSystem();
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
	
}
