package LinguaMatch.csv;

import fr.ulille.but.sae2_02.graphes.Arete;

import java.util.List;

import LinguaMatch.core.Teenager;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.Closeable;

/**
 * Classe utile pour écrire des fichiers CSV respectant quelques contraintes dans le cadre de la SaÉ
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class CSVWriter implements Closeable {
    // Flux en écriture
    private BufferedWriter bw;

    /**
     * Constructeur qui prend en paramètre le chemin du fichier CSV
     * @param filepath Chemin du fichier CSV
    */
    public CSVWriter(String filepath) throws IOException {
        try {
            this.bw = new BufferedWriter(new FileWriter(filepath));
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

    /**
     * Écrit sur un fichier CSV suivant le format donné
     * @param affectations Liste des affectations
    */
    public void write(List<Arete<Teenager>> affectations) throws IOException {
        try {
            this.bw.write(CSVUtil.columns(CSVFileType.HISTORY));
            this.bw.write(CSVUtil.LINE_SEPARATOR);

            Teenager[] ados = new Teenager[2];
            for(Arete<Teenager> couple : affectations) {
                ados[0] = couple.getExtremite1();
                ados[1] = couple.getExtremite2();

                for(int i = 0; i < ados.length; i++) {
                    this.bw.write(
                        ados[i].getForename() + CSVUtil.DELIMITER +
                        ados[i].getName() + CSVUtil.DELIMITER +
                        ados[i].getCountry() + CSVUtil.DELIMITER +
                        ados[i].getBirthDate() + CSVUtil.DELIMITER +
                        ados[i].getRequirement("HOBBIES") + CSVUtil.DELIMITER +
                        ados[i].getRequirement("GUEST_ANIMAL_ALLERGY") + CSVUtil.DELIMITER +
                        ados[i].getRequirement("HOST_HAS_ANIMAL") + CSVUtil.DELIMITER +
                        ados[i].getRequirement("GUEST_FOOD") + CSVUtil.DELIMITER +
                        ados[i].getRequirement("HOST_FOOD") + CSVUtil.DELIMITER +
                        ados[i].getRequirement("GENDER") + CSVUtil.DELIMITER +
                        ados[i].getRequirement("PAIR_GENDER") + CSVUtil.DELIMITER +
                        ados[i].getRequirement("HISTORY")
                    );

                    // Pour éviter de mettre un ";" en fin de ligne
                    if(i == 0) { this.bw.write(CSVUtil.DELIMITER); }
                }
                this.bw.write(CSVUtil.LINE_SEPARATOR);
            }
        } catch(IOException e) {
            throw new IOException("Problème d'écriture dans le fichier");
        }
    }
}
