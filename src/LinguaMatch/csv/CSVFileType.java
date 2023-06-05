package LinguaMatch.csv;

/**
 * Enum pour décrire le type de fichier CSV en lecture
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public enum CSVFileType {
    /**
     * Format CSV avec la liste des adolescents
    */
    STANDARD(1),
    /**
     * Format CSV avec le couple d'adolescents (Historique d'affectation) 
    */
    HISTORY(2);

    private int nbTeenagersByLine;

    private CSVFileType(int nb) {
        this.nbTeenagersByLine = nb;
    }

    /**
     * Retourne le nombre d'adolescents par ligne suivant le format, utilisé dans la classe CSVReader pour indiquer combien d'adolescents qu'il devra traiter par ligne
     * @see CSVReader
    */
    public int getNbTeenagersByLine() {
        return this.nbTeenagersByLine;
    }
}
