import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {
    // класс с обработкой погоды

    public static String makeIcon(String icon) {
        String currentIcon = "https://openweathermap.org/img/w/" + icon + ".png";
        return currentIcon;
    }

    public static String getWeather(String message, Model model) throws IOException {
        // для извелчения данных, необходимо отправить запрос на api
        // класс url для простого извелчения

        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + message + "API TOKEN FROM SITE");

        // чтобы прочитать содержимое того, что пришло по url, используется сканер.
        Scanner in = new Scanner((InputStream) url.getContent());       //из url получаем контекст в строковом типе и приводим к типу Stream
        String result = "";                                             //строка результата из потока
        while (in.hasNext()) {                                          // в цикле, пока из потока есть что прочитать, зыпасываем его в результат
            result += in.nextLine();                                    //считывается последовательность из потоак и сохраняется в переменную
        }

        JSONObject object = new JSONObject(result);                  //кастим полученную строку result в JSON объект
        model.setName(object.getString("name"));                //в объект model сетим значения из Json объекта

        JSONObject main = object.getJSONObject("main");              //из общего json файла, получаем вложенный блок main
        model.setTemp(main.getDouble("temp"));                  //из вложенного объекта json забираем строку температуры
        model.setHumidity(main.getDouble("humidity"));          //из вложенного объекта json забираем строку температуры

        JSONArray getArray = object.getJSONArray("weather");    //получаем все вложенные элементы массива weather
        for (int i = 0; i < getArray.length(); i++) {               //инициализация каждого блока массива weather
            JSONObject obj = getArray.getJSONObject(i);
            model.setIcon((String) obj.get("icon"));                // получем код изображения
            model.setMain((String) obj.get("main"));                // получаем небо
        }

        return "Температура -> " + model.getTemp() + " C" + "\n" +
                "Влажность -> " + model.getHumidity() + " %" + "\n" +
                "Небо -> " + model.getMain() + "\n" +
                "Выглядит как-то так -> " + makeIcon(model.getIcon()) + "\n";
    }
}
