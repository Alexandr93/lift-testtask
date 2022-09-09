package simulator;

import simulator.generator.BuildingGeneratorImpl;
import simulator.model.Button;
import simulator.model.Floor;
import simulator.model.Lift;
import simulator.model.Passengers;

import java.util.*;
import java.util.stream.Collectors;

public class Move {
    private final Lift lift;
    private final Queue<Passengers> liftQueue;
    private final Map<Integer, Floor> floorList;
    private int currentFloorNumb = 1;


    public Move(Map<Integer, Floor> floorList) {
        lift = new Lift();
        lift.setLiftSize(5);
        this.floorList = floorList;
        liftQueue = getAllPassengersQueue(floorList);
        lift.setDirection(Button.UP);
        lift.setFreePlaceCount(5);
        lift.setCurrentFloor(floorList.get(1));
        lift.setTargetFloor(floorList.get(1));
    }

    public void liftMove(int iterationCount) {


        for (int i = 0; i < iterationCount; i++) {


            //работает пока лифт не окажется пустым
            while (lift.getTargetFloor() != lift.getCurrentFloor()) {
                Floor currentFloor = floorList.get(currentFloorNumb);
                if (floorList.containsKey(currentFloorNumb)) {
                    currentFloor = floorList.get(currentFloorNumb);
                } else {
                    currentFloor = floorList.get(1);
                }

                lift.setCurrentFloor(currentFloor);
                System.out.println("**********************************");
                System.out.println("Lift going " + lift.getDirection().name());
                System.out.println("Free place " + lift.getFreePlaceCount());
                System.out.println("Floor number: " + currentFloor.getFloorNumber());
                dropPassengersFromLift(currentFloor);
                addPassengersToLift(currentFloor);
                nextFloor();

            }


            getNewDirection();
        }


    }

    private void getNewDirection() {
        if (lift.getFreePlaceCount() == lift.getLiftSize()) {
            lift.setTargetFloor(floorList.get(Objects.requireNonNull(liftQueue.poll()).getTargetFloor()));
            if (lift.getCurrentFloor().getFloorNumber() > lift.getTargetFloor().getFloorNumber()) {
                lift.setDirection(Button.DOWN);

            }
            if (lift.getCurrentFloor().getFloorNumber() < lift.getTargetFloor().getFloorNumber()) {
                lift.setDirection(Button.UP);

            }
            nextFloor();
        }
    }


    public void addPassengersToLift(Floor currentFloor) {

        Queue<Passengers> pass = currentFloor.getPassengersList().stream().filter(passengers -> passengers.getActiveButton() == lift.getDirection()).collect(Collectors.toCollection(LinkedList::new));

        while (!pass.isEmpty() && lift.getFreePlaceCount() > 0) {
            Passengers passenger = pass.poll();

            lift.addPassengers(passenger);
            lift.freePlaceCountDecr();
            System.out.println(" the passenger entered ");
            currentFloor.getPassengersList().remove(passenger);
            updateTargetFloor(passenger);

        }
        System.out.println();
    }

    private void updateTargetFloor(Passengers passenger) {
        if (lift.getDirection() == Button.UP && passenger.getActiveButton() == Button.UP) {

            if (passenger.getTargetFloor() > lift.getTargetFloor().getFloorNumber()) {
                Floor targetFloor = floorList.get(passenger.getTargetFloor());
                lift.setTargetFloor(targetFloor);
            }
            //устанавливаем желаемый этаж по минимальному этажу между всем пассажиров -  когда едет вниз
        }

        if (lift.getDirection() == Button.DOWN && passenger.getActiveButton() == Button.DOWN) {
            if (passenger.getTargetFloor() < lift.getTargetFloor().getFloorNumber()) {
                lift.setTargetFloor(floorList.get(passenger.getTargetFloor()));
            }
        }
    }


    public void dropPassengersFromLift(Floor currentFloor) {
        //выделяем пассажиров с этого этажа
        if (lift.getLiftSize() != lift.getFreePlaceCount()) {
            List<Passengers> leaveList = lift.getPassengersList().stream().filter(passengers -> floorList.get(passengers.getTargetFloor()) == currentFloor).collect(Collectors.toList());

            leaveList.forEach(passenger -> {
                passenger.setTargetFloor(BuildingGeneratorImpl.generateTargetFloor(currentFloor.getFloorNumber(), passenger));
                passenger.setCurrentFloor(currentFloor.getFloorNumber());
                currentFloor.addPassengers(passenger);

                lift.removePassengers(passenger);
                lift.freePlaceCountIncr();
                liftQueue.add(passenger);
                System.out.println(" the passenger got out");

            });
            System.out.println();

        }
    }

    public void nextFloor() {
        if (lift.getDirection().equals(Button.UP)) {
            currentFloorNumb++;

        }
        if (lift.getDirection().equals(Button.DOWN)) {
            currentFloorNumb--;

        }

    }


    public Queue<Passengers> getAllPassengersQueue(Map<Integer, Floor> floorList) {
        Queue<Passengers> queue = new LinkedList<>();

        floorList.values().forEach(floor -> {
            queue.addAll(floor.getPassengersList());
        });
        return queue;
    }
}
