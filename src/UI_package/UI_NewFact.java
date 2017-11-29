package UI_package;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class UI_NewFact extends JPanel {
	private static final long serialVersionUID = -3973295406702553069L;
	
	private JLabel jl_title;
	
	public JTextField jl_fact;
	public JScrollPane scroll;
	public JButton jb_add;
	
	/**
	 * Constructeur
	 */
	public UI_NewFact(){
		super();
		this.setUp_UI();
	}

	/**
	 * reglages interface
	 */
	private void setUp_UI() {
		//lAYOUT POUR LE PANEL
		this.setLayout(new BorderLayout());
		
		//LES ÉLÉMENTS
		jl_title = new JLabel("<html><h3>Ajouter un fait :</h3></html>");//me titre
		jl_title.setHorizontalAlignment(JLabel.CENTER);
		jl_fact = new JTextField();//le champ input
			jl_fact.setEditable(true);
			jl_fact.setHorizontalAlignment(JLabel.CENTER);
		scroll = new JScrollPane(jl_fact);
		jb_add = new JButton("Ajouter");//le bouton ajouter
		
		//AJOUT DES ELEMENTS DANS LE PANEL
		this.add(jl_title,BorderLayout.NORTH);//ajout en haut
		this.add(scroll,BorderLayout.CENTER);//ajout au centre
		this.add(jb_add,BorderLayout.EAST);//ajout à droite
	}
	

	/**
	 * renvoie la chaine du label de ce module, et vide le label
	 * @return String -> la chaîne du label
	 */
	public String getText_NewFact(){
		String val = this.jl_fact.getText();
		this.jl_fact.setText("");
		return val;
	}
	
	
	
	
	/**
	 * renvoie le bouton add du module
	 * @return
	 */
	public final JButton getButton_Add(){
		return this.jb_add;
	}
}
