package LinguaMatch.csv;

import java.util.Arrays;

/**
 * Boîte à outils pour les manipulations des fichiers CSV (CSVReader, CSVWriter)
 * @see CSVReader
 * @see CSVWriter
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class CSVUtil {
    /**
     * Le délimiteur de chaque colonne CSV
     */
    public final static String DELIMITER = ";"; 
    /*
     * Le séparateur de ligne (retour à la ligne)
    */
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * Retourne le nom de la colonne suivant son indice
     * @param idx Position de la colonne (en partant de 0)
    */
    public static String getColumnName(String line, int idx, int nbCols) {
        if(idx >= 0 && idx < nbCols)
            return line.split(";")[idx];
        return null;
    }

    /**
     * Compte le nombre de ';' d'une ligne CSV
     * @param line Ligne CSV
    */
    public static int countSemicolons(String line) {
        int nb = 0;
        int size = line.length();
        for(int i = 0; i < size; i++) {
            if(line.charAt(i) == ';')
                nb++;
        }
        return nb;
    }

    /**
     * Remplit un tableau de valeurs (issu d'une ligne CSV) de chaînes vides dans le cas où la ligne ne comporte que des colonnes vides jusqu'à la fin de la ligne, afin de conserver le même nombre de colonnes que la première ligne
     * @param valeurs Ligne CSV decomposé en mots via un tableau
     * @param nbSemicolons Nombre de points-virgules (délimiteur)
    */
    public static String[] emptyStrFill(String[] valeurs, int nbSemicolons) {
        String[] tmpValeurs = new String[nbSemicolons];
        for(int i = 0; i < nbSemicolons; i++) {
            if(i < valeurs.length)
                tmpValeurs[i] = valeurs[i];
            else
                tmpValeurs[i] = "";
        }
        return Arrays.copyOf(tmpValeurs, nbSemicolons);
    }

    public static String columns(CSVFileType type) {
        String out = "";
        int nbColonnes = type == CSVFileType.HISTORY ? 2 : 1;
        
        for(int i = 0; i < nbColonnes; i++) {
            out += "FORENAME" + CSVUtil.DELIMITER +
                   "NAME" + CSVUtil.DELIMITER +
                   "COUNTRY" + CSVUtil.DELIMITER +
                   "BIRTH_DATE" + CSVUtil.DELIMITER +
                   "HOBBIES" + CSVUtil.DELIMITER + 
                   "GUEST_ANIMAL_ALLERGY" + CSVUtil.DELIMITER +
                   "HOST_HAS_ANIMAL" + CSVUtil.DELIMITER +
                   "GUEST_FOOD" + CSVUtil.DELIMITER +
                   "HOST_FOOD" + CSVUtil.DELIMITER +
                   "GENDER" + CSVUtil.DELIMITER +
                   "PAIR_GENDER" + CSVUtil.DELIMITER +
                   "HISTORY";
            // Pour éviter de mettre un ";" en fin de ligne
            if(i == 0)
                out += CSVUtil.DELIMITER;
        }
        return out;
    }
}