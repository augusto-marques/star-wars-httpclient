import java.util.Scanner;

public class TravelInterface {
    public int handleDistanceInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Distance in mega lights: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public void displayResults(Ship ship) {
        System.out.println(ship.name);
        System.out.println(ship.stops);
        System.out.println();
    }
}
