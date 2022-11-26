package dvn.coordinates_bot.coordinates.bot.service;

import dvn.coordinates_bot.coordinates.bot.entity.ObjectAddress;
import dvn.coordinates_bot.coordinates.bot.geocoderAPI.GeocoderApiCounter;
import dvn.coordinates_bot.coordinates.bot.telegramAPI.service.FileDownloader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainController {

    public static String prepareAnswerForMessage(String requestedAddress) {
        if (isRequestsLimitReached()) {
            return "Количество запросв превысило допустимые 900 шт. Новые запросы можно будет совершать завтра.";
        }

        ObjectAddress objectAddress = new ObjectAddress(requestedAddress);

        writeLogToConsole(objectAddress.getFoundAddress(), objectAddress.isPrecision(), objectAddress.getLatitude(), objectAddress.getLongitude());

        return "По адресу: " + objectAddress.getFoundAddress() + " "
                + "найдены " + (objectAddress.isPrecision() ? "точные" : "примерные")
                + " координаты " + objectAddress.getLatitude() + " с.ш. "
                + objectAddress.getLongitude() + " в. д.";
    }

    public static String fillExcelFile(String fileName, String fileId) {
        File file = openReceivedFile(fileName, fileId);
        XSSFWorkbook excelFile = openExcelXFileAndSheetForRead(file);
        XSSFSheet excelShit = excelFile.getSheetAt(0);
        Cell currentCell;
        int rowIndex = 1;
        while (((currentCell = getRequestedAddressFromCell(excelShit, rowIndex)) != null) && !isRequestsLimitReached()) {
            ObjectAddress objectAddress = new ObjectAddress(currentCell.getStringCellValue());
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

    private static File openReceivedFile(String fileName, String fileId) {
        File file = null;
        try {
            file = FileDownloader.downloadExcelFile(fileName, fileId);
        } catch (IOException e) {
            System.out.println("Файл не удалось скачать с сервера Telegram");
        }
        return file;
    }

    private static XSSFWorkbook openExcelXFileAndSheetForRead(File file) {
        XSSFWorkbook excelFile = null;
        try (FileInputStream fis = new FileInputStream(file)) {
            excelFile = new XSSFWorkbook(fis);
        } catch (IOException e) {
            System.out.println("Не удалось открыть Excel файл. Ошибка: " + e.getMessage());
        }
        return excelFile;
    }

    private static void writeToTable(XSSFSheet excelShit, ObjectAddress objectAddress, int rowIndex) {
        Cell cellToWrite1 = excelShit.getRow(rowIndex).createCell(6);
        cellToWrite1.setCellValue(objectAddress.getLatitude() + " " + objectAddress.getLongitude());
        Cell cellToWrite2 = excelShit.getRow(rowIndex).createCell(7);
        cellToWrite2.setCellValue("Найдены " + (objectAddress.isPrecision() ? "точные" : "примерные") + " координаты");
    }

    private static void writeToFile(XSSFWorkbook excelFile, File file) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            excelFile.write(fos);
        } catch (IOException e) {
            System.out.println("Не удалось записать координаты в файл. Ошибка: " + e.getMessage());
        }
    }

    private static Cell getRequestedAddressFromCell(XSSFSheet excelShit, int rowIndex) {
        try {
            return excelShit.getRow(rowIndex).getCell(3);
        } catch (NullPointerException e) {
            System.out.println("Все строки в файле прочитаны");
            return null;
        }
    }

    private static boolean isRequestsLimitReached() {
        return GeocoderApiCounter.getAPICounter().getCounter() > 900;
    }

    private static void writeLogToConsole(String foundAddress, boolean precision, double latitude, double longitude) {
        System.out.println("---------------------------------Answer start---------------------------------");
        System.out.println("Адрес: " + foundAddress);
        System.out.println("Точность найденного объекта " + (precision ? "точные" : "примерные"));
        System.out.println("Координаты: " + latitude + " с.ш. " + longitude + " в. д.");
        System.out.println("---------------------------------Answer end---------------------------------");
    }


}