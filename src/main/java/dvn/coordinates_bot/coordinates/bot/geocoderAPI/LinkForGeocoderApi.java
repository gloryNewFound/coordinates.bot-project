package dvn.coordinates_bot.coordinates.bot.geocoderAPI;

import dvn.coordinates_bot.coordinates.bot.regions.Region;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Component
@PropertySource("application.properties")
@Log4j
public class LinkForGeocoderApi {

    private String requestedAddress;
    private String region;

    private final String BASE_URL = "https://geocode-maps.yandex.ru/1.x/";


    @Value("${geocoder.token}")
    private String API_TOKEN;

//    Если потребуется установить центр и область для поиска
    private String ll;

    private String spn;


    //Constructor

    public LinkForGeocoderApi(){
    }

    public String getLinkForGeocoderApi(String requestAddress, String region) {
        this.requestedAddress = requestAddress;
        this.region = region;
        if (region != null) {
            for (Region r: Region.values()) {
                if (r.getName().equals(region)) {
                    ll = r.getCenterLong() + "," + r.getCenterLati();
                    spn = r.getWidth() + "," + r.getHeight();
                    break;
                }
            }
        }
        log.debug("Received address for request: " + requestedAddress);
        String encodedAddress = "";
        try {
            encodedAddress = URLEncoder.encode(requestedAddress, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.debug(e.getMessage());
        }
        String linkForGeocoderApi;
        if (ll != null) {
            linkForGeocoderApi = (BASE_URL + "?apikey=" + API_TOKEN
                    + "&geocode=" + encodedAddress
                    + "&rspn=" + "0"
                    + "&ll=" + ll + "&spn=" + spn //Если потребуется установить центр и область для поиска
                    + "&format=json");
        } else {
            linkForGeocoderApi = (BASE_URL + "?apikey=" + API_TOKEN
                    + "&geocode=" + encodedAddress
                    + "&format=json");
        }


        return linkForGeocoderApi;
    }

}
