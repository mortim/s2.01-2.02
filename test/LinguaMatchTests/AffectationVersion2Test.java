package LinguaMatchTests;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import LinguaMatch.core.graph.AffectationUtil;
import LinguaMatch.csv.CSVFileType;
import LinguaMatch.csv.CSVReader;
import LinguaMatch.csv.CSVUtil;
import LinguaMatch.csv.WrongCSVStructureException;
import LinguaMatch.core.Country;
import LinguaMatch.core.Teenager;
import LinguaMatch.core.graph.Affectation;
import fr.ulille.but.sae2_02.graphes.Arete;

public class AffectationVersion2Test {
    List<Teenager> teenagers;
    List<Teenager> teenagers2;
    List<Arete<Teenager>> historiqueAffectation;
    Affectation affectation;
    Affectation affectation2;

    // Utils
    private static Teenager searchTeenager(String forename, List<Teenager> t) {
        for(int i = 0; i < t.size(); i++) {
            if(t.get(i).getForename().equals(forename))
                return t.get(i);
        }
        return null;
    }
  
    @BeforeEach
    void initialization() {
        try {
            // Lecture du fichier CSV de l'historique d'affectation
            CSVReader csv = new CSVReader("graphes/V2/csv/exemple_minimal_historique_affectation.csv", CSVFileType.HISTORY);
            csv.load();
            this.historiqueAffectation = csv.getHistoryParsed();
            csv.close();

            // Lecture du fichier CSV de l'exemple minimal (exemple 1)
            csv = new CSVReader("graphes/V2/csv/exemple_minimal.csv");
            csv.load();
            this.teenagers = csv.getParsed();
            csv.close();

            // Lecture du fichier CSV de l'exemple 2
            csv = new CSVReader(CSVUtil.getRightAbsolutePath("graphes/V2/csv/exemple2.csv"));
            csv.load();
            this.teenagers2 = csv.getParsed();
            csv.close();

            // Création du graphe biparti de l'exemple minimal (exemple 1)
            this.affectation = new Affectation();
            AffectationUtil.ajouterAdolescents(this.affectation, this.teenagers, Country.GERMANY, Country.ITALY);
            AffectationUtil.ajouterAretesAdolescents(this.affectation, this.teenagers, this.historiqueAffectation, Country.GERMANY, Country.ITALY);

            // Création du graphe biparti de l'exemple 2
            this.affectation2 = new Affectation();
            AffectationUtil.ajouterAdolescents(this.affectation2, this.teenagers2, Country.GERMANY, Country.ITALY);
            AffectationUtil.ajouterAretesAdolescents(this.affectation2, this.teenagers2, this.historiqueAffectation, Country.GERMANY, Country.ITALY);
        } catch(FileNotFoundException | IllegalStateException | WrongCSVStructureException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void testAffectationExempleMinimal() {
        this.affectation.calculerAffectation();
        List<Arete<Teenager>> affectations = this.affectation.getAffectations();

        Teenager giorgio = AffectationVersion2Test.searchTeenager("Giorgio", this.teenagers);
        Teenager thomas = AffectationVersion2Test.searchTeenager("Thomas", this.teenagers);
        Teenager ciro = AffectationVersion2Test.searchTeenager("Ciro", this.teenagers);
        Teenager antonio = AffectationVersion2Test.searchTeenager("Antonio", this.teenagers);
        Teenager mateo = AffectationVersion2Test.searchTeenager("Mateo", this.teenagers);
        Teenager joshua = AffectationVersion2Test.searchTeenager("Joshua", this.teenagers);
        Teenager manuel = AffectationVersion2Test.searchTeenager("Manuel", this.teenagers);
        Teenager marco = AffectationVersion2Test.searchTeenager("Marco", this.teenagers);

        assertTrue(affectations.contains(this.affectation.getGrapheBiparti().getArete(antonio, giorgio)));
        assertTrue(affectations.contains(this.affectation.getGrapheBiparti().getArete(thomas, ciro)));
        assertTrue(affectations.contains(this.affectation.getGrapheBiparti().getArete(manuel, marco)));
        assertTrue(affectations.contains(this.affectation.getGrapheBiparti().getArete(joshua, mateo)));
    }

    @Test
    void testAffectationExemple2() {
        this.affectation2.calculerAffectation();
        List<Arete<Teenager>> affectations2 = this.affectation2.getAffectations();

        Teenager giorgio = AffectationVersion2Test.searchTeenager("Giorgio", this.teenagers2);
        Teenager thomas = AffectationVersion2Test.searchTeenager("Thomas", this.teenagers2);
        Teenager ciro = AffectationVersion2Test.searchTeenager("Ciro", this.teenagers2);
        Teenager antonio = AffectationVersion2Test.searchTeenager("Antonio", this.teenagers2);
        Teenager mateo = AffectationVersion2Test.searchTeenager("Mateo", this.teenagers2);
        Teenager joshua = AffectationVersion2Test.searchTeenager("Joshua", this.teenagers2);
        Teenager manuel = AffectationVersion2Test.searchTeenager("Manuel", this.teenagers2);
        Teenager marco = AffectationVersion2Test.searchTeenager("Marco", this.teenagers2);

        assertTrue(affectations2.contains(this.affectation2.getGrapheBiparti().getArete(thomas, ciro)));
        assertTrue(affectations2.contains(this.affectation2.getGrapheBiparti().getArete(manuel, marco)));
        assertTrue(affectations2.contains(this.affectation2.getGrapheBiparti().getArete(joshua, mateo)));
        assertTrue(affectations2.contains(this.affectation2.getGrapheBiparti().getArete(antonio, giorgio)));
    }
}