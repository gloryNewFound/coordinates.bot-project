package dvn.coordinates_bot.coordinates.bot.geocoderAPI;

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

    private final String BASE_URL = "https://geocode-maps.yandex.ru/1.x/";


    @Value("${geocoder.token}")
    private String API_TOKEN;

    /* Если потребуется установить центр и область для поиска
    private final String ll = "37.617698,55.755864"; // координаты центра Москвы в формате долгота,широта

    private final String spn = "3.111111,2.111111"; // ограничения поиска с центром в координатах ll
     */

    //Constructor

    public LinkForGeocoderApi(){
    }

    public String getLinkForGeocoderApi(String requestAddress) {
        this.requestedAddress = requestAddress;
        log.debug("Received address for request: " + requestedAddress);
        String encodedAddress = "";
        try {
            encodedAddress = URLEncoder.encode(requestedAddress, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.debug(e.getMessage());
        }
        String linkForGeocoderApi = (BASE_URL + "?apikey=" + API_TOKEN
                + "&geocode=" + encodedAddress
//                        + "&ll=" + ll + "&spn=" + spn //Если потребуется установить центр и область для поиска
                + "&format=json");

        return linkForGeocoderApi;
    }

}
