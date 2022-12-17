package dvn.coordinates_bot.coordinates.bot.telegramAPI;

import dvn.coordinates_bot.coordinates.bot.APIController;
import dvn.coordinates_bot.coordinates.bot.parser.ResponseParser;
import lombok.extern.log4j.Log4j;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Properties;

@Log4j
public class FileDownloadService {
    public static File downloadFileFromChat(String fileName, String fileId)  {

        URL fileURL = getFileURL(fileId);

        log.info("-----------------------------------------------");
        log.info("File downloading " + fileURL.getPath());
        log.info("File name: " + fileName);

        try (
                ReadableByteChannel readChanel = Channels.newChannel(fileURL.openStream());
                FileOutputStream fos = new FileOutputStream(fileName);
                FileChannel writeChannel = fos.getChannel()
        ) {
            writeChannel.transferFrom(readChanel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            log.error(e.getStackTrace());
        }

        log.info("File downloaded");
        log.info("-----------------------------------------------");
        System.out.println();

        return new File(fileName);
    }

    private static URL getFileURL(String fileId) {
            //Создаем объект свойств для чтения параметров из application.properties
        Properties properties = getApplicationPropertiesFile();

            //Собираем ссылку для запроса данных о файле у Телеграм API
        String fileDataLink = getFileDataLink(fileId, properties);

            //Отправка запроса для получения данных о файле
        String fileDataResponseString = APIController.getFileDataString(fileDataLink);

            //Парсинг JSON с данными о файле для формирования ссылки на скачивания
        String filePath = ResponseParser.getFileFromChat(fileDataResponseString).getResult().getFilePath();

            //Сбор ссылки для скачивания файла
        String downloadFileLink = getDownloadFileLink(filePath, properties);

        URL url = null;
        try {
            url = new URL(downloadFileLink);
        } catch (MalformedURLException e) {
            log.error(e.getStackTrace());
        }
        return url;
    }

    private static Properties getApplicationPropertiesFile() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getPropertiesFilePath()));
        } catch (IOException e) {
            log.error(e.getStackTrace());
        }

        return properties;
    }

    private static String getFileDataLink(String fileId, Properties properties) {
        return properties.getProperty("telegramAPI.baseLink")
                + "/bot"
                + properties.getProperty("bot.token")
                + "/getFile?file_id=" + fileId;
    }

    private static String getPropertiesFilePath() {
        return "src" + File.separator
                + "main"+ File.separator
                + "resources" + File.separator
                + "application.properties";
    }

    private static String getDownloadFileLink(String filePath, Properties properties) {
        return properties.getProperty("telegramAPI.baseLink")
                + "/file/bot"
                + properties.getProperty("bot.token")
                + "/" + filePath;
    }
}