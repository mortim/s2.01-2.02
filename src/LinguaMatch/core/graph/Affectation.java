package LinguaMatch.core.graph;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import LinguaMatch.core.Teenager;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;
import fr.ulille.but.sae2_02.graphes.Arete;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;

/**
 * Gère les affectations entre adolescents via un graphe biparti
 * @see AffectationUtil
 * @author WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim
 */
public class Affectation implements Serializable {
    private GrapheNonOrienteValue<Teenager> grapheBiparti;
    private List<Teenager> sommetsGauche;
    private List<Teenager> sommetsDroite;
    private List<Arete<Teenager>> affectations;

    /**
     * Constructeur qui initialise un graphe biparti, et 2 listes stockant les sommets de gauche et de droite de ce graphe
    */
    public Affectation() {
        this.grapheBiparti = new GrapheNonOrienteValue<>();
        this.sommetsGauche = new ArrayList<>();
        this.sommetsDroite = new ArrayList<>();
    }

    /**
     * Retourne le graphe biparti
    */
    public GrapheNonOrienteValue<Teenager> getGrapheBiparti() {
        return this.grapheBiparti;
    }

    /**
     * Retourne la liste des sommets de gauche
    */
    public List<Teenager> getSommetsGauche() {
        return this.sommetsGauche;
    }

    /**
     * Retourne la liste des sommets de droite
    */
    public List<Teenager> getSommetsDroite() {
        return this.sommetsDroite;
    }

    /**
     * Retourne le résultat des affectations dans une liste
    */
    public List<Arete<Teenager>> getAffectations() {
        return this.affectations;
    }

    /**
     * Ajoute un adolescent en tant que sommet du graphe biparti
     * @param ado L'adolescent à ajouter
     * @param subset Le sous-ensemble dans le quel nous allons ajouter le sommet (gauche ou droite)
     * @see SubsetGraph
    */
    public void ajouterAdolescent(Teenager ado, SubsetGraph subset) {
        this.grapheBiparti.ajouterSommet(ado);
        if(subset == SubsetGraph.GAUCHE)
            this.sommetsGauche.add(ado);
        else
            this.sommetsDroite.add(ado);
    }

    /**
     * Ajoute une arête entre 2 adolescents avec un poids calculé qui dépend des contraintes et affinités
     * @param ado1 L'adolescent 1
     * @param ado2 L'adolescent 2
     * @param poids Le poids de l'arête
     * @see AffectationUtil#weight
    */
    public void ajouterCoupleHoteVisiteur(Teenager ado1, Teenager ado2, double poids) {
        this.grapheBiparti.ajouterArete(ado1, ado2, poids);
    }

    /**
     * Caclule les affectations selon le graphe biparti
    */
    public void calculerAffectation() {
        this.affectations = new CalculAffectation<>(this.grapheBiparti, this.sommetsGauche, this.sommetsDroite).calculerAffectation();
    }

    // Sérialisation binaire personalisée
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        this.sommetsGauche = (List)ois.readObject();
        this.sommetsDroite = (List)ois.readObject();
        Map<Teenager[], Double> map = (Map)ois.readObject();

        // Reconstruction du graphe
        this.grapheBiparti = new GrapheNonOrienteValue<>();

        // Ajout des sommets de gauche / droite dans le graphe biparti
        for(Teenager t : this.sommetsGauche)
            this.grapheBiparti.ajouterSommet(t);
        
        for(Teenager t : this.sommetsDroite)
            this.grapheBiparti.ajouterSommet(t);

        // Ajout des arêtes
        for(Map.Entry<Teenager[], Double> entry : map.entrySet())
            this.ajouterCoupleHoteVisiteur(entry.getKey()[0], entry.getKey()[1], entry.getValue());
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(this.sommetsGauche);
        oos.writeObject(this.sommetsDroite);

        // Arete n'est pas serialisable... :/
        Map<Teenager[], Double> map = new HashMap<>();

        for(Arete<Teenager> affectation : this.affectations)
            map.put(new Teenager[]{affectation.getExtremite1(), affectation.getExtremite2()}, affectation.getPoids());

        oos.writeObject(map);
    }
    
}
