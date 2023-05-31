package LinguaMatch.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import LinguaMatch.core.graph.Affectation;
import LinguaMatch.core.graph.AffectationUtil;
import LinguaMatch.core.graph.SubsetGraph;
import LinguaMatch.csv.CSVReader;
import LinguaMatch.csv.CSVWriter;

/**
 * Représente la plateforme d'affectation de tous les adolescents
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
*/
public class Platform {
    private ArrayList<Teenager> teenagers;

    /**
     * Constructeur qui définit une liste d'adolescents vide
    */
    public Platform() {
        this.teenagers = new ArrayList<>();
    }

    /**
     * Retourne la liste des adolescents
    */
    public ArrayList<Teenager> getTeenagers() {
        return this.teenagers;
    }

    /**
     * Ajoute un adolescent dans la liste des adolescents
     * @param t Un adolescent
    */
    public void addTeenager(Teenager t) {
        this.teenagers.add(t);
    }

    public void filterTeenagers() {
        Teenager next;
        Iterator<Teenager> it = this.teenagers.iterator();
        while(it.hasNext()) {
            next = it.next();
            if(next.hasInconsistencyCriterions())
                it.remove();
        }
    }

    /**
     * Supprime les critères invalides de tous les adolescents (types incorrects)
     * @see Teenager#purgeInvalidRequirement()
    */
    public void purgeInvalidRequirement() {
        for(Teenager t : this.teenagers)
            t.purgeInvalidRequirement();
    }

    // public static void main(String[] args) {
    //     try {
    //         CSVReader csv = new CSVReader("/home/karim/Downloads/s2.01-s2.02/csv/adosAleatoiresAvecIncompatiblesFranceItalie.csv");
    //         csv.load();

    //         for(Teenager t : csv.getParsed()) {
    //             System.out.println(t);
    //         }

    //         for(String errors : csv.getErrors()) {
    //             System.out.print(errors);
    //             System.out.println();
    //         }

    //         csv.close();
            
    //         // ------------------------
    //         CSVWriter csv2 = new CSVWriter("/home/karim/Downloads/s2.01-s2.02/csv/file.csv", ";");
            
    //         Teenager adonia = new Teenager("Adonia", "A", LocalDate.of(2005,12,15), Country.FRANCE);
    //         adonia.addCriterion("GUEST_ANIMAL_ALLERGY", new Criterion(CriterionName.GUEST_ANIMAL_ALLERGY, "no"));
    //         adonia.addCriterion("HOBBIES", new Criterion(CriterionName.HOBBIES, "sports,technology"));
        
    //         Teenager bellatrix = new Teenager("Bellatrix", "B", LocalDate.of(2005,12,15), Country.FRANCE);
    //         bellatrix.addCriterion("GUEST_ANIMAL_ALLERGY", new Criterion(CriterionName.GUEST_ANIMAL_ALLERGY, "yes"));
    //         bellatrix.addCriterion("HOBBIES", new Criterion(CriterionName.HOBBIES, "culture,science"));

    //         Affectation affectation = new Affectation();
    //         affectation.ajouterAdolescent(adonia, SubsetGraph.GAUCHE);
    //         affectation.ajouterAdolescent(bellatrix, SubsetGraph.DROITE);

    //         affectation.ajouterCoupleHoteVisiteur(adonia, bellatrix, AffectationUtil.weight(adonia, bellatrix));

    //         affectation.calculerAffectation();

    //         csv2.write(affectation.getAffectations());

    //         csv2.close();
    //     } catch(FileNotFoundException e) {
    //         System.out.println(e.getClass() + ": " + e.getMessage());
    //     } catch(IllegalStateException e) {
    //         System.out.println(e.getClass() + ": " + e.getMessage());
    //     } catch(IOException e) {
    //         System.out.println(e.getClass() + ": " + e.getMessage());
    //     }
    // }

}