package Motor_package;

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
}
