## 1 - Premier Service REST
Pour l'instant notre application ne fait rien. Nous allons y remédier.

Nous allons exposer un premier service REST qui va renvoyer la liste des places de parking que notre application est censée gérer :
- Créer une classe `ParkingPlace` dans un package `models` avec un champ private `numero` de type String :
```java
package fr.cirrus.parkingmanager.models;

public class ParkingPlace {
    private String numero;

    public ParkingPlace(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
```

- Créer une classe `ParkingPlaceController` dans un package `controllers` :
```java
package fr.cirrus.parkingmanager.controllers;

import fr.cirrus.parkingmanager.models.ParkingPlace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ParkingPlaceController {

    @GetMapping("/parkingPlaces")
    public List<ParkingPlace> all() {
        return Arrays.asList(
                new ParkingPlace("1"),
                new ParkingPlace("2")
        );
    }
}
```
Ce qu'on peut noter ici :
- L'annotation `@RestController` indique à Spring que cette classe est un Bean Spring (de type RestController). Cette classe va donc participer au système d'injection de dépendence de Spring. Au runtime, Spring va instancier cette classe et l'injecter là où on en aura besoin.
- L'annotation `@GetMapping` sur la méthode `all()` permet de mapper la requête GET sur l'url `/parkingPlaces` à la méthode `all()`

- Rebuilder votre projet

- Maintenant ouvrez votre navigateur à l'url [http://localhost:8080/parkingPlaces](http://localhost:8080/parkingPlaces). 
Vous devrez avoir :
```json
[{"numero":"1"},{"numero":"2"}]
```
qui est la représentation JSON de vos données.

### Bonus
- Comme @GetMapping, les annotations suivantes existent : @PostMapping, @PutMapping, @DeleteMapping 
- Créer une nouvelle méthode dans `ParkingPlaceController` afin de récupérer une place de parking donnée :
```java
    @GetMapping("/parkingPlaces/{numero}")
    public ParkingPlace get(@PathVariable("numero") String numero) {
        return new ParkingPlace(numero);
    }
```
Visitez l'url [http://localhost:8080/parkingPlaces/10](http://localhost:8080/parkingPlaces/10).

L'annotation `@PathVariable` permet de mapper une partie de l'url avec un argument de la méthode.

- Refactorer la classe `ParkingPlaceController` ainsi :
```java
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/parkingPlaces")
public class ParkingPlaceController {

    @GetMapping("")
    public List<ParkingPlace> all() {
        return Arrays.asList(
                new ParkingPlace("1"),
                new ParkingPlace("2")
        );
    }

    @GetMapping("{numero}")
    public ParkingPlace get(@PathVariable("numero") String numero) {
        return new ParkingPlace(numero);
    }
}
```
On a défini `@RequestMapping("/parkingPlaces")` directement sur la classe du controller afin d'éliminer la redondance de `/parkingPlaces` sur les méthodes.

