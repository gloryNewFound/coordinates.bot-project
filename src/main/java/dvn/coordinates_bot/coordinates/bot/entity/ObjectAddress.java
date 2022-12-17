package dvn.coordinates_bot.coordinates.bot.entity;

import dvn.coordinates_bot.coordinates.bot.APIController;
import dvn.coordinates_bot.coordinates.bot.geocoderAPI.GeocoderApiCounter;
import dvn.coordinates_bot.coordinates.bot.geocoderAPI.LinkForGeocoderApi;
import dvn.coordinates_bot.coordinates.bot.parser.ResponseParser;
import dvn.coordinates_bot.coordinates.bot.parser.pojo_response_geocoder_API.FeatureMemberItem;
import dvn.coordinates_bot.coordinates.bot.parser.pojo_response_geocoder_API.Point;
import dvn.coordinates_bot.coordinates.bot.parser.pojo_response_geocoder_API.ResponseFromGeocoderAPI;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
@Log4j
public class ObjectAddress {

    private ResponseFromGeocoderAPI responseFromGeocoderAPI;
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
        String regionString = getRegion(region);
            //Получаем из адреса и района ссылку для запроса на Геокодер API:
        String link = linkForGeocoderApi.getLinkForGeocoderApi(requestedAddress, regionString);

            //Получаем ответ от Геокодер API:
        String responseFromGeocoderApi = APIController.getFileDataString(link);

        //Увеличиваем счетчик сегодняшних запросов:
        GeocoderApiCounter.getAPICounter().incrementCounter();

            //Заносим полученный из парсера ответ в текущий объект:
        this.responseFromGeocoderAPI = ResponseParser.getResponseFromGeocoderAPI(responseFromGeocoderApi);

        this.precision = false; //
        List<FeatureMemberItem> responsesToCheck = responseFromGeocoderAPI.getResponse().getGeoObjectCollection().getFeatureMember();
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
                if (getFoundAddressPrecision(item).equals("exact")) {
                    System.out.println(item);
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
                switch (getFoundAddressPrecision(item)) {
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
                        this.precisionDetails = "Найдено местоположение населенного пункта";
                        break;
                }
                log.info(item.toString());
                this.point = item.getGeoObject().getPoint();
                this.foundAddress = item.getGeoObject().getMetaDataProperty().getGeocoderMetaData().getText();
            }

            this.latitude = Double.parseDouble(point.getPos().split(" ")[1]);
            this.longitude = Double.parseDouble(point.getPos().split(" ")[0]);

    }

    private String getFoundAddressPrecision(FeatureMemberItem item) {
        return item.getGeoObject().getMetaDataProperty().getGeocoderMetaData().getPrecision();
    }

    private String getRegion(String region) {
        if (region != null) {
            return region.split(" ")[0];
        }
        else {
            return null;
        }
    }

    public String getFoundAddress() {
        return foundAddress;
    }

    public boolean isPrecision() {
        return precision;
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

