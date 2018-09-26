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
