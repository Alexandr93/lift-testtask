package simulator.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.Queue;

@Data
public class Floor {
    private Queue<Passengers> passengersList = new LinkedList<>();
    private int floorNumber;
    private boolean UpButtonActivity = false;
    private boolean DownButtonActivity = false;


    public void addPassengers(Passengers passengers) {
        passengersList.add(passengers);
    }

}
