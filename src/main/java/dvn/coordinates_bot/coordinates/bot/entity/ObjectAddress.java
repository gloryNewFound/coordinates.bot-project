package dvn.coordinates_bot.coordinates.bot.entity;

import dvn.coordinates_bot.coordinates.bot.controller.ApiController;
import dvn.coordinates_bot.coordinates.bot.geocoderAPI.GeocoderApiCounter;
import dvn.coordinates_bot.coordinates.bot.geocoderAPI.LinkForGeocoderApi;
import dvn.coordinates_bot.coordinates.bot.parser.ResponseParser;
import dvn.coordinates_bot.coordinates.bot.parser.pojoResponseGeocoderAPI.FeatureMemberItem;
import dvn.coordinates_bot.coordinates.bot.parser.pojoResponseGeocoderAPI.Point;
import dvn.coordinates_bot.coordinates.bot.parser.pojoResponseGeocoderAPI.Response;
import dvn.coordinates_bot.coordinates.bot.regions.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Scope("prototype")
public class ObjectAddress {

    private Response response;
    private String requestedAddress;
    private String foundAddress;
    private boolean precision;
    private Point point;
    private double latitude;
    private double longitude;

    @Autowired
    private LinkForGeocoderApi linkForGeocoderApi;

    public ObjectAddress() {
    }

    public void fillAllObjectAddressFields(String foundAddress, String region) {
        this.requestedAddress = foundAddress;

        String responseFromApi = ApiController.getRequest(linkForGeocoderApi.getLinkForGeocoderApi(requestedAddress, region));
        GeocoderApiCounter.getAPICounter().incrementCounter();
        this.response = ResponseParser.pojoFromJsonGeocoderApiString(responseFromApi);

        List<FeatureMemberItem> responsesToCheck = response.getResponse().getGeoObjectCollection().getFeatureMember();
        if (responsesToCheck.size() == 0) {
            this.precision = false;
            this.point = null;
            this.foundAddress = null;
            this.latitude = 0;
            this.longitude = 0;

        } else {
            for (FeatureMemberItem item : responsesToCheck) {
                if (item.getGeoObject().getMetaDataProperty().getGeocoderMetaData().getPrecision().equals("exact")) {
                    System.out.println(item.toString());
                    this.precision = true;
                    this.point = item.getGeoObject().getPoint();
                    this.foundAddress = item.getGeoObject().getMetaDataProperty().getGeocoderMetaData().getText();
                    break;
                }
            }
            if (!precision) {
                FeatureMemberItem item = responsesToCheck.get(0);
                System.out.println(item.toString());
                this.point = item.getGeoObject().getPoint();
                this.foundAddress = item.getGeoObject().getMetaDataProperty().getGeocoderMetaData().getText();
            }

            this.latitude = Double.parseDouble(point.getPos().split(" ")[1]);
            this.longitude = Double.parseDouble(point.getPos().split(" ")[0]);
        }
    }

    public String getRequestedAddress() {
        return requestedAddress;
    }

    public String getFoundAddress() {
        return foundAddress;
    }

    public boolean isPrecision() {
        return precision;
    }

    public Point getPoint() {
        return point;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}

