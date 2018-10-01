package fr.cirrus.parkingmanager.models;

import javax.persistence.*;

@Entity
@Table(name = "parkingPlace")
public class ParkingPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String numero;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
