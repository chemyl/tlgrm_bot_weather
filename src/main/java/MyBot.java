import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyBot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new MyBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendAnswer(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);                           //вкл возможность разметки
        sendMessage.setChatId(message.getChatId().toString());      //id чата, кому отвечает бот (общение инициализирует пользователь)
        sendMessage.setReplyToMessageId(message.getMessageId());    //id самого сообщения, на который надо ответить
        sendMessage.setText(text.replaceAll("([_*`])", "\\\\$1"));                                  //установить сообщению текст, который отправлялся в метод
        sendMessage.setParseMode("html");
        try {
            setButtons(sendMessage);                           //установка клавиатуры
            execute(sendMessage);                               //отпарвка сообщения
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //добавление экранных кнопок
    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();    //создание клавиатуры
        sendMessage.setReplyMarkup(replyKeyboardMarkup);                        //разметка для клавиатуры
        replyKeyboardMarkup.setSelective(true);                                 //показывать клавиатуру всем пользователям
        replyKeyboardMarkup.setResizeKeyboard(true);                            // указать клиенту подгонку клавиатуры
        replyKeyboardMarkup.setOneTimeKeyboard(false);                          //скрывать клавиатуру после нажатия(true) или нет (false)

        List<KeyboardRow> keyboardRowList = new ArrayList<>();              //список кнопок
        KeyboardRow keyboardFirstRow = new KeyboardRow();                   // строка кнопок

        keyboardFirstRow.add(new KeyboardButton("/что это?"));              //добавить кнопку на строку
        keyboardFirstRow.add(new KeyboardButton("/настройки"));          //добваить кнопку на строку

        keyboardRowList.add(keyboardFirstRow);                              //добавление единственной строки кнопок в сисок отображения
        replyKeyboardMarkup.setKeyboard(keyboardRowList);                   //установка списка снопок клавиатуре.


    }


    //метод для приёма обновлений через longPool /WebHook //longPool - очередь ожидающих запросов.
    // 1- отправляется запрос на сервер, потом соединение не закрывается сервером,пока не появляется новое событие.
    // Событие отправляется в ответ на запрос. И клиент тут же отправляет новый ожидающий запрос.
    //обработка вариантов текста
    public void onUpdateReceived(Update update) {
        //создание объекта меседж, помещая туда текст, полученный из сообщения (обновления от бота)
        Model model = new Model();                      //модель, которая будет хранить данные
        Message message = update.getMessage();          //инициализируем сообщение от пользователя
        if (message != null && message.hasText()) {     //проверяем сообщение
            switch (message.getText()) {
                case "/что это?":
                    sendAnswer(message, "Привет! Меня можно спросить о погоде в любом городе мира");
                    break;
                case "/настройки":
                    sendAnswer(message, "Обращаться -> @replicantDuke");
                    break;
                default:                                                        //если не кнопки, тогда try метод getWeather
                    try {
                        sendAnswer(message, Weather.getWeather(message.getText(), model));
                    } catch (IOException e) {
                        sendAnswer(message, "Введите название города!");    //кастоманая обработка исключения
                    }
            }
        }

    }

    // метод для того, чтобы вернуть имя ота при регистрации
    public String getBotUsername() {
        return "CheckTheWeather";
    }

    //токен, полученный при регистрации
    public String getBotToken() {
        return "1267107624:AAF8Vg_fIn6vCzoZJsCb1RRN84abS0Tu1hw";
    }
}
