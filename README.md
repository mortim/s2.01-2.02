# LinguaMatch

par **WASSON Baptiste, LAGACHE Kylian, AOULAD-TAYAB Karim**

LinguaMatch est une application (avec et sans interface graphique) permettant d'automatiser le processus d'appariement entre adolescents hôtes/visiteur dans le cadre de séjours linguistiques...

(Capture d'écran de l'interface graphique)

**Avec LinguaMatch vous pourrez :**
- Importer un fichier CSV avec un format bien précis contentant les informations et les critères des adolescents et gérer les éventuelles incohérences (type, valeur)
- Trouver une solution sur les meilleurs appariements possibles pour tous les adolescents (si possible) via un algorithme d'affectation
- Afficher la solution via une interface graphique qui permettra également de modifier les paramètres de l'algorithme pour guider la solution mais aussi d'exporter la solution au format CSV
- Afficher les adolescents affectés nul part (toutes les contraintes n'ont pas été satisfaites comme les contraintes rédhibitoires)
- Prendre en compte un historique des séjours des années précédentes pour une meilleure affectation des hôtes-visiteurs (cet historique est traité par sérialisation binaire)

**Liens vers tous les rapports:**
- [Rapport POO](#)
- [Rapport Graphes](graphes/rapport.md)
- [Rapport IHM](#)
    - Compétence 5 "identifier les besoins métiers des clients et des utilisateurs" de R2.02
    - Compétence 6 "identifier ses aptitudes pour travailler dans une équipe" de R2.02

### Release

Vous retrouverez juste [ici](#) le fichier JAR avec toutes les classes compilées, il ne vous suffit que de lancer cette commande:

```
java -jar -cp [...] linguamatch_gui.jar
```

### Configurer le projet

Vous devez configurer le buildpath car ce n'est pas le même chemin selon chaque utilisateur

Dans [.vscode/settings.json](.vscode/settings.json):
```json
"java.project.referencedLibraries": [
    "CHEMIN_ABSOLU_VERS_LE_DOSSIER_LIB_DE_JAVAFX_SDK_17.0.2/*.jar",
    "lib/*.jar"
]
```

Dans le fichier global 'settings.json' de VSCode:

> Chemin de ce fichier:
> - (Linux) ~/.config/Code/User/settings.json
> - (MacOS) $HOME/Library/Application\ Support/Code/User/settings.json
> - (Windows) %APPDATA%\Code\User\settings.json

Ajouter cette ligne pour lancer correctement le fichier de test 'AffectationVersion2Test.java' (et aussi pour utiliser les fichiers JAR du dossier lib du projet que l'on peut voir ci-dessus) car il utilise des chemins relatifs pour accèder aux CSV du projet, chose que l'extension qui s'occupe des tests ne fait pas par défaut:
```json
"java.test.config": { "workingDirectory": "${workspaceFolder}" }
```

et aussi cette ligne pour lui renseigner les arguments de la JVM par défaut, en l'occurence ici c'est pour renseigner et ajouter certains modules indispensables au fonctionnement de JavaFX

```json
"java.debug.settings.vmArgs": "--module-path [CHEMIN_ABSOLU_VERS_LE_DOSSIER_LIB_DE_JAVAFX_SDK_17.0.2] --add-modules=javafx.controls,javafx.fxml"
```

## Générer la documentation en local

Pour générer la documentation Javadoc en local

> Le fichier 'package-info.java' présent dans l'arborescence sert à documenter chaque package du projet

```
javadoc -author -d doc -cp lib/sae2_02.jar:lib/jgrapht-core-1.5.1.jar -sourcepath src -subpackages LinguaMatch 
```

La page principale se trouve dans ``doc/LinguaMatch/package-summary.html``