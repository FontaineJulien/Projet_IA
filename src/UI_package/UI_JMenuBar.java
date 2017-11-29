package UI_package;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;



public class UI_JMenuBar extends JMenuBar{
	private static final long serialVersionUID = -1909313204182057432L;
	
	public final String DEEPFORWARD = "Avant en profondeur";
	public final String WIDEFORWARD = "Avant en largeur";
	public final String BACKWARD = "Arrière";
	
	
	//Les menus
	  protected  JMenu menu_File;
	  protected  JMenu menu_Mode;
	  protected  JMenu menu_Preferences;
	  
	//menu fichier
	  protected  JMenuItem item_NewSystem;
	  protected  JMenuItem item_LoadSystem;
	  protected  JMenuItem item_Save;
	  protected  JMenuItem item_Save_As;
	  protected  JMenuItem item_Quit;			  
	  
	//menu Mode
	  protected  ButtonGroup bg_Mode;
	  protected  JRadioButtonMenuItem jrmi_WideForward;
	  protected  JRadioButtonMenuItem jrmi_DeepForward;
	  protected  JRadioButtonMenuItem jrmi_Backward;
	  
	//menu Systeme
	  protected  JMenuItem item_Run;
	  
	//menu Preferences
	  protected  JMenu SMenu_AppTheme;
		  protected  ButtonGroup bg_AppTheme;
		  protected  JRadioButtonMenuItem jrmi_AppTheme_Metal;
		  protected  JRadioButtonMenuItem jrmi_AppTheme_Nimbus;
		  protected  JRadioButtonMenuItem jrmi_AppTheme_CDE_Motif;
		  protected  JRadioButtonMenuItem jrmi_AppTheme_GTK_Plus;
		  
	  protected  JMenu SMenu_Pannel_Style;
		  protected  ButtonGroup bg_PannelStyle;
		  protected  JRadioButtonMenuItem jrmi_PannelStyle_Oblivion;
		  protected  JRadioButtonMenuItem jrmi_PannelStyle_Classic;
		  protected  JRadioButtonMenuItem jrmi_PannelStyle_Cobalt;
		  
	  protected  JMenu SMenu_Pannel_FontSize;
		  protected  ButtonGroup bg_PannelFontSize;
		  protected  JRadioButtonMenuItem jrmi_PannelFont_Small;
		  protected  JRadioButtonMenuItem jrmi_PannelFont_Medium;
		  protected  JRadioButtonMenuItem jrmi_PannelFont_Large;

			  
	/*****************************************************
	 * CONSTRUCTEUR	  
	 */
	public UI_JMenuBar(UI_Expert_System ui_expert_system){
		super();
		this.configureMenuBar();
		this.setActionListener(ui_expert_system);
	}
	
	/*****************************************************
	 * METHODES
	 */

