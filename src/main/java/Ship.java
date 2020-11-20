import com.fasterxml.jackson.annotation.JsonProperty;

import javax.json.JsonObject;
import java.util.HashMap;
import java.util.Map;

public class Ship {

    private final int HOURS_IN_DAY = 24;
    String name;
    int speed;
    int consumables;
    String consumablesType;
    int stops;

    public Ship (String name, String speed, String[] consumables){
        this.name = name;
        this.speed = speed.contains("unknown") ? 0 : Integer.parseInt(speed);
        this.consumables = consumables[0].contains("unknown") ? 0 : Integer.parseInt(consumables[0]);
        this.consumablesType = consumables.length > 1 ? consumables[1] : "";
    }

    private Map<String, Integer> timeframes= new HashMap<String, Integer>();

    public Ship buildTimeframes(String timeSpan, int daysInTimeSpan) {
        this.timeframes.put(timeSpan, daysInTimeSpan);
        return this;
    }

    public void calculateStops (int distance) {
        int consumablesInHour = calculateConsumablesInHour();
        try {
            int timeInHour = distance / speed;
            stops = timeInHour / consumablesInHour;
        } catch (ArithmeticException e) {
            stops = 0;
        }
    }

    private int calculateConsumablesInHour() {
        int consumablesInHour = 0;
        for (Map.Entry<String, Integer> entry : timeframes.entrySet()) {
            if (consumablesType.contains(entry.getKey())) {
                consumablesInHour = consumables * entry.getValue() * this.HOURS_IN_DAY;
            }
        }
        return consumablesInHour;
    }
}
