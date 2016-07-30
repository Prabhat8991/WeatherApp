package current.weather.com.weatherapp;

public class WeatherResponse {
    private Weather[] weather;

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }
}
