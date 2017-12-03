package Motor_package;

import java.util.ArrayList;
import java.util.List;

public class Categorie {
	
	private String categorie;
	
	public Categorie(String categorie){
		this.setCategorie(categorie.toLowerCase());
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	
	/**
	 * Renvoyer une List de catégorie à partir d'une list de String
	 * @param liste
	 * @return 
	 */
	public static List<Categorie> listString_toCategorie(List<String> liste){
		try{
			List<Categorie> list_Categorie = new ArrayList<Categorie>();
			for(String str : liste)
				list_Categorie.add(new Categorie(str));
			return list_Categorie;
		}
		catch(Exception e){
			return null;
		}
	}
}
