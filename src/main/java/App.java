import client.ShipClient;
import entities.Ship;
import view.TravelInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        TravelInterface inputOutput = new TravelInterface();
        List<Ship> ships = new ArrayList<>();
        ShipClient shipClient = new ShipClient(ships);

        int distance = inputOutput.handleDistanceInput();

        for (Ship ship : ships) {
            ship.calculateStops(distance);
            inputOutput.displayResults(ship);
        }
    }
}
