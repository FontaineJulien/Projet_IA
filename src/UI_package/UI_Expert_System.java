package UI_package;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



public class UI_Expert_System extends JFrame implements ActionListener{
	private static final long serialVersionUID = 4257235605540370319L;
	
	//LES DIFFÉRENTS ÉLÉMENTS
	public UI_Display panel_Display;		//AFFCIHAGE
	public UI_Input panel_Test;			//TESTER MOT
	public UI_NewRule panel_Add_Rule;		//AJOUT REGLE
	public UI_NewFact panel_Add_Fact;		//AJOUT FAIT
	public UI_NewCategory panel_Add_Category;		//AJOUT CATEGORY
	public JSplitPane split_pane_Horiz_1;	//SEPARATEUR partie_gauche <-> partie_droite
	public JSplitPane split_pane_Vert_1;	//SEPARATEUR affichage <-> tester_mot
	public JSplitPane split_pane_Vert_2;	//SEPARATEUR ajout_regle <-> ajout_fait	
	//MENU BAR
	public UI_JMenuBar menuBar;
		    
	/**
	 * CONSTRUCTEUR
	 */
	public UI_Expert_System(){
		super("Système Expert");
		this.setUp_UI();		//Réglages interface
	  }
	
	/********************************************************
	 * MISE EN PLACE DES ELEMENTS GRAPHIQUES
	 */
		
