import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



/*
 * Divers methodes de traitement pouvant être appelée par les différentes classes
 */
public class Util {
	
	/*
	 * Permet de convertir les Strings d'une liste en petits caractères 
	 */
	static public List<String> toLowerCaseList(List<String> list){
		ArrayList<String> lc_list = new ArrayList<String>();
		
		Iterator<String> it_list = list.iterator();
		while(it_list.hasNext()){
			lc_list.add(it_list.next().toLowerCase());
		}
		
		return lc_list;
	}

}
