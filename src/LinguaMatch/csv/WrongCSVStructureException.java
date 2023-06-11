package LinguaMatch.csv;

/**
 * Exception qui traite les problèmes de structure d'un fichier CSV
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class WrongCSVStructureException extends Exception {
    /**
     * Constructeur qui traite avec plus de précisions l'erreur survenue (avec la ligne et sa position dans le fichier CSV)
     * @param ligne La ligne en question
     * @param pos La position de la ligne dans le fichier
     * @param msg Le message d'erreur
    */
    public WrongCSVStructureException(String ligne, int pos, String msg) {
        super("(L" + pos + ") sur la ligne '" + ligne + "':\n\t" + msg + "\n"); 
    }

    /**
     * Simple exception avec message
     * @param msg Le message d'erreur
    */
    public WrongCSVStructureException(String msg) {
        super(msg);
    }
 
}