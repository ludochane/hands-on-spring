## 4 - Service et Profil
Pour l'instant, le controller appelle directement le repository et la méthode update(), par exemple, contient une logique métier un peu complexe. Cela n'a rien à faire dans le controller. Le controller doit rester le plus simple possible. Il est en charge du mapping des url et des arguments.

On va donc introduire un service `ParkingPlaceService` dans le package `services`. Celui-ci va être injecté dans le controller en lieu et place du repository. Les méthodes du controller vont appeler les méthodes du service et non plus du repository.

### Exercice 1
Implémenter le service et refactorer le controller pour l'utiliser.

Ou sinon copier/coller le code ci-dessous :

Code de `ParkingPlaceService`:
```java
package fr.cirrus.parkingmanager.services;

import fr.cirrus.parkingmanager.models.ParkingPlace;
import fr.cirrus.parkingmanager.repositories.ParkingPlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingPlaceService {

    private final ParkingPlaceRepository repository;

    public ParkingPlaceService(ParkingPlaceRepository repository) {
        this.repository = repository;
    }

    public Iterable<ParkingPlace> all() {
        return this.repository.findAll();
    }

    public Optional<ParkingPlace> findByNumero(String numero) {
        return this.repository.findById(numero);
    }

    public ParkingPlace create(ParkingPlace parkingPlace) {
        return this.repository.save(parkingPlace);
    }

    public ParkingPlace update(ParkingPlace parkingPlace) {
        Optional<ParkingPlace> maybeParkingPlace = this.repository.findById(parkingPlace.getNumero());

        if (maybeParkingPlace.isPresent()) {
            return this.repository.save(parkingPlace);
        } else {
            throw new IllegalArgumentException(
                    String.format("Parking place with numero=%s does not exist", parkingPlace.getNumero())
            );
        }
    }

    public void delete(ParkingPlace parkingPlace) {
        this.repository.delete(parkingPlace);
    }

    public List<ParkingPlace> findByAvailable(boolean available) {
        return this.repository.findParkingPlacesByAvailable(available);
    }
}

```

Code de `ParkingPlaceController`:
```java
package fr.cirrus.parkingmanager.controllers;

import fr.cirrus.parkingmanager.models.ParkingPlace;
import fr.cirrus.parkingmanager.services.ParkingPlaceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parkingPlaces")
public class ParkingPlaceController {

    private final ParkingPlaceService service;

    public ParkingPlaceController(ParkingPlaceService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<ParkingPlace> all() {
        return this.service.all();
    }

    @GetMapping("{numero}")
    public Optional<ParkingPlace> get(@PathVariable("numero") String numero) {
        return this.service.findByNumero(numero);
    }

    @PutMapping
    public ParkingPlace create(@RequestBody ParkingPlace parkingPlace) {
        return this.service.create(parkingPlace);
    }

    @PostMapping
    public ParkingPlace update(@RequestBody ParkingPlace parkingPlace) {
        return this.service.update(parkingPlace);
    }

    @DeleteMapping
    public void delete(@RequestBody ParkingPlace parkingPlace) {
        this.service.delete(parkingPlace);
    }

    @GetMapping("/available/{available}")
    public List<ParkingPlace> findByAvailable(@PathVariable("available") boolean available) {
        return this.service.findByAvailable(available);
    }
}

```

### Exercice 2
L'avantage d'avoir déplacé le code dans un service, c'est qu'on peut maintenant en proposer différentes implémentations.

Imaginons que pour des raisons de test ou démo, nous voulons faire fonctionner notre application sans base de données. Et bien nous pouvons implémenter un `ParkingPlaceServiceMock` qui va nous renvoyer des données bouchonnées.

Ou peut-être que dans un deuxième temps, l'application ne va plus persister en base de données mais va faire appel à des Web Services d'une autre application en charge de persister les données. Nous pouvons implémenter un `ParkingPlaceServiceWS`.

Essayons.

Renommer la classe `ParkingPlaceService` en `ParkingPlaceServiceImpl`.
A partir de la classe `ParkingPlaceServiceImpl`, extraire une interface que vous nommerez `ParkingPlaceService` (Eclipse et IntelliJ devraient pouvoir le faire pour vous).

Dans la classe `ParkingPlaceController`, utiliser l'interface `ParkingPlaceService` au lieu de `ParkingPlaceServiceImpl`.

