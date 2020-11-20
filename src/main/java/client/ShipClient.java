package client;

import entities.Ship;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


public class ShipClient {
    String nextPage = "http://swapi.dev/api/starships/";
    List<Ship> ships;

    public ShipClient(List<Ship> ships) throws IOException, InterruptedException {
        this.ships = ships;
        while (nextPage != null) {
            JSONArray shipArray = getHttpRequest();
            generateShips(shipArray);
        }
    }

    public JSONArray getHttpRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.nextPage))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return parseResponse(response);
    }

    private JSONArray parseResponse(HttpResponse response) {
        JSONObject shipsObject = new JSONObject(response.body().toString());
        this.nextPage = shipsObject.isNull("next") ? null : shipsObject.getString("next");
        return shipsObject.getJSONArray("results");
    }

    private void generateShips(JSONArray shipsResultArray) {
        for (int i = 0 ; i < shipsResultArray.length(); i++) {
            JSONObject shipObject = shipsResultArray.getJSONObject(i);
            String shipName = shipObject.getString("name");
            String speedString = shipObject.getString("MGLT");
            String[] consumablesList = shipObject.getString("consumables").split(" ");
            ships.add(new Ship(shipName, speedString, consumablesList));
        }
    }
}
