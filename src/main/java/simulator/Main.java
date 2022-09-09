package simulator;

import simulator.generator.BuildingGenerator;
import simulator.generator.BuildingGeneratorImpl;
import simulator.model.Floor;

import java.util.Map;

public class Main {

    public static void main(String[] args) {

        BuildingGenerator buildingGenerator = new BuildingGeneratorImpl(5, 20);
        Map<Integer, Floor> building = buildingGenerator.generate();
        Move move = new Move(building);
        move.liftMove(10);

    }
}
