# LinguaMatch

par **WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim**

LinguaMatch est une application (avec et sans interface graphique) permettant d'automatiser le processus d'appariement entre adolescents hôtes/visiteur dans le cadre de séjours linguistiques...

(Capture d'écran de l'interface graphique/console)

**Avec LinguaMatch vous pourrez :**
- Importer un fichier CSV avec un format bien précis contentant les informations et les critères des adolescents et gérer les éventuelles incohérences (type, valeur)
- Trouver une solution sur les meilleurs appariements possibles pour tous les adolescents (si possible) via un algorithme d'affectation
- Afficher la solution via une interface graphique qui permettra également de modifier les paramètres de l'algorithme pour guider la solution mais aussi d'exporter la solution au format CSV
- Afficher les adolescents affectés nul part (toutes les contraintes n'ont pas été satisfaites comme les contraintes rédhibitoires)
- Prendre en compte un historique des séjours des années précédentes pour une meilleure affectation des hôtes-visiteurs (cet historique est traité par sérialisation binaire)

**Liens vers tous les rapports:**
- [Rapport POO](#)
- [Rapport Graphes](graphes/graphes.md)
- [Rapport IHM](#)
    - Compétence 5 "identifier les besoins métiers des clients et des utilisateurs" de R2.02
    - Compétence 6 "identifier ses aptitudes pour travailler dans une équipe" de R2.02

## Générer la documentation en local

Pour générer la documentation Javadoc en local

> Le fichier 'package-info.java' présent dans l'arborescence sert à documenter chaque package du projet

```
javadoc -author -d doc -cp lib/sae2_02.jar:lib/jgrapht-core-1.5.1.jar -sourcepath src -subpackages LinguaMatch 
```

La page principale se trouve dans ``doc/LinguaMatch/package-summary.html``