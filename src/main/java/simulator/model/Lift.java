package simulator.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Lift {
    private List<Passengers> passengersList = new ArrayList<>();
    private Button direction;

    private Floor targetFloor;
    private Floor currentFloor;
    private int freePlaceCount = 5;
    private int liftSize;

    public void addPassengers(Passengers passengers) {
        passengersList.add(passengers);
    }

    public void removePassengers(Passengers passengers) {
        passengersList.remove(passengers);
    }


    public void freePlaceCountIncr() {
        freePlaceCount++;
    }

    public void freePlaceCountDecr() {
        freePlaceCount--;
    }
}
