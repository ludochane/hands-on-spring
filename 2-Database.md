## 2 - Database
Alors pour l'instant nous renvoyons des places de parking un peu fictive. Nous allons utiliser une base de données pour persister nos données.
- Dans le `pom.xml`, ajouter : 
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```
Ce starter Spring Boot permet de :
- rajouter toutes les dépendances pour pouvoir faire du JPA (Java Persistence API) et utiliser Hibernate comme implémentation
- autoconfigurer les datasources. Il faut juste préciser à Spring l'url de connexion à la base de données dans le fichier `application.properties`

Nous allons utiliser une base de données en mémoire (H2 Database). Cela est très utile pour :
- prototyper rapidement
- effectuer des tests unitaires qui ont besoin d'une base de données

Rajoutez dans le `pom.xml` : 
```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

Si besoin, dans votre IDE, effectuer un refresh Maven.

Et dans le fichier `application.properties`, nous allons préciser l'url de connexion à la database :
```properties
# Datasource
spring.datasource.url=jdbc:h2:mem:testdb
```

Recompiler le projet. Dans la console de log, vous devriez voir les lignes :
```
HHH000412: Hibernate Core {5.2.17.Final}
HHH000400: Using dialect: org.hibernate.dialect.H2Dialect
Initialized JPA EntityManagerFactory for persistence unit 'default'
```
qui montrent bien que JPA est dans la place (qu'un entity manager a été instancié), que Hibernate est utilisé comme implémentation, que le dialecte SQL utilisé est de type H2.

Nous allons ensuite transformer notre classe `ParkingPlace` en entité JPA. De manière simpliste, une entité JPA est mappée directement à une table SQL.
Modifier la classe `ParkingPlace` ainsi :
```java
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parkingPlace")
public class ParkingPlace {
    @Id
    private String numero;

    public ParkingPlace() {
    }

    ...
}
```
- L'annotation `@Entity` permet de déclarer une entité JPA. Et nous précisons le nom de la table SQL afférente "parkingPlace"
- L'annotation `@Id` déclare une primary key
- N'oubliez pas le constructeur par défaut. JPA en a besoin.

Créer le fichier `data.sql` dans le dossier `src/main/resources` :
```sql
insert into PARKING_PLACE (numero) values (1);
insert into PARKING_PLACE (numero) values (2);
insert into PARKING_PLACE (numero) values (3);
``` 
Par défaut, Spring Boot va exécuter le contenu de ce fichier pour initialiser les données de la base de données au démarrage de l'application.

<i>Pour aller plus loin</i>
Vous pouvez customiser le comportement de Spring à cet égard. Je vous renvoie vers la doc officielle : https://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html


Il nous faut maintenant une classe permettant de requêter la base de données sur la table "PARKING_PLACE". Spring Data utilise le concept de `Repository`. Créer une interface `ParkingPlaceRepository` dans le package `repositories` :
```java
package fr.cirrus.parkingmanager.repositories;

import fr.cirrus.parkingmanager.models.ParkingPlace;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingPlaceRepository extends CrudRepository<ParkingPlace, String> {

}

```
- L'annotation `@Repository` déclare un Bean Spring de type Repository. La classe pourra donc être injectée par Spring dans votre code.
- Comme l'interface étend l'interface `CrudRepository`, Spring va en créer une implémentation par défaut avec une multitude de fonctions CRUD basiques : findAll(), findById(), count(), delete(), etc...


Modifions à présent notre controller `ParkingPlaceController` afin d'utiliser le `ParkingPlaceRepository` :
```java
@RestController
@RequestMapping("/parkingPlaces")
public class ParkingPlaceController {

    private final ParkingPlaceRepository repository;

    @Autowired
    public ParkingPlaceController(ParkingPlaceRepository repository) {
        this.repository = repository;
    }

    ...

}
```
- `ParkingPlaceRepository` est injecté dans le constructeur du controller
- Cela fonctionne car `ParkingPlaceController` est lui-même un Bean Spring
- L'annotation `@Autowired` est en fait optionnel ici

<i>Pour aller plus loin</i>
Ici, c'est une injection par constructeur. Il existe deux autres types d'injections :
- l'injection par champ
- l'injection par setter
L'injection par constructeur est la méthode recommandée.
https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-spring-beans-and-dependency-injection.html

### Exercice
Réimplémenter les méthodes de `ParkingPlaceController` en utilisant le repository.

Compiler et vérifier 

<i>Pour aller plus loin</i>
Spring Data Rest ?

