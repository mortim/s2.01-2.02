package LinguaMatch.csv;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import LinguaMatch.core.Country;
import LinguaMatch.core.Criterion;
import LinguaMatch.core.CriterionName;
import LinguaMatch.core.Teenager;
import fr.ulille.but.sae2_02.graphes.Arete;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Classe utile pour lire des fichiers CSV respectant quelques contraintes dans le cadre de la SaÉ
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class CSVReader implements Closeable {
    // Flux de lecture
    private Scanner sc;
    // En commençant par la deuxième ligne
    private int pos;
    private String firstLine;
    private int nbCols;
    private CSVFileType type;
    // Liste des lignes CSV respectant le format standard
    private List<Teenager> standardParsed;
    // Liste des lignes CSV respectant le format d'un historique d'affectation
    private List<Arete<Teenager>> historyParsed;
    // Liste des erreurs pour les lignes CSV ne respectant pas le format
    private List<String> errors;
    // Liste des pays possibles pour des séjours
    private List<String> countries;

    /**
     * Constructeur qui prend en paramètre le chemin du fichier CSV et le type de fichier CSV
     * @param filepath Chemin du fichier CSV
     * @param type Format CSV (standard ou historique d'affectation)
     * @see CSVFileType
    */
    public CSVReader(String filepath, CSVFileType type) throws FileNotFoundException, WrongCSVStructureException {
        this.sc = new Scanner(new File(filepath));
        this.sc.useDelimiter(CSVUtil.DELIMITER);
        // On skip la première ligne (représentant les colonnes) et on retourne le nombre de colonnes
        this.pos = 2;
        this.firstLine = this.sc.nextLine().replaceAll("\"", "");
        this.nbCols = this.firstLine.split(CSVUtil.DELIMITER).length;

        if((type == CSVFileType.HISTORY && this.nbCols != 24))
            throw new WrongCSVStructureException("Le fichier stockant l'historique d'affectations n'en est pas un");
        if(type == CSVFileType.STANDARD && this.nbCols != 12)
            throw new WrongCSVStructureException("Le fichier de configuration ne doit pas être un historique d'affectations");

        this.type = type;
        this.standardParsed = new ArrayList<>();
        this.historyParsed = new ArrayList<>();
        this.errors = new ArrayList<>();
        this.countries = new ArrayList<>();
    }

    /**
     * Constructeur qui prend en paramètre le chemin du fichier CSV (avec le type de fichier CSV par défaut STANDARD)
     * @param filepath Chemin du fichier CSV
    */
    public CSVReader(String filepath) throws FileNotFoundException, WrongCSVStructureException {
        this(filepath, CSVFileType.STANDARD);
    }

    /**
     * Retourne les lignes du CSV parsés correctement sous le format STANDARD
     * @see CSVFileType
    */
    public List<Teenager> getParsed() {
        return this.standardParsed;
    }

    /**
     * Retourne les lignes du CSV parsés correctement sous le format HISTORY
     * @see CSVFileType
    */
    public List<Arete<Teenager>> getHistoryParsed() {
        return this.historyParsed;
    }

    /**
     * Retourne la liste des pays possibles pour des séjours
    */
    public List<String> getCountries() {
        return this.countries;
    }

    /**
     * Retourne les lignes du CSV (avec le numéro de ligne du fichier et leur message d'erreur provenant de l'exception WrongCSVStructureException) qui n'ont pas été parsé correctement
     * @see WrongCSVStructureException
    */
    public List<String> getErrors() {
        return this.errors;
    }

    /**
     * Fermeture du flux en lecture
    */
    public void close() {
        this.sc.close();
    }

    /**
     * Vérifie que le structure du fichier CSV est correcte sinon il levera une exception
     * @param ligne Ligne du CSV que l'on est en train de traiter (utilisé lorsque l'on lève une exception)
     * @param valeurs Ligne du CSV sous forme de tableau
    */
    public void checkCSVStruct(String ligne, String[] valeurs) throws WrongCSVStructureException {
        if(valeurs.length != this.nbCols)
            throw new WrongCSVStructureException(ligne, this.pos, "Le nombre de colonnes n'est pas identique.");

        int k = 0;
        // Le vérification de la structure est compatible pour les 2 formats (standard, historique)
        for(int i = 0; i < this.type.getNbTeenagersByLine(); i++) {
            if(valeurs[0+k].equals("") || valeurs[1+k].equals(""))
                throw new WrongCSVStructureException(ligne, this.pos, "Les champs FORENAME / NAME ne doivent pas être vides.");
            
            try {
                Country.valueOf(valeurs[2+k]);
            } catch(IllegalArgumentException e) {
                throw new WrongCSVStructureException(ligne, this.pos, "Le champ COUNTRY contient un pays qui n'est pas dans l'enum COUNTRY.");
            }

            try {
                LocalDate.parse(valeurs[3+k]);
            } catch(DateTimeParseException e) {
                throw new WrongCSVStructureException(ligne, this.pos, "La date à un format incorrect.");
            }

            k+=12;
        }
    }

    /**
     * Charge le fichier CSV suivant le format donné
    */
    public void load() throws IllegalStateException {
        String ligne;
        String[] valeurs;
        List<Teenager> teenagers = new ArrayList<>();
        int nbSemicolons, k;
        Teenager teenager;
    
        try {
            while(this.sc.hasNextLine()) {
                ligne = sc.nextLine().replaceAll("\"", "");
                valeurs = ligne.split(CSVUtil.DELIMITER);
                nbSemicolons = CSVUtil.countSemicolons(ligne);

                // S'il n'y a pas de ';' à la fin
                if(nbSemicolons == this.nbCols-1)
                    nbSemicolons++;

                // S'il n'y a que des colonnes vides jusqu'à la fin split ne les tient pas en compte, il faut les tenir en compte en tant que chaîne de caractères vides
                // La condition (nbSemicolons == this.nbCols) vérifie que la ligne (avant le split) a le bon nombre de colonnes pour éviter des lignes ayant trop de colonnes ou peu de colonnes rentrent dans la condition et qu'il ne lève pas d'exception dans la méthode this.checkCSVStruct() qui suit...
                if((valeurs.length != this.nbCols) && (nbSemicolons == this.nbCols))
                    valeurs = CSVUtil.emptyStrFill(valeurs, nbSemicolons);
                
                try {
                    k = 0;

                    this.checkCSVStruct(ligne, valeurs);

                    for(int i = 0; i < this.type.getNbTeenagersByLine(); i++) {
                        teenager = new Teenager(valeurs[0+k], valeurs[1+k], LocalDate.parse(valeurs[3+k]), Country.valueOf(valeurs[2+k]));

                        // Utilisé pour l'interface graphique (JavaFX)
                        if(!countries.contains(valeurs[2+k]))
                            countries.add(valeurs[2+k]);

                        for(int j = 4+k; j <= 11+k; j++) {
                            teenager.addCriterion(new Criterion(
                                        CriterionName.valueOf(CSVUtil.getColumnName(this.firstLine, j, this.nbCols)),
                                        valeurs[j]));
                        }

                        teenagers.add(teenager);
                        
                        // Passer à l'adolescent suivant (12 cols par ado sur la même ligne)
                        k+=12;
                    }

                    if(this.type == CSVFileType.HISTORY)
                        this.historyParsed.add(new Arete<Teenager>(teenagers.get(0), teenagers.get(1)));
                    else 
                        this.standardParsed.add(teenagers.get(0));

                    teenagers.clear();
                } catch(WrongCSVStructureException e) {
                    this.errors.add(e.getMessage());
                }
                this.pos++;
            }
        } catch(IllegalStateException e) {
            throw new IllegalStateException("Le flux du fichier CSV à été fermé");
        }
       
    }
}
