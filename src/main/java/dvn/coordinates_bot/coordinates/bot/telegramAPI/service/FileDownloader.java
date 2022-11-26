package dvn.coordinates_bot.coordinates.bot.telegramAPI.service;

import dvn.coordinates_bot.coordinates.bot.controller.ApiController;
import dvn.coordinates_bot.coordinates.bot.parser.ResponseParser;
import lombok.extern.log4j.Log4j;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

@Log4j
public class FileDownloader {

    private static final String BASE_URL = "https://api.telegram.org";

    private static final String API_TOKEN = "5505170056:AAG-H1biJ_jMUMIuZrMa8yjVFRPnDNIZ4mA";

    public static File downloadExcelFile(String fileName, String fileId) throws IOException {

        String apiResponse = ApiController.getRequest(BASE_URL + "/bot" + API_TOKEN + "/getFile?file_id=" + fileId);
        String urlFileDownloading = BASE_URL + "/file/bot" + API_TOKEN + "/" + ResponseParser.pojoFromJsonTGBotApiString(apiResponse).getResult().getFilePath();
        log.debug("-----------------------------------------------");
        log.debug("File downloading");
        log.debug(urlFileDownloading);
        URL url = new URL(urlFileDownloading);
//        BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
//        FileOutputStream fos = new FileOutputStream(".\\forFiles\\" + fileName);
//        byte[] buffer = new byte[1024];
//        int count=0;
//        while((count = inputStream.read(buffer,0,1024)) != -1) {
//            fos.write(buffer, 0, count);
//        }
//        fos.close();
//        inputStream.close();

        String fullFileName = fileName;
        System.out.println(fullFileName);
        ReadableByteChannel readChanel = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(fullFileName);
        FileChannel writeChannel = fos.getChannel();
        writeChannel.transferFrom(readChanel, 0, Long.MAX_VALUE);
        fos.close();
        writeChannel.close();
        readChanel.close();
        log.debug("File downloaded");
        log.debug("-----------------------------------------------");
        return new File(fullFileName);
    }


}
