package fr.cirrus.parkingmanager.dto;

import fr.cirrus.parkingmanager.models.ParkingPlace;
import fr.cirrus.parkingmanager.models.User;

public class UserParkingPlace {

    private User user;
    private ParkingPlace parkingPlace;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ParkingPlace getParkingPlace() {
        return parkingPlace;
    }

    public void setParkingPlace(ParkingPlace parkingPlace) {
        this.parkingPlace = parkingPlace;
    }
}
