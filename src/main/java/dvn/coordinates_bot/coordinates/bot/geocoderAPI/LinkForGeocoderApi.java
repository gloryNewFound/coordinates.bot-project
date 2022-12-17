package dvn.coordinates_bot.coordinates.bot.geocoderAPI;

import dvn.coordinates_bot.coordinates.bot.regions.RegionsAreas;
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

    @Value("${geocoder.baseLink}")
    private String BASE_URL;

    @Value("${geocoder.token}")
    private String API_TOKEN;

    private String ll;

    private String spn;

    public LinkForGeocoderApi(){
    }

    public String getLinkForGeocoderApi(String requestAddress, String region) {
        this.requestedAddress = requestAddress;
        log.info("Received address for request: " + requestedAddress);
        log.info("Region to cat area: " + region);

        if (region != null && !region.isEmpty()) {
            setSearchingAreaByRegion(region);
        } else {
            ll = null;
        }
        String encodedAddress = encodeAddress(requestAddress);
        String linkForGeocoderApi;
        if (ifRegionWasSet()) {
            linkForGeocoderApi = createLinkWithRegionBoundaries(encodedAddress);
        } else {
            linkForGeocoderApi = createLink(encodedAddress);
        }

        return linkForGeocoderApi;
    }

    private void setSearchingAreaByRegion(String region) {
        for (RegionsAreas r: RegionsAreas.values()) {
            if (r.getName().equals(region)) {
                this.ll = r.getCenterLong() + "," + r.getCenterLati();
                this.spn = r.getWidth() + "," + r.getHeight();
                break;
            }
        }
    }

    private String encodeAddress(String requestAddress) {
        String encodedAddress = null;
        try {
            encodedAddress = URLEncoder.encode(requestedAddress, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
        return encodedAddress;
    }

    private boolean ifRegionWasSet() {
        return ll != null;
    }

    private String createLinkWithRegionBoundaries(String encodedAddress) {
        return BASE_URL + "?apikey=" + API_TOKEN
                + "&geocode=" + encodedAddress
                + "&rspn=" + "1"
                + "&ll=" + ll + "&spn=" + spn
                + "&format=json";
    }

    private String createLink(String encodedAddress) {
        return BASE_URL + "?apikey=" + API_TOKEN
                + "&geocode=" + encodedAddress
                + "&format=json";
    }
}
