package Motor_package;

import java.util.Iterator;
import java.util.List;

/*
 * Permet de stocker une règles, càd ses antécédents et les conséquences
 */

public class Rule {

    private List<Fait> antecedents;
    private List<Fait> consequences;
    private Categorie categorie;

    public Rule( List<Fait> antecedents, List<Fait> consequences, Categorie categorie ) {

        this.antecedents = antecedents;
        this.consequences = consequences;
        this.categorie = categorie;
    }

    

    /**
     * Renvoyer une Regle sous forme d'une chaine.
     */
    public String toString() {
        String s = "";
        Iterator<Fait> it_antecedents = antecedents.iterator();
        Iterator<Fait> it_consequences = consequences.iterator();

        if ( it_antecedents.hasNext() )
            s += it_antecedents.next().toString();
        while ( it_antecedents.hasNext() ) {
            s += " & " + it_antecedents.next().toString();
        }
        if ( it_consequences.hasNext() )
            s += " => " + it_consequences.next().toString();
        while ( it_consequences.hasNext() ) {
            s += " & " + it_consequences.next().toString();
        }
        return s;
    }

    public Iterator<Fait> iteratorAntecedents() {
        return antecedents.iterator();
    }

    public Iterator<Fait> iteratorConsequences() {
        return consequences.iterator();
    }

    public List<Fait> getAntecedents() {
        return antecedents;
    }

    public List<Fait> getConsequences() {
        return consequences;
    }
    
    public Categorie getCategorie() {
        return categorie;
    }

}
