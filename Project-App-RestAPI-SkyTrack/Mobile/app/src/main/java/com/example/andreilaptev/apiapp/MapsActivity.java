package com.example.andreilaptev.apiapp;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
/*Developed by Manoel B. Burgos Filho*/
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    List<Address> geocodeResults;
    double latitude ;
    double longitude ;
    String location;
    String locInfo="";
    String store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        final Geocoder coder = new Geocoder(getApplicationContext());
        location = getIntent().getExtras().getString("airport");

        try {
            geocodeResults =
                    coder.getFromLocationName(location, 3);
            Iterator<Address> locations = geocodeResults.iterator();
            while (locations.hasNext()) {
                Address loc = locations.next();
                latitude = loc.getLatitude();
                longitude = loc.getLongitude();

                int addIdx = loc.getMaxAddressLineIndex();
                for (int idx = 0; idx <= addIdx; idx++){
                    String addLine = loc.getAddressLine(idx);
                    if (!addLine.equals(null))
                    {
                        locInfo += String.format("%s", addLine); }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng locationShowroom = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(locationShowroom).title(location).snippet("Details: "+locInfo)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationShowroom,10));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 200, null);
    }
}
