import javax.json.JsonArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        TravelInterface inputOutput = new TravelInterface();
        List<Ship> ships = new ArrayList<>();
        ShipController shipController = new ShipController(ships);

        int distance = inputOutput.handleDistanceInput();

        for (Ship ship : ships) {
            ship.buildTimeframes("day", 1)
                    .buildTimeframes("week", 7)
                    .buildTimeframes("month", 30)
                    .buildTimeframes("year", 365);
            ship.calculateStops(distance);
            inputOutput.displayResults(ship);
        }
    }
}
