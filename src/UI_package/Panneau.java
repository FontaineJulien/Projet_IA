package UI_package;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

public class Panneau extends JTextArea{
  private static final long serialVersionUID = -6117897819071235530L;
  
  public enum PANSTYLE {OBLIVION,CLASSIC,COBALT}
  public enum FONTSIZE {SMALL,MEDIUM,LARGE}
  
  private String font_family = "Arial";
  private int font_weight = Font.PLAIN;
  
  private List<Panel_Style> les_PANSTYLEs = new ArrayList<Panel_Style>();
  
  
  private int font_size = 15;
  
  
  public Panneau(Color BGColor, Color FontColor){
    this.setVisible(true);
    this.setEditable(false);
    this.setBackground(BGColor);
    this.setMaximumSize(new Dimension(500,500));
    this.initPANSTYLEs();
	this.setFont(new Font("Arial", Font.PLAIN, font_size));
  }
    
  /**
   * initialisation des PANSTYLEs disponibles pour le Panneau
   */
  private void initPANSTYLEs(){
	  this.les_PANSTYLEs.add(new Panel_Style(new Color(114,159,207),new Color(46,52,54)));//OBLIVION
	  this.les_PANSTYLEs.add(new Panel_Style(new Color(0,0,0),new Color(255,255,255)));//CLASSIC
	  this.les_PANSTYLEs.add(new Panel_Style(new Color(255,255,255),new Color(0,33,63)));//COBALT
  }
  
  /**
   * Ajoute un text à la ligne.
   * @param text
   */
  public void addText(String text){
	  this.append(text+"\n");
  }
  /**
   * Ajoute un text à la fin de la ligne courante.
   * @param text
   */
  public void addTextInLine(String text){
	  this.append(text);
  }
  
  /**
   * Met à jour le text en cas de changement de PANSTYLE.
   */
  public void updateText(){
	  this.setText(""+this.getText());
  }
  
    
  
  /**
   * modifie la taille de la police.
   * @param size
   */
  public void setFontSize(int size){
	  if(size>1 && size<100)
	  this.font_size=size;
  }
  
  /**
   * Augmente la police du panel
   * @return
   */
  public int getFontSize(){
	  return this.font_size;
  }
  
  
  /**
   * Modifie le PANSTYLE du panneau
   * @param pan_PANSTYLE
   */
  public void setPanStyle(Panneau.PANSTYLE pan_PANSTYLE){
	  int numstyle=0;
	  if(pan_PANSTYLE==null)pan_PANSTYLE=Panneau.PANSTYLE.CLASSIC;
		switch(pan_PANSTYLE){
			case OBLIVION:
				System.out.println("OBLIVION");
				numstyle=0;break;
			case CLASSIC:
				System.out.println("CLASSIC");
				numstyle=1;break;
			case COBALT:
				System.out.println("COBALT");
				numstyle=2;
				break;
			default:
				System.out.println("DEFAUT");
				numstyle=1;
				break;
		}
		//on applique le style
		setForeground(this.les_PANSTYLEs.get(numstyle).font_color);
		setBackground(this.les_PANSTYLEs.get(numstyle).background_color);
		//on met à jour le texte
		this.updateText();
  }
  
  /**
   * Met à jour la taille de la police
   * @param f_size
   */
  public void setFontSize(FONTSIZE f_size){
	  switch(f_size){
		  case SMALL: this.setFontSize(10);break;
		  case MEDIUM: this.setFontSize(15);break;
		  case LARGE: this.setFontSize(20);break;
		  default: this.setFontSize(10);break;
	  }
	  this.setFont(new Font(font_family, font_weight, font_size));
	  this.updateText();
  }
  
  /**
   * Reinitialise les paramretres de styles de couleur et de police
   */
  public void clearStyle(){
	  this.setFont(new Font("Arial",Font.PLAIN,15));
	  this.setForeground(Color.white);
	  this.setBackground(Color.white);
	  this.updateText();
  }

	
}