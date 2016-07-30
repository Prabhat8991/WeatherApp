package current.weather.com.weatherapp;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by admin on 10/18/2015.
 */
public interface WeatherService {


    @GET("/weather?appid=f84b55709c46a824fea22472dda4ef28")
    void getWeather(@Query("q") String cityName, Callback<WeatherResponse> cb);


}
