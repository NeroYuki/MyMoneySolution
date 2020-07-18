public class Config {
    private static String activeCurrency="VND";


    public static String getActiveCurrency() {
        return activeCurrency;
    }

    public static void setActiveCurrency(String activeCurrency) {
        Config.activeCurrency = activeCurrency;
    }
}
