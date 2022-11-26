package dvn.coordinates_bot.coordinates.bot.geocoderAPI;

import java.util.Date;

/*based on Singleton pattern*/
public class GeocoderApiCounter {

    private static GeocoderApiCounter instance;
    private static int counter;
    private static Date date;

    private GeocoderApiCounter() {
    }

    public static GeocoderApiCounter getAPICounter() {
        if ((date == null)) {date = new Date();}
        if ( (instance == null) && (date.getDate() == (new Date()).getDate()) ) {
            instance = new GeocoderApiCounter();
            date = new Date();
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
