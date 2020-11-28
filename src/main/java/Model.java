public class Model {

    // Модель объект, который будет возвращаться пользователю. На основе выбранных полей из возвращаемого json
    // после обновления (получения от пользователя запроса), отправляем на сервер OpenWeather запрос, полученный ответ превращаем в модель данных (объект) и отправляем пользователю в интерфейс бота.

    private String name;
    private Double temp;
    private Double humidity;
    private String icon;
    private String main;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
