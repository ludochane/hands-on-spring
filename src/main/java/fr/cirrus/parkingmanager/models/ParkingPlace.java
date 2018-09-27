package fr.cirrus.parkingmanager.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parkingPlace")
public class ParkingPlace {
    @Id
    private String numero;

    @Column(name = "available")
    private boolean available;

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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
