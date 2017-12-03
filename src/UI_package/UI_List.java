package UI_package;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

public class UI_List extends JPanel{
	private static final long serialVersionUID = 6855125328805338718L;
	
	private JList<String> jl_list_cat;
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UI_List(List<String> str_list, int ListSelectionModel){
		super();
		DefaultListModel default_List = new DefaultListModel();
		if(str_list!=null)
			for(String str : str_list){
				System.out.println("ajout");
				default_List.addElement(str);
			}
		this.jl_list_cat = new JList(default_List);
		this.jl_list_cat.setSelectionMode(ListSelectionModel);
		this.add(jl_list_cat);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UI_List(String str, int ListSelectionModel){
		super();
		DefaultListModel default_List = new DefaultListModel();
		
		default_List.addElement(str);
		
		this.jl_list_cat = new JList(default_List);
		this.jl_list_cat.setSelectionMode(ListSelectionModel);
		this.add(jl_list_cat);
	}
	
	/**
	 * Renvoyer la liste des éléments sélectionnés.
	 * @return
	 */
	public List<String> getSelectedElement(){
		return this.jl_list_cat.getSelectedValuesList();
	}
	
	/**
	 * met à jour la liste des éléments.
	 * @param list_str
	 */
	public void setListData(List<String> list_str){
		String[] liste;
		if(list_str!=null)
		{
			liste = new String[list_str.size()];
			for(int i=0;i<liste.length;i++)
			liste[i]=list_str.get(i);
		}
		else{
			liste = new String[0];
		}
		this.jl_list_cat.setListData(liste);
	}
}



