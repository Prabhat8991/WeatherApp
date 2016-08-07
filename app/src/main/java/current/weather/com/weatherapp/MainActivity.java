package current.weather.com.weatherapp;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    ListView weatherDetails;
    EditText city;
    String cityName;
    Button weather;
    TextView details;
    private double lat,lon;
    private GoogleApiClient mGoogleApiClient;


    String api="f84b55709c46a824fea22472dda4ef28";
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city=(EditText)findViewById(R.id.editText);
        details= (TextView) findViewById(R.id.textView2);
        weather= (Button) findViewById(R.id.buttonWeather);
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestAdapter restAdapter  = new RestAdapter.Builder()
                     /*   .setEndpoint("api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}")*/
                        .setEndpoint("http://api.openweathermap.org/data/2.5")
                        .build();
                WeatherService service=restAdapter.create(WeatherService.class);
               // cityName=city.getText().toString();

                service.getWeather(lat,lon,new Callback<WeatherResponse>() {


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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    1 );
        }

        if ( Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
            lat=mLastLocation.getLatitude();
                lon=mLastLocation.getLongitude();
                Toast.makeText(getApplicationContext(),"Latitude:" + lat + " Longitude:" + lon,Toast.LENGTH_SHORT).show();

            }
        }
    }






    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}
