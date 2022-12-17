package dvn.coordinates_bot.coordinates.bot.parser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dvn.coordinates_bot.coordinates.bot.parser.pojo_response_geocoder_API.ResponseFromGeocoderAPI;
import dvn.coordinates_bot.coordinates.bot.parser.pojo_response_tgbot_API.FileFromChat;
import lombok.extern.log4j.Log4j;

import java.io.IOException;

@Log4j
public class ResponseParser {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static ResponseFromGeocoderAPI getResponseFromGeocoderAPI(String response) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ResponseFromGeocoderAPI pojoFromJsonString = null;
        try {
            pojoFromJsonString = objectMapper.readValue(response, ResponseFromGeocoderAPI.class);
        } catch (IOException e) {
            log.error("Не удалось распарсить JSON" + e.getMessage());;
        }
        return pojoFromJsonString;
    }

    public static FileFromChat getFileFromChat(String response) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        FileFromChat pojoFromJsonString = null;
        try {
            pojoFromJsonString = objectMapper.readValue(response, FileFromChat.class);
        } catch (IOException e) {
            log.error("Не удалось распарсить JSON: " + e.getMessage());;
        }
        return pojoFromJsonString;
    }
}
