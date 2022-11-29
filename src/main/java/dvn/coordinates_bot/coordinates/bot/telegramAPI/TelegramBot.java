package dvn.coordinates_bot.coordinates.bot.telegramAPI;

import dvn.coordinates_bot.coordinates.bot.geocoderAPI.GeocoderApiCounter;
import dvn.coordinates_bot.coordinates.bot.controller.AnswerConfigurator;
import dvn.coordinates_bot.coordinates.bot.telegramAPI.config.BotConfig;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.Date;


@Component
@Log4j
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    private boolean flagByMessage;

    private boolean flagByFile;

    @Autowired
    private AnswerConfigurator answerConfigurator;

    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = update.getMessage().getChatId();
        String message = update.getMessage().getText();
        log.debug(message);
        if (flagByFile) {
            if (update.getMessage().hasDocument()) {
                System.out.println(new Date());
                System.out.println("File received");
                sendMessage(chatId, "Получен файл");
                byTableAnswer(chatId, update.getMessage());
                flagByFile = false;
                return;
            } else if (update.getMessage().getText().equals("/restart")) {
                flagByFile = false;
            } else {
                sendMessage(chatId, "Пришлите файл формата .xlsx с адресами, в котором адрес находится в столбце \"D\"");
                return;
            }
        }

        if(update.hasMessage() && update.getMessage().hasText() ) {

            if (flagByMessage && (message.charAt(0) != '/') ) {
                if (update.getMessage().hasText()) {
                    byMessageAnswer(chatId, message);
                    sendMessage(chatId, "Введите следующий адрес или введите /restart, чтобы выбрать другую команду");
                    return;
                } else {
                    sendMessage(chatId, "Введите адрес для получения координат");
                }
            }

            if (message.charAt(0) == '/') {
                switch (message) {
                    case "/start":
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        break;

                    case "/bymessage":
                        sendMessage(chatId, "Введите адрес для получения координат");
                        sendMessage(chatId, "Желательно вводить адрес в формате:");
                        sendMessage(chatId, "(Страна), (область), (округ), населённый пункт, (уточнение), улица, номер дома");
                        sendMessage(chatId, "в скобках указаны необязательные для ввода параметры");
                        flagByMessage = true;
                        break;

                    case "/bytable":
                        sendMessage(chatId, "Пришлите файл формата .xlsx с адресами, в котором адрес находится в столбце \"D\"");
                        flagByFile = true;
                        break;

                    case "/getcounter":
                        sendMessage(chatId, "Сегодня совершено " + GeocoderApiCounter.getAPICounter().getCounter() + " запросов из 900.");
                        break;

                    case "/restart":
                        sendMessage(chatId, "Выберите действие заново");
                        break;
                }
            }
        }
    }

    private void startCommandReceived(long chatId, String userFirstName) {
        String answer = "Привет, " + userFirstName;
        sendMessage(chatId, answer);
        sendMessage(chatId, "Выберите действие");
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        log.info(message.getText());

        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Message was not executed: " + e.getMessage());
        }
    }

    private void sendFile(long chatId, File file) {
        InputFile inputFile = new InputFile(file);
        SendDocument sendDocument = new SendDocument(String.valueOf(chatId), inputFile);
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void byMessageAnswer(long chatId, String address) {
        sendMessage(chatId, answerConfigurator.prepareAnswerForMessage(address));
    }

    private void byTableAnswer(long chatId, Message message) {
        String fileDownloadStatus = null;
        fileDownloadStatus = answerConfigurator.fillExcelFile(message.getDocument().getFileName(), message.getDocument().getFileId());
        sendMessage(chatId, fileDownloadStatus);
        sendMessage(chatId, "Вот заполенный файл");
        sendFile(chatId, new File(message.getDocument().getFileName()));
    }
}