	/**
	 * gère le redimensionnement de l'application
	 */
	private void addResizeSettings(){
		this.addComponentListener((new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent arg0) {}
			@Override
			public void componentResized(ComponentEvent arg0) {
				//GESTION DU REDIMENSIONNEMENT DE LA FENÊTRE.
				split_pane_Horiz_1.setDividerLocation(getWidth()-200);
				split_pane_Vert_1.setDividerLocation(getHeight()-100);
				split_pane_Vert_2.setDividerLocation(getHeight()-200);
				
			}
			@Override
			public void componentMoved(ComponentEvent arg0) {}
			@Override
			public void componentHidden(ComponentEvent arg0) {}
		}));
	}
	
	/**
	 * Réglages de la fenêtre principale
	 */
	private void setUp_UI(){
	    this.setAppTheme("METAL");							//on choisit le thème
	    this.setLocationRelativeTo(null);					//position de départ de l'app
	    this.setSize(800,600);								//taille par défaut
	    this.setResizable(true);							//redimensionnement : true
	    this.setMinimumSize(new Dimension(800,450));		//taille minimale : 800x600
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//fermeture par la croix

	    this.addMenu();				//on ajoute la barre de menu
	    this.addPanels();			//on ajoute les panneaux
	    this.addResizeSettings();	//on ajoute la gestion du redimensionnement
	    this.setVisible(true);
	}

	/**
	 * Instancie la barre de menu et l'ajoute à l'appli
	 */
	private void addMenu(){
	    this.menuBar = new UI_JMenuBar(this);//instanciation de la barre de menu
	    this.setJMenuBar(menuBar);//ajout de la barre de menu
	}
	
	/**
	 * Ajoute les panneaux dans la fenêtre
	 */
	private void addPanels(){
		//INITIALISATION DES ÉLÉMENTS
			//Left Group
			panel_Display = new UI_Display();
			panel_Test= new UI_Input();
			split_pane_Vert_1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,panel_Display,panel_Test);
			
			//Right Group			
			panel_Add_Fact = new UI_NewFact();
			panel_Add_Category = new UI_NewCategory();
			JPanel panel_Fact_and_Category = new JPanel();
				GridLayout gridLayout = new GridLayout();
				gridLayout.setColumns(1);
				gridLayout.setRows(2);
				panel_Fact_and_Category.setLayout(gridLayout);
				panel_Fact_and_Category.add(panel_Add_Fact);
				panel_Fact_and_Category.add(panel_Add_Category);
			
			
			panel_Add_Rule = new UI_NewRule();
			
			split_pane_Vert_2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,panel_Add_Rule,panel_Fact_and_Category);
			
			//on sépare les deux groupes verticaux par un séparateur Horizontale
			split_pane_Horiz_1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,split_pane_Vert_1,split_pane_Vert_2);
			
			//ON AJOUTE À LA FENÊTRE PRINCIPALE LES DEUX GROUPES SÉPARÉS PAR UN SPLIT_HORIZONTAL.
			this.getContentPane().add(split_pane_Horiz_1,BorderLayout.CENTER);

		//REGLAGES DES ELEMENTS
			//position par défaut des séparateurs.
			split_pane_Horiz_1.setDividerLocation(this.getWidth()-200);
			split_pane_Horiz_1.setOneTouchExpandable(true);
			split_pane_Vert_1.setDividerLocation(this.getHeight()-100);
			split_pane_Vert_1.setOneTouchExpandable(true);
			split_pane_Vert_2.setDividerLocation(this.getHeight()-200);
			split_pane_Vert_2.setOneTouchExpandable(true);
			
	}//fin addPanels
	
	
		
	/**
	 * remet à zéro toutes les modules de l'interface.
	 */
	public void clearUI(){
		this.panel_Add_Fact.getText();//a pour effet de vider la zone de texte
		this.panel_Add_Rule.getConsequences();//idem
		this.panel_Add_Rule.getPremisses();//idem
		this.panel_Display.clearPanels();
	}
	
	


	
	/**
	 * GESTION DES ÉVENNEMENTS.
	 * Connexion des éléments du menu avec les autres éléments graphiques
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {			
		//ITEMS MENU
			if(arg0.getSource()==this.menuBar.jrmi_AppTheme_Metal)//theme Metal 
				this.setAppTheme("METAL");
			if(arg0.getSource()==this.menuBar.jrmi_AppTheme_Nimbus)//theme Nimbus
				this.setAppTheme("NIMBUS");
			if(arg0.getSource()==this.menuBar.jrmi_AppTheme_GTK_Plus)//theme GTK+
				this.setAppTheme("GTK_PLUS");
			if(arg0.getSource()==this.menuBar.jrmi_AppTheme_CDE_Motif)//theme CDE/Motif
				this.setAppTheme("CDE/MOTIF");
			if(arg0.getSource()==this.menuBar.jrmi_PannelStyle_Oblivion)//style Oblivion 
				this.setPanelsStyle(Panneau.PANSTYLE.OBLIVION);
			if(arg0.getSource()==this.menuBar.jrmi_PannelStyle_Classic)//style classique
				this.setPanelsStyle(Panneau.PANSTYLE.CLASSIC);
			if(arg0.getSource()==this.menuBar.jrmi_PannelStyle_Cobalt)//style cobalt
				this.setPanelsStyle(Panneau.PANSTYLE.COBALT);
			if(arg0.getSource()==this.menuBar.jrmi_PannelFont_Small)//police small
				this.setPanelsFontSize(Panneau.FONTSIZE.SMALL);
			if(arg0.getSource()==this.menuBar.jrmi_PannelFont_Medium)//police medium
				this.setPanelsFontSize(Panneau.FONTSIZE.MEDIUM);
			if(arg0.getSource()==this.menuBar.jrmi_PannelFont_Large)//police Large
				this.setPanelsFontSize(Panneau.FONTSIZE.LARGE);
	}
	

	/**
	 * Modifie le theme de l'application.
	 * @param them
	 */
	private void setAppTheme(String them){
		String theme_path="";
		if(them==null)them="";
		switch(them){
		case "NIMBUS" : theme_path="javax.swing.plaf.nimbus.NimbusLookAndFeel";break;
		case "METAL" : theme_path="javax.swing.plaf.metal.MetalLookAndFeel";break;
		case "GTK_PLUS" : theme_path="com.sun.java.swing.plaf.gtk.GTKLookAndFeel";break;
		case "CDE/MOTIF" : theme_path="com.sun.java.swing.plaf.motif.MotifLookAndFeel";break;
		default : theme_path="javax.swing.plaf.nimbus.NimbusLookAndFeel";break;
		}
		try { 
			   UIManager.setLookAndFeel(theme_path); 
			   SwingUtilities.updateComponentTreeUI(this); 
			   //force chaque composant de la fenêtre à appeler sa méthode updateUI 
			} catch (InstantiationException e) { 
			} catch (ClassNotFoundException e) { 
			} catch (UnsupportedLookAndFeelException e) { 
			} catch (IllegalAccessException e) {}
	}//end function
	
	/**
	 * Modifie le style des panneaux du module central, panel_Display
	 * @param pan_style
	 */
	private void setPanelsStyle(Panneau.PANSTYLE pan_style){
		this.panel_Display.setPanelsStyle(pan_style);
	}
	
	/**
	 * Modifie la taille de police des panneaux du module central, panel_Display
	 * @param f_size
	 */
	private void setPanelsFontSize(Panneau.FONTSIZE f_size){
		this.panel_Display.setPanelsFontSize(f_size);
	}
	
	

	
	/**
	 * Renvoi le nom du mode, de fonctionnement du moteur, choisi dans la barre de menu.
	 * "BACKWARD" , "DEEPFORWARD" ou "WIDEFORWARD"
	 * @return
	 */
	public String getSystemModeValue(){
		if(this.menuBar.jrmi_Backward.isSelected())
			return "BACKWARD";
		else if(this.menuBar.jrmi_DeepForward.isSelected())
			return "DEEPFORWARD";
		else 
			return "WIDEFORWARD";
	}
	

	/***********************************************************************
	 * GETTERS & SETTERS
	 */
	
	/**
	 * Ajoute la chaine dans le panneau correspondant.
	 * @param str	-> la chaîne à ajouter
	 * @param panel	-> le panneau correspondant
	 */
	public void addText_ResultDisplay(String str, UI_Display.PANEL panel){
		this.panel_Display.addText_Panel(str,panel);
	}
	
	
	/**
	 * renvoie la chaîne du module Ajouter un fait.
	 * @return String -> la chaine correspondant au "FAIT" à ajouter.
	 */
	public String getText_NewFact(){
		return this.panel_Add_Fact.getText();
	}
	
	
	/**
	 * renvoie le menu Charger Systeme
	 * @return
	 */
	public final JMenuItem getMenuItemLoadSystem(){
		return this.menuBar.item_LoadSystem;
	}
	
	/**
	 * renvoie le menu Nouveau Syteme
	 * @return
	 */
	public final JMenuItem getMenuItemNewSystem(){
		return this.menuBar.item_NewSystem;
	}
	
	/**
	 * renvoie le menu Save
	 * @return
	 */
	public final JMenuItem getMenuItemSave(){
		return this.menuBar.item_Save;
	}
	/**
	 * renvoie le menu Save As
	 * @return
	 */
	public final JMenuItem getMenuItemSaveAs(){
		return this.menuBar.item_Save_As;
	}
	
	/**
	 * renvoie le menu Quitter
	 * @return
	 */
	public final JMenuItem getMenuItemQuitApp(){
		return this.menuBar.item_Quit;
	}
	
}//end CLASS