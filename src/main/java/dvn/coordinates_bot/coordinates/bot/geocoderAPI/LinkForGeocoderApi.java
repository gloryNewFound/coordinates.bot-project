package dvn.coordinates_bot.coordinates.bot.geocoderAPI;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Log4j
public class LinkForGeocoderApi {
    private String address;

    private final String BASE_URL = "https://geocode-maps.yandex.ru/1.x/";

    private String API_TOKEN = "c2fa646e-1938-4510-91a0-71da95877d8e";

    /* Если потребуется установить центр и область для поиска
    private final String ll = "37.617698,55.755864"; // координаты центра Москвы в формате долгота,широта

    private final String spn = "3.111111,2.111111"; // ограничения поиска с центром в координатах ll
     */

    private String linkForGeocoderApi;

    //Constructor
    public LinkForGeocoderApi(String address){
        this.address = address;
        log.debug("Received address for request: " + address);
        String encodedAddress = "";
        try {
            encodedAddress = URLEncoder.encode(address, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.debug(e.getMessage());
        }
        linkForGeocoderApi = (BASE_URL + "?apikey=" + API_TOKEN
                        + "&geocode=" + encodedAddress
//                        + "&ll=" + ll + "&spn=" + spn //Если потребуется установить центр и область для поиска
                        + "&format=json");

    }

    public String getRequestLink() {
        return linkForGeocoderApi;
    }
}
