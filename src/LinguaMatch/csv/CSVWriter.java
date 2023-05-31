package LinguaMatch.csv;

import fr.ulille.but.sae2_02.graphes.Arete;

import java.util.List;

import LinguaMatch.core.Teenager;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

/**
 * Classe utile pour écrire des fichiers CSV respectant quelques contraintes dans le cadre de la SaÉ
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class CSVWriter {
    // Flux en écriture
    private BufferedWriter bw;
    // Le délimiteur
    private String delimiter;

    /**
     * Constructeur qui prend en paramètre le chemin du fichier CSV
     * @param filepath Chemin du fichier CSV
    */
    public CSVWriter(String filepath, String delimiter) throws IOException {
        try {
            this.bw = new BufferedWriter(new FileWriter(filepath));
            this.delimiter = delimiter;
        } catch(IOException e) {
            throw new IOException("Problème d'écriture avec le fichier indiqué en paramètre");
        }
    }
 
    /**
     * Fermeture du flux en écriture
    */
    public void close() {
        try {
            this.bw.close();
        } catch(IOException e) {
            System.out.println("Problème IO lors de la fermeture du flux");
        }
    }

    private String columns() {
        String out = "";
        // 24 colonnes pour le colonnes du couple hôte-visiteur
        for(int i = 0; i < 2; i++) {
            out += "FORENAME" + this.delimiter +
                   "NAME" + this.delimiter +
                   "COUNTRY" + this.delimiter +
                   "BIRTH_DATE" + this.delimiter +
                   "HOBBIES" + this.delimiter + 
                   "GUEST_ANIMAL_ALLERGY" + this.delimiter +
                   "HOST_HAS_ANIMAL" + this.delimiter +
                   "GUEST_FOOD" + this.delimiter +
                   "HOST_FOOD" + this.delimiter +
                   "GENDER" + this.delimiter +
                   "PAIR_GENDER" + this.delimiter +
                   "HISTORY";
            // Pour éviter de mettre un ";" en fin de ligne
            if(i == 0)
                out += this.delimiter;
        }
        return out;
    }

    /**
     * Écrit sur un fichier CSV suivant le format donné
     * @param affectations liste des affectations
    */
    public void write(List<Arete<Teenager>> affectations) throws IOException {
        try {
            this.bw.write(this.columns());
            this.bw.write(System.getProperty("line.separator"));

            Teenager[] ados = new Teenager[2];
            for(Arete<Teenager> couple : affectations) {
                ados[0] = couple.getExtremite1();
                ados[1] = couple.getExtremite2();

                for(int i = 0; i < ados.length; i++) {
                    this.bw.write(
                        ados[i].getForename() + this.delimiter +
                        ados[i].getName() + this.delimiter +
                        ados[i].getCountry() + this.delimiter +
                        ados[i].getBirthDate() + this.delimiter +
                        ados[i].getRequirement("HOBBIES") + this.delimiter +
                        ados[i].getRequirement("GUEST_ANIMAL_ALLERGY") + this.delimiter +
                        ados[i].getRequirement("HOST_HAS_ANIMAL") + this.delimiter +
                        ados[i].getRequirement("GUEST_FOOD") + this.delimiter +
                        ados[i].getRequirement("HOST_FOOD") + this.delimiter +
                        ados[i].getRequirement("GENDER") + this.delimiter +
                        ados[i].getRequirement("PAIR_GENDER") + this.delimiter +
                        ados[i].getRequirement("HISTORY")
                    );

                    // Pour éviter de mettre un ";" en fin de ligne
                    if(i == 0) { this.bw.write(";"); }
                }
                this.bw.write(System.getProperty("line.separator"));
            }
        } catch(IOException e) {
            throw new IOException("Problème d'écriture dans le fichier");
        }
    }
}
