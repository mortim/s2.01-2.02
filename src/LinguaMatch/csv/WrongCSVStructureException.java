package LinguaMatch.csv;

/**
 * Exception qui traite les problèmes de structure d'un fichier CSV
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class WrongCSVStructureException extends Exception {
    public WrongCSVStructureException(String ligne, int pos, String msg) {
        super("(L" + pos + ") sur la ligne '" + ligne + "':\n\t" + msg + "\n"); 
    }
 
}