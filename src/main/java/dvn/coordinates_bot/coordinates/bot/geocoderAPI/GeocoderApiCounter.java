package dvn.coordinates_bot.coordinates.bot.geocoderAPI;

import java.time.LocalDate;

//based on Singleton pattern
public class GeocoderApiCounter {

    private static GeocoderApiCounter instance;
    private static int counter;
    private static LocalDate date;

    private GeocoderApiCounter() {
    }

    public static GeocoderApiCounter getAPICounter() {
        if (date == null) date = LocalDate.now();
        if ( (instance == null) || (!date.equals(LocalDate.now())) ) {
            instance = new GeocoderApiCounter();
            date = LocalDate.now();
            counter = 0;
        }
        return instance;
    }

    public int getCounter() {
        getAPICounter();
        return counter;
    }

    public void incrementCounter() {
        counter++;
    }
}