Créer une classe `ParkingPlaceServiceMock` implémentant l'interface `ParkingPlaceService`. Et implémenter toutes les méthodes avec des données bouchonnées :
```java
package fr.cirrus.parkingmanager.services;

import fr.cirrus.parkingmanager.models.ParkingPlace;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingPlaceServiceMock implements ParkingPlaceService {
    @Override
    public Iterable<ParkingPlace> all() {
        return Arrays.asList(new ParkingPlace("1"));
    }

    @Override
    public Optional<ParkingPlace> findByNumero(String numero) {
        return Optional.empty();
    }

    @Override
    public ParkingPlace create(ParkingPlace parkingPlace) {
        return parkingPlace;
    }

    @Override
    public ParkingPlace update(ParkingPlace parkingPlace) {
        return parkingPlace;
    }

    @Override
    public void delete(ParkingPlace parkingPlace) {

    }

    @Override
    public List<ParkingPlace> findByAvailable(boolean available) {
        return Arrays.asList(new ParkingPlace("1"));;
    }
}

```

Recompiler le code.

Dans la console de logs, vous devriez voir l'erreur :
```
***************************
APPLICATION FAILED TO START
***************************

Description:

Parameter 0 of constructor in fr.cirrus.parkingmanager.controllers.ParkingPlaceController required a single bean, but 2 were found:
    - parkingPlaceServiceImpl: defined in file [ParkingPlaceServiceImpl.class]
    - parkingPlaceServiceMock: defined in file [ParkingPlaceServiceMock.class]


Action:

Consider marking one of the beans as @Primary, updating the consumer to accept multiple beans, or using @Qualifier to identify the bean that should be consumed
```
Cela signifie que Spring ne sait pas quelle implémentation injecter dans `ParkingPlaceController` pour `ParkingPlaceService`.
`ParkingPlaceServiceImpl` ou `ParkingPlaceServiceMock` ?

Il faut donc l'aider un peu.

Dans les logs, Spring nous suggère d'utiliser l'annotation `@Primary` ou `@Qualifier` :
- si on annote `ParkingPlaceServiceImpl` par `@Primary`, alors elle aura la priorité et sera injectée.
- L'utilisation de l'annotation `@Qualifier` permet d'injecter une implémentation donnée en fonction d'un nom au lieu de laisser @Autowired faire.

Il y une autre possibilité, c'est d'utiliser les <b>profils</b> Spring.

Annotez la classe `ParkingPlaceServiceImpl` avec l'annotation `@Profile("default")`.

Annotez la classe `ParkingPlaceServiceMock` avec l'annotation `@Profile("mock")`.

Relancez votre application en passant comme argument à la JVM `-Dspring.profiles.active=mock`.
Testez l'url http://localhost:8080/parkingPlaces.

Relancez votre application en passant comme argument à la JVM `-Dspring.profiles.active=default`.
Testez l'url http://localhost:8080/parkingPlaces.

En fonction du profil activé lors de l'exécution de votre application, le bean correspondant sera injecté.

Du coup, dans votre éditeur préféré, vous pouvez configurer 2 exécutions différentes de votre application : une avec le profil mock, l'autre avec le profil default. <b>Et vous n'avez pas besoin de toucher à votre code</b> pour avoir l'un ou l'autre des comportements.

### Bonus
Vous savez que c'est toujours une bonne pratique d'externaliser la configuration de votre application dans les fichiers de properties.

Par défaut, Spring utilise le fichier application.properties.

Créer un répertoire `config` dans `src/main/resources`. Créer un fichier `application-mysql.properties`:
```properties
# Datasource
spring.datasource.url=jdbc:mysql://localhost/aliss
spring.datasource.username=user
spring.datasource.password=user
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```

Et maintenant exécuter votre l'application avec argument `-Dspring.profiles.active=default,mysql`.
 
Vous aurez une erreur disant que le driver `com.mysql.jdbc.Driver` ne peut pas être chargé. C'est normal nous ne l'avons pas mis en dépendance dans notre pom.xml. Mais cela veut surtout dire que Spring a surchargé les properties définies dans `application.properties` par les propriétés définies dans `application-mysql.properties`.

Conclusion : en fonction du profil passé, Spring va rechercher un fichier dans `config/application-{profile}.properties` en surchargeant les propriétés existantes.

De plus, on peut voir qu'on peut déclarer plusieurs profils actifs à l'exécution de l'application.