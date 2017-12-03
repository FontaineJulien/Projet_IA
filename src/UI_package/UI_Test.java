package UI_package;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

public class UI_Test extends JPanel{
  private static final long serialVersionUID = 2566506046782345335L;
  
  public JTextArea jta_input = new JTextArea();
  protected JScrollPane scroll = new JScrollPane(jta_input);
  public JButton jb_enter = new JButton("TESTER");
  protected UI_List list_category = new UI_List("toutes", ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
  private JScrollPane scroll_cat = new JScrollPane(list_category);
  protected JLabel label_category = new JLabel("Catégorie(s)");
  public Color font_color = Color.black;
  public Color background_Color = Color.white;
  public int font_size = 15;
  public Font le_font = new Font("Arial", Font.BOLD, font_size);
  
  /**
   * constructeur
   * @param BGColor
   * @param FontColor
   */
  public UI_Test(){
	super();
	this.setUpUi();
  }
  
  /**
   * reglage de l'interface
   */
  private void setUpUi(){
	  GridLayout gridLayout = new GridLayout();
	  gridLayout.setColumns(4);
	  gridLayout.setRows(1);
	  this.setLayout(new BorderLayout());
	  
	  JPanel panel_east = new JPanel();
	  panel_east.setLayout(new BorderLayout());
	  panel_east.add(jb_enter,BorderLayout.EAST);
	  panel_east.add(scroll_cat,BorderLayout.CENTER);
	  panel_east.add(label_category,BorderLayout.WEST);
	  
	  

	  JSplitPane split_pane_Horiz_1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,scroll,panel_east);
	  
	  split_pane_Horiz_1.setDividerLocation(200);
	  
	  this.add(split_pane_Horiz_1,BorderLayout.CENTER);
	  
	  jta_input.setToolTipText("Pour tester plusieurs termes : terme1 & terme2 & terme3 ...");
	  
	  
	  
	  this.settingColor();
	  this.setVisible(true);
  }
  
  public void settingColor(){
    this.jta_input.setBackground(this.background_Color);
    this.jta_input.setForeground(font_color);
    this.jta_input.setFont(le_font);
  }
  
  /**
   * Modifie le texte du JPanel.
   * @param text
   */
  public void resetText(){
	  this.jta_input.setText("");
	  repaint();
  }
  
  /**
   * renvoie la valeur du text.
   * @return
   */
  public String getInputText(){
	  return this.jta_input.getText();
  }
  
  /**
   * Modifie la couleur de la police.
   * @param color
   */
  public void setFontColor(Color color){
	  this.font_color=color;
  }
  
  /**
   * Modifie la couleur du fond du Panneau. 
   * @param color
   */
  public void setBackgroundColor(Color color){
	  this.background_Color=color;
  }
  
  /**
   * modifie la taille de la police.
   * @param size
   */
  public void setFontSize(int size){
	  this.font_size=size;
  }
  
  
  /**
   * renvoie le bouton entrer "TESTER";
   * @return
   */
  public final JButton getButton_Enter(){
	  return this.jb_enter;
  }
  
  /**
   * Recharge la liste avec la liste de catégories "list_cat"
   * @param list_cat
   */
  public void updateCategoryList(List<String> list_cat){
	  this.list_category.setListData(list_cat);
  }
  
  /**
   * Renvoyer la liste des éléments sélectionnés dans la liste.
   * Renvoie une liste contenant 1 seul élément,("toutes"), si la sélection est vide.
   * @return
   */
  public List<String> getCategoryList(){
	  List<String> tmp =this.list_category.getSelectedElement(); 
	  if(tmp.size()==0)
		  {tmp = new ArrayList<String>();tmp.add("toutes");}
	  return tmp;
  }
  
  /**
   * Effacer le contenu des éléments du module.
   */
  public void clearAll(){
	  this.list_category.setListData(null);
  }
   
}