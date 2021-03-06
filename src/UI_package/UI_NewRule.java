package UI_package;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

public class UI_NewRule extends JPanel{
	private static final long serialVersionUID = 6387578816629545185L;
	
	//Layout principal
	private GridLayout grid_Layout;
	//le titre
	public JLabel jl_title;
	//la catégorie
	private JLabel jl_category; 
	private UI_List list_category;
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
		
		list_category = new UI_List("Aucune",ListSelectionModel.SINGLE_SELECTION);
		jl_category = new JLabel("Catégorie");
		
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
			jl_title.setText("<html><h3>Règle :</h3></html>");
			jl_title.setHorizontalAlignment(JLabel.CENTER);
			
			//la catégorie
			jl_category.setVerticalAlignment(JLabel.CENTER);
			
			
			
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
		
			//les bouttons
			jb_add.setText("Ajouter");
			jb_del.setText("Effacer");
		
		//DISPOSITION DES OBJETS
		//lAYOUT PRINCIPAL
		this.grid_Layout = new GridLayout();//le layout manager principal
		this.grid_Layout.setColumns(1);		//1 colonne
		this.grid_Layout.setRows(4);		//4 lignes
		this.setLayout(new BorderLayout());
		
		//LA CATEGORY
		JPanel panel_category = new JPanel();
		panel_category.setLayout(new BorderLayout());
		JScrollPane scroll_list_cat = new JScrollPane(list_category);
		panel_category.add(this.jl_category,BorderLayout.NORTH);
		panel_category.add(scroll_list_cat,BorderLayout.CENTER);
		
		//BORDERLAYOUT NORTH
		JPanel panel_North = new JPanel();
		panel_North.setLayout(new BorderLayout());
		panel_North.add(jl_title,BorderLayout.NORTH);
		panel_North.add(panel_category,BorderLayout.CENTER);
		
		//LES TABLEAUX
		JPanel panel_regle = new JPanel();
		panel_regle.setLayout(new GridLayout(2,1));
			//PREMISSE
			JPanel panel_premisse = new JPanel();
			panel_premisse.setLayout(new BorderLayout());
			//ajout element
			panel_premisse.add(jl_premisse,BorderLayout.NORTH);
			panel_premisse.add(scroll_premisse,BorderLayout.CENTER);
			//CONSÉQUENCE
			JPanel panel_cons = new JPanel();
			panel_cons.setLayout(new BorderLayout());
			//ajout élément
			panel_cons.add(jl_consequence,BorderLayout.NORTH);
			panel_cons.add(scroll_consequence,BorderLayout.CENTER);
		panel_regle.add(panel_premisse);
		panel_regle.add(panel_cons);
			
		
		
		//ON AJOUTE LES PANNELS SECONDAIRES AU PRINCIPAL
		

		JSplitPane split_pane_Vert_1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,panel_North,panel_regle);
		split_pane_Vert_1.setDividerLocation(150);
		
		this.add(split_pane_Vert_1,BorderLayout.CENTER);
		this.add(jb_add,BorderLayout.SOUTH);
		
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
	 * Renvoyer la catégorie sélectionnée dans la liste
	 * @return
	 */
	public String getCategory(){
		if(this.list_category.getSelectedElement().size()==0)
			return null;
		return this.list_category.getSelectedElement().get(0);
	}
	
	/**
	 * Mettre à jour la liste des catégories
	 * @param list_cat
	 */
	public void updateCategoryList(List<String> list_cat){
		this.list_category.setListData(list_cat);
	}
	
	/**
	 * efface le contenu des TextArea des premisses et des consequences.
	 */
	public void clearAll(){
		this.jta_premisse.setText("");
		this.jta_consequence.setText("");
		this.list_category.setListData(null);
	}
	
	
	
}
