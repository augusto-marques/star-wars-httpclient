package enums;

public enum HoursInTimespan {
    HOUR(1),
    DAY(1 * HoursInTimespan.HOURS_IN_DAY),
    WEEK(7 * HoursInTimespan.HOURS_IN_DAY),
    MONTH(30 * HoursInTimespan.HOURS_IN_DAY),
    YEAR(365 * HoursInTimespan.HOURS_IN_DAY);

    private final int value;

    private static final int HOURS_IN_DAY = 24;

    HoursInTimespan(int consumableValue){
        value = consumableValue;
    }

    public int getValue(){
        return value;
    }

}
