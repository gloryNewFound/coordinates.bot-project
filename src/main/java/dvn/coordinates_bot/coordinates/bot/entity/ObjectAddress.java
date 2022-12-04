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

    private String precisionDetails;


    @Autowired
    private LinkForGeocoderApi linkForGeocoderApi;

    public ObjectAddress() {
    }

    public void fillAllObjectAddressFields(String foundAddress, String region) {
        this.requestedAddress = foundAddress;
        System.out.println(region);
        String responseFromApi = ApiController.getRequest(linkForGeocoderApi.getLinkForGeocoderApi(requestedAddress, (region.split(" "))[0]));
        GeocoderApiCounter.getAPICounter().incrementCounter();
        this.response = ResponseParser.pojoFromJsonGeocoderApiString(responseFromApi);
        this.precision = false;
        List<FeatureMemberItem> responsesToCheck = response.getResponse().getGeoObjectCollection().getFeatureMember();
        if (responsesToCheck.size() == 0) {
            this.precision = false;
            this.precisionDetails = null;
            this.point = null;
            this.foundAddress = null;
            this.latitude = 0;
            this.longitude = 0;
            return;

        }
            for (FeatureMemberItem item : responsesToCheck) {
                if (item.getGeoObject().getMetaDataProperty().getGeocoderMetaData().getPrecision().equals("exact")) {
                    System.out.println(item.toString());
                    this.precision = true;
                    this.precisionDetails = "Найдены точные координаты";
                    this.point = item.getGeoObject().getPoint();
                    this.foundAddress = item.getGeoObject().getMetaDataProperty().getGeocoderMetaData().getText();
                    break;
                } else {

                }
            }
            if (!precision) {
                FeatureMemberItem item = responsesToCheck.get(0);
                switch (item.getGeoObject().getMetaDataProperty().getGeocoderMetaData().getPrecision()) {

                    case "number":
                        this.precisionDetails = "Найден дом с указанным номером, но с другим номером строения или корпуса";
                        break;
                    case "near":
                        this.precisionDetails = "Найден дом с номером, близким к запрошенному";
                        break;
                    case "range":
                        this.precisionDetails = " Найдены приблизительные координаты запрашиваемого дома";
                        break;
                    case "street":
                        this.precisionDetails = "Найдена только улица";
                        break;
                    case "other":
                        this.precisionDetails = "Найдено примерное местоположение населенного пункта";
                        break;
                }
                System.out.println(item.toString());
                this.point = item.getGeoObject().getPoint();
                this.foundAddress = item.getGeoObject().getMetaDataProperty().getGeocoderMetaData().getText();
            }

            this.latitude = Double.parseDouble(point.getPos().split(" ")[1]);
            this.longitude = Double.parseDouble(point.getPos().split(" ")[0]);

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

    public String getPrecisionDetails() {
        return precisionDetails;
    }
}

