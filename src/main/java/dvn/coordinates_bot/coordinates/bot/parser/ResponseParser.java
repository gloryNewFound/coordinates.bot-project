package dvn.coordinates_bot.coordinates.bot.parser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dvn.coordinates_bot.coordinates.bot.parser.pojoResponseGeocoderAPI.Response;
import dvn.coordinates_bot.coordinates.bot.parser.pojoResponseTGBotAPI.FileFromChat;

import java.io.IOException;

public class ResponseParser {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static Response pojoFromJsonGeocoderApiString(String response) {

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Response pojoFromJsonString = null;
        try {
            pojoFromJsonString = objectMapper.readValue(response, Response.class);
        } catch (IOException e) {
            System.out.println("Не удалось распарсить JSON" + e.getMessage());;
        }
        return pojoFromJsonString;
    }

    public static FileFromChat pojoFromJsonTGBotApiString(String response) {

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        FileFromChat pojoFromJsonString = null;
        try {
            pojoFromJsonString = objectMapper.readValue(response, FileFromChat.class);
        } catch (IOException e) {
            System.out.println("Не удалось распарсить JSON: " + e.getMessage());;
        }
        return pojoFromJsonString;
    }

}
