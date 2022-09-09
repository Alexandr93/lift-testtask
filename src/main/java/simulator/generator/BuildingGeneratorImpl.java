package simulator.generator;

import simulator.model.Button;
import simulator.model.Floor;
import simulator.model.Passengers;

import java.util.*;

public class BuildingGeneratorImpl implements BuildingGenerator {

    private static int floorsCount;
    static Random random = new Random();

    public BuildingGeneratorImpl(int minFloorsCount, int maxFloorsCount) {
        floorsCount = random.nextInt(minFloorsCount, maxFloorsCount);
    }

    public Map<Integer, Floor> generate() {
        Map<Integer, Floor> building = new HashMap<>();
        for (int i = 1; i <= floorsCount; i++) {
            Floor floor = generateFloor(i);
            building.put(i, floor);
        }
        return building;
    }


    public Floor generateFloor(int floorNumber) {
        Floor floor = new Floor();
        floor.setFloorNumber(floorNumber);
        int passengersCount = random.nextInt(0, 10);

        Queue<Passengers> passengersList = new LinkedList<>();


        for (int i = passengersCount; i > 0; i--) {
            Passengers passengers = generatePassenger(floorNumber);
            if (passengers.getActiveButton() == Button.UP) {
                floor.setUpButtonActivity(true);
            }
            if (passengers.getActiveButton() == Button.DOWN) {
                floor.setDownButtonActivity(true);
            }
            passengersList.add(passengers);
        }
        floor.setPassengersList(passengersList);


        return floor;
    }

    public Passengers generatePassenger(int currentFloor) {
        Passengers passenger = new Passengers();

        passenger.setCurrentFloor(currentFloor);
        int desiredFloor = generateTargetFloor(currentFloor, passenger);
        passenger.setTargetFloor(desiredFloor);
        return passenger;
    }

    public static int generateTargetFloor(int currentFloor, Passengers passenger) {
        int result = currentFloor;
        while (result == currentFloor) {
            result = random.nextInt(1, floorsCount);
        }
        if (result < currentFloor) {
            passenger.setActiveButton(Button.DOWN);
        } else {
            passenger.setActiveButton(Button.UP);
        }
        return result;


    }
}
