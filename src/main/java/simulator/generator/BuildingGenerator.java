package simulator.generator;

import simulator.model.Floor;

import java.util.List;
import java.util.Map;

public interface BuildingGenerator {
    public Map<Integer, Floor> generate();
}
