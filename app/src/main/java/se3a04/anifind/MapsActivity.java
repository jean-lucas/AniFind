package se3a04.anifind;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





        Log.d("MAP", "hello");
        Toast.makeText(MapsActivity.this, "Hello from Map", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        Location loc = new Location("default");
        loc.setLatitude(-1);
        loc.setLongitude(-1);

        LocationManager locMan = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);


        List<String> providers = locMan.getProviders(true);


        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
           ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }



        for (String p: providers) {
            Log.d("loc", "result = " + locMan.getLastKnownLocation(p));
            if (locMan.getLastKnownLocation(p) != null) {
                loc = new Location(locMan.getLastKnownLocation(p));
                break;
            }
        }


        Geocoder geo = new Geocoder(this.getApplicationContext(), Locale.getDefault());

        Log.d("LAT",String.valueOf(loc.getLatitude()) + " - " + String.valueOf(loc.getLongitude()));
        String[] address = {String.valueOf(loc.getLatitude()), String.valueOf(loc.getLongitude()), "", ""};
        try {
            List<Address> addr = geo.getFromLocation(loc.getLatitude(), loc.getLongitude(), 5);

            if (addr.size() > 0) {
               for (Address a: addr) {
                   if (a.getLocality() != null) address[2] = a.getLocality();
                   if (a.getFeatureName() != null) address[3] = a.getFeatureName();
               }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        Intent intent = new Intent();
        intent.putExtra("loc",address);
        setResult(1, intent);
        finish();
    }



}
