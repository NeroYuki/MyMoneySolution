package helper;

public class IntervalEnum {
    public enum INTERVAL {
        DAILY,
        WEEKLY,
        MONTHLY,
        QUARTERLY,
        YEARLY,
        ONE_TIME,
    }

    private INTERVAL intervalValue;

    public IntervalEnum(INTERVAL input) {
        this.intervalValue = input;
    }

    public int intervalToDays() {
        switch (intervalValue) {
            case DAILY: return 1;
            case WEEKLY: return 7;
            case MONTHLY: return 30;
            case QUARTERLY: return 90;
            case YEARLY: return 365;
            case ONE_TIME: return 0;
            //should never reach this
            default: return -1;
        }
    }
}
