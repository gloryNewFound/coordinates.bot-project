package dvn.coordinates_bot.coordinates.bot.telegramAPI;

import dvn.coordinates_bot.coordinates.bot.geocoderAPI.GeocoderApiCounter;
import dvn.coordinates_bot.coordinates.bot.services.AnswerConfigurator;
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
        log.debug("Received new message: " + message);

        if (flagByFile && (message == null)) {
            if (update.getMessage().hasDocument()) {
                fileReceived(chatId, update); //Starting to handle the file and stop waiting for a new message
                return;
            } else if (message.equals("/restart")) {
                flagByFile = false; //Restarting commandline and starting to wait for a new message
                return;
            } else if (message.equals("/getdraftfile")) {
                sendDraftTableFile(chatId, update.getMessage());
                return;
            } else {
                byTableCommandReceived(chatId);
                return;
            }
        }

        if(update.hasMessage() && update.getMessage().hasText() ) {
            if (flagByMessage && (message.charAt(0) != '/') ) {
                if (update.getMessage().hasText()) {
                    byMessageAnswer(chatId, message);
                    return;
                } else {
                    sendMessage(chatId, "Введите адрес для получения координат");
                    return;
                }
            }
            if (message.charAt(0) == '/') {
                switch (message) {
                    case "/start":
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        break;
                    case "/bymessage":
                        byMessageCommandReceived(chatId);
                        break;
                    case "/bytable":
                        byTableCommandReceived(chatId);
                        break;
                    case "/getcounter":
                        getCounterCommandReceived(chatId);
                        break;
                    case "/getdraftfile":
                        sendDraftTableFile(chatId, update.getMessage());
                        break;
                    case "/restart":
                        restartCommandReceived(chatId);
                }
            }
        }
    }

    private void fileReceived(long chatId, Update update) {
        log.info("File received");
        sendMessage(chatId, "Получен файл");
        byTableAnswer(chatId, update.getMessage());
        flagByFile = false;
    }

    private void restartCommandReceived(long chatId) {
        sendMessage(chatId, "Выберите действие заново");
        flagByFile = false;
        flagByMessage = false;
    }

    private void getCounterCommandReceived(long chatId) {
        String quantityOfRequests = String.valueOf(GeocoderApiCounter.getAPICounter().getCounter());
        sendMessage(chatId, "Сегодня совершено " + quantityOfRequests + " запросов из 900.");
    }

    private void byTableCommandReceived(long chatId) {
        sendMessage(chatId, "Пришлите файл формата .xlsx с адресами, в котором адрес находится в столбце \"D\"");
        sendMessage(chatId, "Запросить файл для заполнения адресами можно командой /getdraftfile");
        flagByFile = true;
    }

    private void byMessageCommandReceived(long chatId) {
        sendMessage(chatId, "Введите адрес для получения координат");
        sendMessage(chatId, "Желательно вводить адрес в формате:");
        sendMessage(chatId, "(Страна), (область), (округ), населённый пункт, (уточнение), улица, номер дома");
        sendMessage(chatId, "в скобках указаны необязательные для ввода параметры");
        flagByMessage = true;
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
        log.info("Sending message: " + text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Message was not executed: " + e.getMessage());
        }
    }

    private void sendFile(long chatId, File file) {
        InputFile inputFile = new InputFile(file);
        SendDocument sendDocument = new SendDocument(String.valueOf(chatId), inputFile);
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            log.error("Can't send the file: " + e.getMessage());
        }
    }

    private void byMessageAnswer(long chatId, String address) {
        sendMessage(chatId, answerConfigurator.answerForMessage(address));
        sendMessage(chatId, "Введите следующий адрес или введите /restart," +
                " чтобы выбрать другую команду");
    }

    private void byTableAnswer(long chatId, Message message) {
        String fileDownloadStatus = answerConfigurator.fillExcelFile(
                message.getDocument().getFileName(),
                message.getDocument().getFileId(),
                config);

        sendMessage(chatId, fileDownloadStatus);
        sendMessage(chatId, "Вот заполенный файл");
        sendFile(chatId, new File(message.getDocument().getFileName()));
    }

    private void sendDraftTableFile(long chatId, Message message) {
        sendMessage(chatId, "Заполните файл адресами и районами, а потом пришлите его мне");
        sendFile(chatId, new File("Draft Table.xlsx"));
    }
}
