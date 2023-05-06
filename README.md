# LinguaMatch

par **WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim**

LinguaMatch est une application (avec et sans interface graphique) permettant d'automatiser le processus d'apparaiement entre adolescents hôtes/visiteur dans le cadre de séjours linguistiques...

Avec LinguaMatch vous pourrez:
- Importer un CSV avec un format bien précis contentant les informations et critères des adolescents et gérer les éventuelles incohérences
- Trouver une solution sur les meilleurs apparaiements possibles pour tous les adolescents (si possible) via des algorithmes d'affectation et pouvoir ainsi les afficher
- Afficher les adolescents affectés nul part (toutes les contraines n'ont pas été satisfaites)
- Prendre en compte un historique des séjours des années précédentes pour une meilleure affectation des hôtes-visiteurs

## Générer la documentation en local

Pour générer la documentation Javadoc en local

> Le fichier 'src/LinguaMatch/package-info.java' sert à documenter le package LinguaMatch (à la racine de la documentation javadoc)

```
javadoc -d doc src/LinguaMatch/*.java -author
```