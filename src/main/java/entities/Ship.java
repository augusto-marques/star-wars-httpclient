package entities;

import enums.HoursInTimespan;

public class Ship {

    String name;
    int speed;
    int consumables;
    String consumablesType;
    int stops;

    public String getName() {
        return name;
    }

    public int getStops() {
        return stops;
    }

    public Ship (String name, String speed, String[] consumables){
        this.name = name;
        this.speed = speed.contains("unknown") ? 0 : Integer.parseInt(speed);
        this.consumables = consumables[0].contains("unknown") ? 0 : Integer.parseInt(consumables[0]);
        this.consumablesType = consumables.length > 1 ? consumables[1] : "";
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
        for (HoursInTimespan timeframe : HoursInTimespan.values()) {
            if (consumablesType.contains(timeframe.name().toLowerCase())) {
                consumablesInHour = consumables * timeframe.getValue();
            }
        }
        return consumablesInHour;
    }
}
