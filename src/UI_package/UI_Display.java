package UI_package;

import java.awt.Color;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class UI_Display extends JInternalFrame {
	private static final long serialVersionUID = 6715722470729366308L;
	
  public JTabbedPane onglet;
  public Panneau[] tPan;
  
  public JScrollPane[] scrollTab;
  
  
  public static enum PANEL { RESULT, RULE, FACT, CATEGORY };
  
  /**
   * CONSTRUCTEUR
   */
  public UI_Display(){
    this.setSize(400, 200);
    
     
    //Création des panneaux
    this.tPan = new Panneau[4];
    this.scrollTab = new JScrollPane[4];
    for(int i=0;i<tPan.length;i++){
        this.tPan[i] = new Panneau(Color.white,Color.black);
        this.scrollTab[i] = new JScrollPane(tPan[i]);
    }
    
      
    //Création de notre conteneur d'onglets
    onglet = new JTabbedPane();
    //on met les onglets en bas du JInternalFrame
    onglet.setTabPlacement(JTabbedPane.BOTTOM);
    
    this.setResizable(true);
    
    //Méthode d'ajout d'onglet
	onglet.add("Résultats", scrollTab[0]);
	onglet.add("Règles", scrollTab[1]);
	onglet.add("Faits", scrollTab[2]);
	onglet.add("Catégories",scrollTab[3]);
  
    //On passe ensuite les onglets au content pane
    this.add(onglet);
    this.setVisible(true);
   
  }
  
  
  /**************************************************
   * METHODES
   */
    
  /**
   * ajoute une chaine de caractere à la ligne dans le panneau "panel". 
   * PANEL.RESULT,PANEL.FACT,PANEL.RULE
   * @param str
   */
  public void addText_Panel(String str, PANEL panel){
	  switch(panel){
		  case RESULT : 
			  this.tPan[0].addText(str);
			  break;
		  case RULE : 
			  this.tPan[1].addText(str);
			  break;
		  case FACT : 
			  this.tPan[2].addText(str);
			  break;
		  case CATEGORY : 
			  this.tPan[3].addText(str);
			  break;
		  default: 
			  
			  break;
	  }//end switch
  }//end function
  
  
  public void addText_Panel_InLine(String str,PANEL panel){
	  switch(panel){
		  case RESULT : 
			  this.tPan[0].addTextInLine(str);
			  break;
		  case RULE : 
			  this.tPan[1].addTextInLine(str);
			  break;
		  case FACT : 
			  this.tPan[2].addTextInLine(str);
			  break;
		  case CATEGORY : 
			  this.tPan[3].addTextInLine(str);
			  break;
		  default: 
			  
			  break;
	  }//end switch
	}//end function
  
  /**
   * met à jour le panneau des faits
   * @param listeFacts
   */
  public void updateText_PanelFACT(String to_add){
		 tPan[2].addText(to_add);
  }
  
  
  /**
   * met à jour le panneau des règles
   * @param listeRules
   */
  public void updateText_PanelRULE(List<String> listeRules){
	  this.tPan[1].setText("");
	  for(int i=0; i<listeRules.size();i++){
		 tPan[1].addText(listeRules.get(i));
		 }
  }
  
  /**
   * update le panel choisi avec le text fourni
   * @param panel
   */
  public void updateText_Panel(String txt,PANEL panel){
	  switch(panel){
	  case RESULT:this.tPan[0].setText(txt);break;
	  case RULE:this.tPan[1].setText(txt);break;
	  case FACT:this.tPan[2].setText(txt);break;
	  case CATEGORY:this.tPan[3].setText(txt);break;
	  default:break;
	  }
  }
  
  /**
   * Update le 'panel' choisi avec la liste de String 'str_list'.
   * @param str_list -> la liste de String
   * @param panel -> le panel choisi
   */
  public void updateText_Panel(List<String> str_list, PANEL panel){
	  int tPan_ind = 0;
	  switch(panel){
	  	case RESULT:tPan_ind=0;break;
	  	case RULE:tPan_ind=1;break;
	  	case FACT:tPan_ind=2;break;
	  	case CATEGORY:tPan_ind=3;break;
	  	default:break;
	  }
	  this.tPan[tPan_ind].setText("");
	  for(String str : str_list)
		  this.tPan[tPan_ind].addText(str);
  }
  /**
   * Choisir le panel à afficher. RESULT, RULE ou FACT.
   * @param panel
   */
  public void setActivePanel(PANEL panel){
	  switch(panel){
	  case RESULT:this.onglet.setSelectedIndex(0);break;
	  case RULE:this.onglet.setSelectedIndex(1);break;
	  case FACT:this.onglet.setSelectedIndex(2);break;
	  case CATEGORY:this.onglet.setSelectedIndex(3);break;
	  default:break;
	  }
  }
  
  /**
   * efface le contenu des pannels
   */
  public void clearPanels(){
	  for(Panneau pan : tPan){
		  pan.setText("");
	  }
  }
  
  /**
   * Modifie le style du panel actif
   * @param pan_style
   */
  public void setPanelsStyle(Panneau.PANSTYLE pan_style){
	  for(Panneau pan : tPan){
		  if(pan.isShowing())
			  pan.setPanStyle(pan_style);
	  }
  }
  
  /**
   * Modifie la taille de la police du pannel actif.
   * @param f_size
   */
  public void setPanelsFontSize(Panneau.FONTSIZE f_size){
	  for(Panneau pan : tPan){
		  if(pan.isShowing())
			  pan.setFontSize(f_size);
	  }
  }

  /**
   * Augmente la police du pannel affiché
   */
  public void increaseFontSize(){
	  for(Panneau pan : tPan){
		  if(pan.isShowing())
			  pan.setFontSize(pan.getFontSize()+1);
	  }
  }
  
  /**
   * Réduit la police du pannel affiché
   */
  public void decreaseFontSize(){
	  for(Panneau pan : tPan){
		  if(pan.isShowing())
			  pan.setFontSize(pan.getFontSize()-1);
	  }
  }
  
}//end CLASS