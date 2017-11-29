package UI_package;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class UI_Input extends JPanel{
  private static final long serialVersionUID = 2566506046782345335L;
  
  public JTextArea jta_input = new JTextArea();
  protected JScrollPane scroll = new JScrollPane(jta_input);
  public JButton jb_enter = new JButton("TESTER");	
  public Color font_color = Color.black;
  public Color background_Color = Color.white;
  public int font_size = 15;
  public Font le_font = new Font("Arial", Font.BOLD, font_size);
  
  /**
   * constructeur
   * @param BGColor
   * @param FontColor
   */
  public UI_Input(){
	super();
	this.setUpUi();
  }
  
  /**
   * reglage de l'interface
   */
  private void setUpUi(){
	  this.setLayout(new BorderLayout());
	  this.add(scroll,BorderLayout.CENTER);
	  this.add(jb_enter,BorderLayout.EAST);
	  
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
}