import javax.json.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Program {
    private final int HOURS_IN_DAY = 24;
    String nextPage = "http://swapi.dev/api/starships/";
    private Map<String, Integer> timeframes= new HashMap<String, Integer>();

    public Program buildTimeframes(String timeSpan, int daysInTimeSpan) {
        this.timeframes.put(timeSpan, daysInTimeSpan);
        return this;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Program program = new Program();
        program.buildTimeframes("day", 1)
                .buildTimeframes("week", 7)
                .buildTimeframes("month", 30)
                .buildTimeframes("year", 365);

        JsonArray shipArray;
        int distance = program.handleDistanceInput();
        while (program.nextPage != null) {
            shipArray = program.getHttpRequest();
            program.calculateStops(shipArray, distance);
        }
    }

    public JsonArray getHttpRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.nextPage))
                .header("Content-Type", "application/json")
                .build();


        InputStream response = client.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
                .thenApply(HttpResponse::body)
                .join();

        JsonReader reader = Json.createReader(response);
        JsonObject shipsObject = reader.readObject();


        this.nextPage = shipsObject.get("next").getValueType() == JsonValue.ValueType.STRING
                ? shipsObject.getString("next")
                : null;

        return shipsObject.getJsonArray("results");

    }

    public int handleDistanceInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Distance in mega lights: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public void calculateStops(JsonArray shipArray, int distance) {
        shipArray.forEach(ship -> {
            JsonObject shipContent = (JsonObject) ship;
            System.out.println(shipContent.getString("name"));
            String[] consumablesList = shipContent.getString("consumables").split(" ");
            int consumablesInHour = calculateConsumablesInHour(consumablesList);
            String speedString = shipContent.getString("MGLT");
            int stops;
            if (speedString.contains("unknown")) {
                stops = 0;
            } else {
                int speed = Integer.parseInt(speedString);
                int timeInHour  = distance / speed;

                try {
                    stops = timeInHour / consumablesInHour;
                } catch (ArithmeticException e) {
                    stops = 0;
                }
            }

            System.out.println("Number of stops: " + stops);
            System.out.println();
        });
    }

    public int calculateConsumablesInHour(String[] consumablesList) {
        if (consumablesList[0].contains("unknown")) {
            return 0;
        }
        int digits = Integer.parseInt(consumablesList[0]);
        String timeframe = consumablesList[1];
        System.out.println("Consumables: " + digits + " - " + timeframe);
        int consumablesInHour = 0;
        for (Map.Entry<String, Integer> entry : timeframes.entrySet()) {
            if (timeframe.contains(entry.getKey())) {
                consumablesInHour = digits * entry.getValue() * this.HOURS_IN_DAY;
            }
        }
        return consumablesInHour;
    }
}
