package LinguaMatch.csv;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import LinguaMatch.core.Country;
import LinguaMatch.core.Criterion;
import LinguaMatch.core.CriterionName;
import LinguaMatch.core.Teenager;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Classe utile pour lire des fichiers CSV respectant quelques contraintes dans le cadre de la SaÉ
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class CSVReader {
    // Flux de lecture
    private Scanner sc;
    // En commencant par la deuxième ligne
    private int pos;
    private String firstLine;
    private int nbCols;
    // Liste des lignes CSV respectant le format
    private List<Teenager> parsed;
    // Liste des erreurs pour les lignes CSV ne respectant pas le format
    private List<String> errors;

    /**
     * Constructeur qui prend en paramètre le chemin du fichier CSV
     * @param filepath Chemin du fichier CSV
    */
    public CSVReader(String filepath) throws FileNotFoundException {
        try {
            this.sc = new Scanner(new File(filepath));
        } catch(FileNotFoundException e) {
            throw new FileNotFoundException("Le fichier '" + filepath + "' n'existe pas");
        }
        this.sc.useDelimiter(";");
        // On skip la première ligne (représentant les colonnes) et on retourne le nombre de colonnes
        this.pos = 2;
        this.firstLine = this.sc.nextLine().replaceAll("\"", "");
        this.nbCols = this.firstLine.split(";").length;
        this.parsed = new ArrayList<>();
        this.errors = new ArrayList<>();
    }

    /**
     * Retourne les lignes du CSV parsés correctement sous le type Teenager
    */
    public List<Teenager> getParsed() {
        return this.parsed;
    }

    /**
     * Retourne les lignes du CSV (avec le numéro de ligne du fichier et leur message d'erreur provenant de l'exception WrongCSVStructureException) qui n'ont pas été parsé correctement
     * @see WrongCSVStructureException
    */
    public List<String> getErrors() {
        return this.errors;
    }

    /**
     * Retourne le nom de la colonne suivant son indice
     * @param idx Position de la colonne (en partant de 0)
    */
    public String getColumnName(int idx) {
        if(idx >= 0 && idx < nbCols) {
            String[] columns = this.firstLine.split(";");
            return columns[idx];
        }
        return null;
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
            throw new WrongCSVStructureException(ligne, pos, "Le nombre de colonnes n'est pas identique");

        if(valeurs[0].equals("") || valeurs[1].equals(""))
            throw new WrongCSVStructureException(ligne, pos, "Les champs FORENAME / NAME ne doivent pas être vides.");
                
        try {
            Country.valueOf(valeurs[2]);
        } catch(IllegalArgumentException e) {
            throw new WrongCSVStructureException(ligne, pos, "Le champ COUNTRY contient un pays qui n'est pas dans l'enum COUNTRY.");
        }

        try {
            LocalDate.parse(valeurs[3]);
        } catch(DateTimeParseException e) {
            throw new WrongCSVStructureException(ligne, pos, "La date à un format incorrect.");
        }
    }

    /**
     * Charge le fichier CSV suivant le format donné
    */
    public void load() throws IllegalStateException {
        String ligne;
        String[] valeurs;
        Teenager teenager;
    
        try {
            while(this.sc.hasNextLine()) {
                ligne = sc.nextLine().replaceAll("\"", "");
                valeurs = ligne.split(";");
                try {
                    this.checkCSVStruct(ligne, valeurs);
                    
                    teenager = new Teenager(valeurs[0], valeurs[1], LocalDate.parse(valeurs[3]), Country.valueOf(valeurs[2]));
    
                    String criterion;
                    for(int i = 4; i <= 11; i++) {
                        criterion = this.getColumnName(i);
    
                        teenager.addCriterion(criterion, new Criterion(CriterionName.valueOf(criterion), valeurs[i]));
                    }
    
                    this.parsed.add(teenager);
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
