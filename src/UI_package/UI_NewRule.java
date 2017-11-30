package UI_package;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class UI_NewRule extends JPanel {
	private static final long serialVersionUID = 6387578816629545185L;
	
	//le titre
	public JLabel jl_title;
	//les boutons
	public JButton jb_add;
	public JButton jb_del; //pour supprimer un règle chargée dans le panneau
	//les sous_titres
	public JLabel jl_premisse;
	public JLabel jl_consequence;
	//les textarea
	public JTextArea jta_premisse;
	public JTextArea jta_consequence;
	private JScrollPane scroll_premisse;
	private JScrollPane scroll_consequence;
	
	
		  
	/**
	 * CONSTRUCTEUR
	 */
	public UI_NewRule(){
		super();
		//on règle la fenêtre
		this.setUp_UI();
		this.setVisible(true);
	  }
	
	
	/**
	 * Réglages de la fenêtre principale
	 */
	private void setUp_UI(){
		//INTIALISATION DES OBJETS
		jl_title = new JLabel();
		
		jb_add = new JButton();
		jb_del = new JButton();
		
		jl_premisse = new JLabel();
		jl_consequence = new JLabel();

		jta_premisse = new JTextArea();
		jta_consequence = new JTextArea();
		scroll_premisse = new JScrollPane(jta_premisse);
		scroll_consequence = new JScrollPane(jta_consequence);
	
		//REGLAGES DES OBJETS
			//tableau prémisse
			jta_consequence.setBackground(Color.white);
			jta_consequence.setColumns(1);
			jta_consequence.setRows(2);
			//tableau conséquence
			jta_premisse.setBackground(Color.white);
			jta_premisse.setColumns(1);
			jta_premisse.setRows(2);
			//bordure des tableaux
			Border border = BorderFactory.createLineBorder(Color.BLACK);
			jta_consequence.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(10, 10, 10, 10)));
			jta_premisse.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(10, 10, 10, 10)));
				
			//le titre
			jl_title.setText(" la règle.");
			//les bouttons
			jb_add.setText("Ajouter");
			jb_del.setText("Effacer");
			//les titres des textarea
			jl_premisse.setText("Prémisse(s) :");
			jl_consequence.setText("Conséquence(s) :");
			//les tooltips infos pour les textarea
			jta_premisse.setToolTipText("<html>Ajouter les prémisses ici.<br/>" +
					"Exemple :<br/>" +
					"Galaxie<br/>" +
					"Science-Fiction</html>");
			jta_consequence.setToolTipText("<html>Ajouter les conséquences ici.<br/>" +
					"Exemple :<br/>" +
					"Star-wars<br/>" +
					"Jedi</html>");
		
		
		//DISPOSITION DES OBJETS
		//lAYOUT PRINCIPAL
		this.setLayout(new BorderLayout());
		
		//TITRE + BOUTONS
		JPanel panel_top = new JPanel();
		panel_top.setLayout(new BorderLayout());
			//TITRE
			panel_top.add(jl_title,BorderLayout.EAST);
			//BOUTONS
			JPanel panel_buttons = new JPanel();
			panel_buttons.setLayout(new BorderLayout());
			panel_top.add(panel_buttons,BorderLayout.CENTER);
				//ajout des boutons.
				panel_buttons.add(jb_add,BorderLayout.PAGE_START);
				panel_buttons.add(jb_del,BorderLayout.PAGE_END);
		
		//LES TABLEAUX
		JPanel tabs = new JPanel();
		tabs.setLayout(new GridLayout(2,1));
			//PREMISSE
			JPanel panel_premisse = new JPanel();
			panel_premisse.setLayout(new BorderLayout());
			//ajout element
			panel_premisse.add(jl_premisse,BorderLayout.NORTH);
			panel_premisse.add(scroll_premisse,BorderLayout.CENTER);
			tabs.add(panel_premisse);
			
			//CONSÉQUENCE
			JPanel panel_cons = new JPanel();
			panel_cons.setLayout(new BorderLayout());
			//ajout élément
			panel_cons.add(jl_consequence,BorderLayout.NORTH);
			panel_cons.add(scroll_consequence,BorderLayout.CENTER);
			tabs.add(panel_cons);
			
		
		//ON AJOUTE LES PANNELS SECONDAIRES AU PRINCIPAL
		this.add(panel_top,BorderLayout.NORTH);//PANEL DU HAUT
		this.add(tabs,BorderLayout.CENTER);//PANEL TABLEAUX
		
	}//setUp_UI end
	
	
	/**
	 * renvoie le bouton add de ce module.
	 */
	public final JButton getButton_Add(){
		return this.jb_add;
	}
	
	/**
	 * renvoie le bouton del de ce module.
	 */
	public final JButton getButton_Del(){
		return this.jb_del;
	}
	
	/**
	 * renvoie le contenu de la zone de texte des prémisses.
	 * et efface son contenu après
	 * @return
	 */
	public String getPremisses(){
		String premisses = this.jta_premisse.getText();
		this.jta_premisse.setText("");//efface le contenu du textarea
		return premisses;
	}
	/**
	 * renvoie le contenu de la zone de texte des conséquences.
	 * et efface son contenu après
	 * @return
	 */
	public String getConsequences(){
		String consequences = this.jta_consequence.getText();
		this.jta_consequence.setText("");//efface le contenu du textarea
		return consequences;
	}
	
	/**
	 * efface le contenu des TextArea des premisses et des consequences.
	 */
	public void clearAllTexts(){
		this.jta_premisse.setText("");
		this.jta_consequence.setText("");
	}
	
	
}
