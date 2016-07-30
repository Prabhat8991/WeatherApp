package current.weather.com.weatherapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {
    ListView weatherDetails;
    EditText city;
    String cityName;
    Button weather;
    TextView details;
    String api="f84b55709c46a824fea22472dda4ef28";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city=(EditText)findViewById(R.id.editText);
        details= (TextView) findViewById(R.id.textView2);
        weather= (Button) findViewById(R.id.buttonWeather);
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestAdapter restAdapter  = new RestAdapter.Builder()
                        .setEndpoint("http://api.openweathermap.org/data/2.5")
                        .build();
                WeatherService service=restAdapter.create(WeatherService.class);
                cityName=city.getText().toString();
                service.getWeather(cityName ,new Callback<WeatherResponse>() {


                    @Override
                    public void success(WeatherResponse weather, Response response) {
                         details.setText(weather.getWeather()[0].getDescription());
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        Toast.makeText(getApplicationContext(),"Some error occured",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });













    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
