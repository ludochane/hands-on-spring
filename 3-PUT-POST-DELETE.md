## 3 - PUT, POST, DELETE
Pour créer une entité, nous devons répondre au verbe HTTP `PUT`.

Modifier la classe `ParkingPlaceController`, en rajoutant une méthode create :
```java
@PutMapping
public ParkingPlace create(@RequestBody ParkingPlace parkingPlace) {
    return this.repository.save(parkingPlace);
}
```
- L'annotation `@PutMapping` permet de répondre au verbe HTTP `PUT`
- L'annotation  `@RequestBody` permet de mapper automatiquement le body de la requête HTTP à un objet Java.

Recompiler votre code. Vérifier dans la console que le serveur a redémarré automatiquement.

Effectuer une requête PUT. Vous pouvez utiliser `curl` ou l'application Postman (à installer) :
```
curl -X PUT --header 'Content-Type: application/json' --data '{"numero": "11"}' localhost:8080/parkingPlaces
```

Vérifier que la place de parking a bien été créée.

### Exercice 1
De la même façon, implémenter dans le controller les méthodes update() et delete() qui répondront aux verbes HTTP POST et DELETE respectivement.

Pour la méthode update(), ajouter un champ `private boolean isAvailable;` à `ParkingPlace` et tester en updatant ce champ.

Mettre à jour le fichier data.sql :
```sql
insert into PARKING_PLACE (numero, available) values (1, true);
insert into PARKING_PLACE (numero, available) values (2, true);
insert into PARKING_PLACE (numero, available) values (3, true);
```

Requêtes `curl` pour tester:
```
curl -X POST --header 'Content-Type: application/json' --data '{"numero": "1", "available": false}' localhost:8080/parkingPlaces
curl -X POST --header 'Content-Type: application/json' --data '{"numero": "1", "available": true}' localhost:8080/parkingPlaces

curl -X DELETE --header 'Content-Type: application/json' --data '{"numero": "10"}' localhost:8080/parkingPlaces
```

### Bonus
Rajouter une méthode dans l'interface `ParkingPlaceRepository` pour récupérer toutes les places de parking disponibles ou non  :
```java
List<ParkingPlace> findParkingPlacesByAvailable(boolean available);
```

Créer un endpoint Rest dans `ParkingPlageController` : 
```java
@GetMapping("/available/{available}")
public List<ParkingPlace> findByAvailable(@PathVariable("available") boolean available) {
    return this.repository.findParkingPlacesByAvailable(available);
}
```

Recompiler le code.

Tester les urls : 
- [http://localhost:8080/parkingPlaces/available/true](http://localhost:8080/parkingPlaces/available/true)
- [http://localhost:8080/parkingPlaces/available/false](http://localhost:8080/parkingPlaces/available/false)

Trouvez-vous normal que cela fonctionne ?
