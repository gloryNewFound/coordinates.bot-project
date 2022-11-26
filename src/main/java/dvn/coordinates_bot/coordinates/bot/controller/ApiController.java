package dvn.coordinates_bot.coordinates.bot.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiController {

    public static String getRequest(String linkForRequest) {

        System.out.println("Sending request to API. Link: " + linkForRequest);
        URL url = getURL(linkForRequest);

        HttpURLConnection connection = createConnection(url);
        String response = getResponse(connection);
        return response;
    }

    private static URL getURL(String linkForRequest) {
        System.out.println("Preparing URL");
        URL url = null;
        try {
            url = new URL(linkForRequest);
        } catch (MalformedURLException e) {
            System.out.println("Exception in Preparing URL " + e.getMessage());
        }
        return url;
    }

    private static HttpURLConnection createConnection(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
        return connection;
    }

    private static String getResponse(HttpURLConnection connection) {

        System.out.println("Getting response from API");
        StringBuffer response = new StringBuffer();
        try (BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = input.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (IOException e) {
            System.out.println("IOException in getResponse" + e.getStackTrace());
        }
        return response.toString();
    }

}
