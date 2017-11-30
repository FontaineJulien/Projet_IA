package Motor_package;

import java.util.ArrayList;
import java.util.List;

public class Fait {

    private String  label;
    private boolean valuation;

    public Fait( String label, boolean valuation ) {
        this.label = label.toLowerCase();
        this.valuation = valuation;
    }

    public String getLabel() {
        return label;
    }

    public boolean getValuation() {
        return valuation;
    }

    public boolean equals( Fait f ) {
        return this.label.equals( f.getLabel() ) && this.valuation == f.getValuation();
    }

    /**
     * Convertit une chaine de caractère en un fait. Gère l'évaluation du "!" en
     * début de de chaîne.
     * 
     * @param label
     * @return
     */
    public static Fait String_toFact( String label ) {
        try {
            String negation = label.substring( 0, 1 );
            boolean value = true;
            if ( negation.equals( "!" ) ) {
                value = false;
                return new Fait( label.substring( 1, label.length() ).trim(), value );
            } else
                return new Fait( label.trim(), value );
        } catch ( Exception e ) {
            return null;
        }
    }

    /**
     * Transforme une liste de string(représentant des faits) en une liste de
     * Fait.
     * 
     * @param liste_fait
     * @return list<Fait> -> la liste si succès. null sinon.
     */
    public static List<Fait> ArrayString_toFact( String[] liste ) {
        List<Fait> transformed_list = new ArrayList<Fait>();
        for ( int i = 0; i < liste.length; i++ ) {
            transformed_list.add( Fait.String_toFact( liste[i] ) );
        }
        return transformed_list;
    }

    /**
     * renvoyer le fait sous forme d'une chaine
     */
    public String toString() {
        return ( ( this.valuation == true ) ? "" : "!" ) + this.label;
    }
}