	/**
	 * Configure la MenuBar
	 */
	private void configureMenuBar(){
		//INITIALISATION DES ÉLÉMENTS
			//Les menus
			  menu_File = new JMenu("Fichier");
			  menu_Mode = new JMenu("Mode");
			  menu_Preferences = new JMenu("Préférences");
			//les items
			  item_NewSystem = new JMenuItem("Nouveau Système");
			  item_LoadSystem = new JMenuItem("Charger un système");
			  item_Save = new JMenuItem("Enregistrer");
			  item_Save_As = new JMenuItem("Enregistrer sous...");
			  item_Quit = new JMenuItem("Fermer");
			  
			  item_Run = new JMenuItem("Tester");
			  
			  SMenu_AppTheme = new JMenu("Thèmes");
			  
			  //groupe pour les bouttons du MODE
			  bg_Mode= new ButtonGroup();
			  jrmi_WideForward = new JRadioButtonMenuItem(WIDEFORWARD);
			  jrmi_DeepForward = new JRadioButtonMenuItem(DEEPFORWARD);
			  jrmi_Backward = new JRadioButtonMenuItem(BACKWARD);
			  //activation du mode WideForward par defaut
			  jrmi_WideForward.setSelected(true);
			  
			  
			  //groupe lié au Styles : theme de l'application, Styles des panel display, taille de police
			  bg_AppTheme = new ButtonGroup();
			  jrmi_AppTheme_Metal = new JRadioButtonMenuItem("Metal");
			  jrmi_AppTheme_Metal.setSelected(true);
			  jrmi_AppTheme_Nimbus = new JRadioButtonMenuItem("Nimbus");
			  jrmi_AppTheme_CDE_Motif = new JRadioButtonMenuItem("CDE/Motif");
			  jrmi_AppTheme_GTK_Plus = new JRadioButtonMenuItem("GTK+");
			  
			  SMenu_Pannel_Style = new JMenu("Style Affichage");
			  bg_PannelStyle = new ButtonGroup();
			  jrmi_PannelStyle_Oblivion = new JRadioButtonMenuItem("Oblivion");
			  jrmi_PannelStyle_Classic = new JRadioButtonMenuItem("Classic");
			  jrmi_PannelStyle_Classic.setSelected(true);
			  jrmi_PannelStyle_Cobalt = new JRadioButtonMenuItem("Cobalt");
			  
			  SMenu_Pannel_FontSize = new JMenu("Taille Police");
			  bg_PannelFontSize = new ButtonGroup();
			  jrmi_PannelFont_Small = new JRadioButtonMenuItem("Petit");
			  jrmi_PannelFont_Medium = new JRadioButtonMenuItem("Normal");
			  jrmi_PannelFont_Medium.setSelected(true);
			  jrmi_PannelFont_Large = new JRadioButtonMenuItem("Grand");
			  
		//DISPOSITION DES ÉLÉMENTS
			  //MENU FICHIER
			  menu_File.add(item_NewSystem);
			  menu_File.add(item_LoadSystem);
			  menu_File.addSeparator();
			  menu_File.add(item_Save);
			  menu_File.add(item_Save_As);
			  menu_File.addSeparator();
			  menu_File.add(item_Quit);
			  
			  //MENU MODE
			  bg_Mode.add(jrmi_DeepForward);
			  bg_Mode.add(jrmi_WideForward);
			  bg_Mode.add(jrmi_Backward);
			  menu_Mode.add(jrmi_DeepForward);
			  menu_Mode.add(jrmi_WideForward);
			  menu_Mode.add(jrmi_Backward);
			  
			  
			  //MENU PREFERENCES
			  //on ajoute les elements du groupe AppTheme
			  bg_AppTheme.add(jrmi_AppTheme_Metal);
			  bg_AppTheme.add(jrmi_AppTheme_CDE_Motif);
			  bg_AppTheme.add(jrmi_AppTheme_GTK_Plus);
			  bg_AppTheme.add(jrmi_AppTheme_Nimbus);
			  
			  SMenu_AppTheme.add(jrmi_AppTheme_Metal);
			  SMenu_AppTheme.add(jrmi_AppTheme_CDE_Motif);
			  SMenu_AppTheme.add(jrmi_AppTheme_GTK_Plus);
			  SMenu_AppTheme.add(jrmi_AppTheme_Nimbus);
			  
			  menu_Preferences.add(SMenu_AppTheme);
			  
			  //on ajoute les elements du groupe PannelStyle
			  bg_PannelStyle.add(jrmi_PannelStyle_Classic);
			  bg_PannelStyle.add(jrmi_PannelStyle_Oblivion);
			  bg_PannelStyle.add(jrmi_PannelStyle_Cobalt);
			  
			  SMenu_Pannel_Style.add(jrmi_PannelStyle_Classic);
			  SMenu_Pannel_Style.add(jrmi_PannelStyle_Cobalt);
			  SMenu_Pannel_Style.add(jrmi_PannelStyle_Oblivion);
			  
			  menu_Preferences.add(SMenu_Pannel_Style);
			  
			  //on ajoute les elements du groupe pannelFont_size
			  bg_PannelFontSize.add(jrmi_PannelFont_Small);
			  bg_PannelFontSize.add(jrmi_PannelFont_Medium);
			  bg_PannelFontSize.add(jrmi_PannelFont_Large);
			  
			  SMenu_Pannel_FontSize.add(jrmi_PannelFont_Small);
			  SMenu_Pannel_FontSize.add(jrmi_PannelFont_Medium);
			  SMenu_Pannel_FontSize.add(jrmi_PannelFont_Large);
			  
			  menu_Preferences.add(SMenu_Pannel_FontSize);
			  
			  
		//AJOUT DES MENUS DANS LA BARRE DE MENU			  
			  this.add(menu_File);
			  this.add(menu_Mode);
			  this.add(menu_Preferences);
			  
	}//fin addMenuBar
	
	
	/**
	 * Ajoute les listeners sur les éléments de la barre de Menu.
	 * 
	 */
	public void setActionListener(UI_Expert_System ui_expert_system){
		//connexion des éléments du menu
		this.item_Quit.addActionListener(ui_expert_system);
		this.item_LoadSystem.addActionListener(ui_expert_system);
		this.item_NewSystem.addActionListener(ui_expert_system);
		this.item_Run.addActionListener(ui_expert_system);
		//les modes de fonctionnement du moteur
		this.jrmi_Backward.addActionListener(ui_expert_system);
		this.jrmi_WideForward.addActionListener(ui_expert_system);
		this.jrmi_DeepForward.addActionListener(ui_expert_system);
		//les themes de l'application
		this.jrmi_AppTheme_CDE_Motif.addActionListener(ui_expert_system);
		this.jrmi_AppTheme_GTK_Plus.addActionListener(ui_expert_system);
		this.jrmi_AppTheme_Metal.addActionListener(ui_expert_system);
		this.jrmi_AppTheme_Nimbus.addActionListener(ui_expert_system);
		//les styles d'affichage des panneaux
		this.jrmi_PannelStyle_Classic.addActionListener(ui_expert_system);
		this.jrmi_PannelStyle_Cobalt.addActionListener(ui_expert_system);
		this.jrmi_PannelStyle_Oblivion.addActionListener(ui_expert_system);
		//les tailles de police des panneaux
		jrmi_PannelFont_Small.addActionListener(ui_expert_system);
		jrmi_PannelFont_Medium.addActionListener(ui_expert_system);
		jrmi_PannelFont_Large.addActionListener(ui_expert_system);
	}
	
	
	
	/**********************************************************************
	 * GETTERS & SETTERS
	 */
	/**
	 * renvoie la chaine du mode selectionné.
	 */
	public final String getMotorMode(){
		if(this.jrmi_Backward.isSelected())
			return BACKWARD;
		if(this.jrmi_DeepForward.isSelected())
			return DEEPFORWARD;
		if(this.jrmi_WideForward.isSelected())
			return WIDEFORWARD;
		return null;
	}
	
}