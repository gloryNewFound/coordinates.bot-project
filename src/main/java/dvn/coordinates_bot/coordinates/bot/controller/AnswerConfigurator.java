package dvn.coordinates_bot.coordinates.bot.controller;

import dvn.coordinates_bot.coordinates.bot.entity.ObjectAddress;
import dvn.coordinates_bot.coordinates.bot.geocoderAPI.GeocoderApiCounter;
import dvn.coordinates_bot.coordinates.bot.telegramAPI.service.FileDownloader;
import lombok.extern.log4j.Log4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
@Log4j
public class AnswerConfigurator {

    @Autowired
    private ObjectAddress objectAddress;

    public AnswerConfigurator(){
    }

    public String prepareAnswerForMessage(String requestedAddress) {
        if (isRequestsLimitReached()) {
            return "Количество запросв превысило допустимые 900 шт. Новые запросы можно будет совершать завтра.";
        }
        objectAddress.fillAllObjectAddressFields(requestedAddress, null);
        writeLogToConsole(objectAddress.getFoundAddress(), objectAddress.isPrecision(), objectAddress.getLatitude(), objectAddress.getLongitude());

        return "По адресу: " + objectAddress.getFoundAddress() + " "
                + objectAddress.getPrecisionDetails()
                + " координаты " + objectAddress.getLatitude() + " с.ш. "
                + objectAddress.getLongitude() + " в. д.";
    }

    public String fillExcelFile(String fileName, String fileId) {
        File file = openReceivedFile(fileName, fileId);
        XSSFWorkbook excelFile = openExcelXFileAndSheetForRead(file);
        XSSFSheet excelShit = excelFile.getSheetAt(0);
        Cell cellWithAddress;
        int rowIndex = 1;
        while (((cellWithAddress = getRequestedAddressFromCell(excelShit, rowIndex)) != null) && !isRequestsLimitReached()) {
            String region = getRequestedRegionFromCell(excelShit, rowIndex);
            objectAddress.fillAllObjectAddressFields(cellWithAddress.getStringCellValue(), region);
            writeToTable(excelShit, objectAddress, rowIndex);
            writeLogToConsole(objectAddress.getFoundAddress(), objectAddress.isPrecision(), objectAddress.getLatitude(), objectAddress.getLongitude());
            rowIndex++;
        }
        writeToFile(excelFile, file);

        if (isRequestsLimitReached()) {
            System.out.println("Количество запросв превысило допустимые 900 шт. Новые запросы можно будет совершать завтра.");
        }
        return "Записываю координаты в файл";
    }

    private File openReceivedFile(String fileName, String fileId) {
        File file = null;
        try {
            file = FileDownloader.downloadExcelFile(fileName, fileId);
        } catch (IOException e) {
            System.out.println("Файл не удалось скачать с сервера Telegram");
        }
        return file;
    }

    private XSSFWorkbook openExcelXFileAndSheetForRead(File file) {
        XSSFWorkbook excelFile = null;
        try (FileInputStream fis = new FileInputStream(file)) {
            excelFile = new XSSFWorkbook(fis);
        } catch (IOException e) {
            System.out.println("Не удалось открыть Excel файл. Ошибка: " + e.getMessage());
        }
        return excelFile;
    }

    private void writeToTable(XSSFSheet excelShit, ObjectAddress objectAddress, int rowIndex) {
        Cell cellToWrite1 = excelShit.getRow(rowIndex).createCell(6);
        cellToWrite1.setCellValue(objectAddress.getLatitude() + " " + objectAddress.getLongitude());
        Cell cellToWrite2 = excelShit.getRow(rowIndex).createCell(7);
        cellToWrite2.setCellValue(objectAddress.getPrecisionDetails());
    }

    private void writeToFile(XSSFWorkbook excelFile, File file) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            excelFile.write(fos);
        } catch (IOException e) {
            System.out.println("Не удалось записать координаты в файл. Ошибка: " + e.getMessage());
        }
    }

    private Cell getRequestedAddressFromCell(XSSFSheet excelShit, int rowIndex) {
        try {
            return excelShit.getRow(rowIndex).getCell(3);
        } catch (NullPointerException e) {
            System.out.println("Все строки в файле прочитаны");
            return null;
        }
    }

    private String getRequestedRegionFromCell(XSSFSheet excelShit, int rowIndex) {
//        try {
            String res = excelShit.getRow(rowIndex).getCell(2).getStringCellValue();
            return res.split(" ")[0];
//        } catch (NullPointerException e) {
//            log.info("Для данного запроса область не указана");
//            return null;
//        }
    }

    private boolean isRequestsLimitReached() {
        return GeocoderApiCounter.getAPICounter().getCounter() > 900;
    }

    private void writeLogToConsole(String foundAddress, boolean precision, double latitude, double longitude) {
        log.info("---------------------------------Answer start---------------------------------");
        log.info("Адрес: " + foundAddress);
        log.info("Точность найденного объекта " + (precision ? "точные" : "примерные"));
        log.info("Координаты: " + latitude + " с.ш. " + longitude + " в. д.");
        log.info("---------------------------------Answer end---------------------------------");
    }


}
