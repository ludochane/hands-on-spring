# Maven, Spring, Spring Boot Hands On

# Tutorial

## Présentation
Application de gestion de parking

## Récupérer les sources
Cloner le repository : 
```
git clone https://github.com/ludochane/hands-on-spring.git
```

Importer le projet dans votre éditeur préféré (IntelliJ).

## 0 - Structure du projet
Remarquer la structure du projet. C'est un layout classique d'application java :
- `src/main/java` : toutes les classes java
- `src/main/resources` : toutes les resources qui seront dans le classpath de l'application
- `src/test/java` : classes de test
- `src/test/resources` : resources pour les tests

Ouvrir le fichier `pom.xml` et parcourir le fichier. 

Pour le moment, nous n'avons qu'un seul starter Spring Boot : `spring-boot-starter-web`.
Celui-ci permet :
- d'importer toutes les dépendances nécessaires au développement d'application web avec Spring
- et offre des mécanismes de configuration par défaut.

### Point d'entrée
Ouvrir la classe `ParkingManagerApplication`. C'est le point d'entrée de notre application Spring.
L'annotation `@SpringBootApplication` permet d'activer une configuration par défaut et le scan des entités Spring déclarées dans votre projet.
Exécuter cette classe dans votre éditeur : Clic-droit sur la classe, puis Run.

### En dev, Tomcat embarqué
Dans la console de log, vous pouvez voir :
```
o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
```
Un tomcat embarqué est démarré et vous pouvez accéder à l'application à l'url `http://localhost:8080`.

Toujours dans la console de log, vous avez peut-être remarquer la ligne :
```
o.s.b.d.a.OptionalLiveReloadServer       : LiveReload server is running on port 35729
```
Et oui nous avons du Live Reloading avec Spring Boot.

Créer un fichier `banner.txt` dans le dossier `src/main/resources` et copier-coller ceci dans le fichier :
```
  _____        _____  _  _______ _   _  _____            __  __          _   _          _____ ______ _____  
 |  __ \ /\   |  __ \| |/ /_   _| \ | |/ ____|          |  \/  |   /\   | \ | |   /\   / ____|  ____|  __ \ 
 | |__) /  \  | |__) | ' /  | | |  \| | |  __   ______  | \  / |  /  \  |  \| |  /  \ | |  __| |__  | |__) |
 |  ___/ /\ \ |  _  /|  <   | | | . ` | | |_ | |______| | |\/| | / /\ \ | . ` | / /\ \| | |_ |  __| |  _  / 
 | |  / ____ \| | \ \| . \ _| |_| |\  | |__| |          | |  | |/ ____ \| |\  |/ ____ \ |__| | |____| | \ \ 
 |_| /_/    \_\_|  \_\_|\_\_____|_| \_|\_____|          |_|  |_/_/    \_\_| \_/_/    \_\_____|______|_|  \_\
                                                                                                            
                                                                                                            
```

Ensuite rebuilder votre projet dans votre éditeur (IntelliJ : CTRL+F9, Eclipse : automatique). 

Vous devez remarquer dans votre console que le serveur a redémarré automatiquement pour prendre en compte les modifications.


