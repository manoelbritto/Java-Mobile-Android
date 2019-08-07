package com.example.andreilaptev.apiapp;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
/*Developed by Manoel B. Burgos Filho*/
public class SMSActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mapView;
    private static final String MAP_BUNDLE_KEY = "MapViewBundleKey";
    Double latitude;
    Double longitude;
    String country;
    String altitude;
    String speed;
    String[] valueFind;
    List<Address> geocodeResults;
    EditText phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        Bundle mapBundle = null;
        if (savedInstanceState != null) {
            mapBundle = savedInstanceState.getBundle(MAP_BUNDLE_KEY);
        }
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(mapBundle);
        mapView.getMapAsync(this);
        final Geocoder coder = new Geocoder(getApplicationContext());
        phone = (EditText)findViewById(R.id.editTextPhone);

        valueFind = getIntent().getExtras().getStringArray("gps_list");
        latitude = Double.valueOf(valueFind[0]);
        longitude = Double.valueOf(valueFind[1]);
        altitude = valueFind[2];
        speed = valueFind[3];

        try {
            geocodeResults =
                    coder.getFromLocation(latitude, longitude,1);
            Iterator<Address> locations = geocodeResults.iterator();
            while (locations.hasNext()) {
                Address loc = locations.next();
                country = loc.getCountryName();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker
        LatLng locationShowroom = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(locationShowroom).title("Plane").snippet("Details: Altitude: "+ altitude + " Speed: "+speed + " Country: " + country)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationShowroom,10));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 200, null);
    }

    public void sendSMS(View view) {
        String number = phone.getText().toString();
        if (!number.equals("")){
            SMS sms = new SMS(this,number, "Plane - "+ "Details: Altitude: "+ altitude + " Speed: "+speed + " Country: " + country);
            sms.send();
        }
        else{
            Toast.makeText(SMSActivity.this, "Type a Phone", Toast.LENGTH_LONG).show();
        }

    }
}
