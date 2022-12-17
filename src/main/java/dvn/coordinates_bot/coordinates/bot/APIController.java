package dvn.coordinates_bot.coordinates.bot;

import lombok.extern.log4j.Log4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Log4j
public class APIController {

    public static String getFileDataString(String linkForRequest) {

        log.info("Sending request to API. Link: " + linkForRequest);
        URL url = getURL(linkForRequest);

        HttpURLConnection connection = createConnection(url);
        String response = getResponse(connection);
        return response;
    }

    private static URL getURL(String linkForRequest) {
        log.info("Preparing URL");
        URL url = null;
        try {
            url = new URL(linkForRequest);
        } catch (MalformedURLException e) {
            log.error("Exception in Preparing URL " + e.getMessage());
        }
        return url;
    }

    private static HttpURLConnection createConnection(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
        } catch (IOException e) {
            log.error(e.getStackTrace());
        }
        return connection;
    }

    private static String getResponse(HttpURLConnection connection) {

        log.info("Getting response from API");
        StringBuffer response = new StringBuffer();
        try (BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = input.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (IOException e) {
            log.error("IOException in getResponse" + e.getStackTrace());
        }
        return response.toString();
    }

}
