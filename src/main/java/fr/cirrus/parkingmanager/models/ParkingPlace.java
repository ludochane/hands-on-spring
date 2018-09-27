package fr.cirrus.parkingmanager.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "parkingplace")
public class ParkingPlace {
    @Id
    private String numero;

    public ParkingPlace(String numero) {
        this.numero = numero;
    }

    public ParkingPlace() {
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
