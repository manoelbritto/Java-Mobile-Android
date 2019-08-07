package com.example.andreilaptev.apiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
/*Developed by Manoel B. Burgos Filho*/
public class SelectAirportActivity extends AppCompatActivity {
    ArrayAdapter<CharSequence> adapter;
    Spinner country1;
    Spinner city1;
    Spinner airport1;
    //dest
    Spinner country2;
    Spinner city2;
    Spinner airport2;
    String filter [] = new String [3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_airport);
        // origin
        country1 = (Spinner)findViewById(R.id.spinnerContryOrigin);
        city1 = (Spinner)findViewById(R.id.spinnerCityOrigin);
        airport1 = (Spinner)findViewById(R.id.spinnerAirportOrigin);
        //dest
        country2 = (Spinner)findViewById(R.id.spinnerContryDest);
        city2 = (Spinner)findViewById(R.id.spinnerCityDest);
        airport2 = (Spinner)findViewById(R.id.spinnerAirportDest);
        ///Method to fill the spinner according values selected
        OriginDest();
    }

    public void findAirport (View v){
        Object o_airp1 = airport1.getSelectedItem();
        Object o_airp2 = airport2.getSelectedItem();
        Intent i = new Intent(SelectAirportActivity.this,MapsActivity.class);

        if (v.getId()==R.id.imgLocateDepart){
            if (o_airp1==null){
                Toast.makeText(SelectAirportActivity.this, "Select at least one country", Toast.LENGTH_LONG).show();
            }
            else{
                String airp1="";
                airp1 = airport1.getSelectedItem().toString();
                i.putExtra("airport", airp1);
                startActivity(i);
            }
        }
        else if (v.getId()==R.id.imgLocateArriv){
            if (o_airp2==null){
                Toast.makeText(SelectAirportActivity.this, "Select at least one country", Toast.LENGTH_LONG).show();
            }
            else{
                String airp2="";
                airp2 = airport2.getSelectedItem().toString();
                i.putExtra("airport", airp2);
                startActivity(i);
            }
        }

    }

    // get the airport code to search by rest api airport
    public void Search(View v){
        String airp1="";
        String airp2="";
        int value1;
        int value2;
        Intent i = new Intent(SelectAirportActivity.this,ListFlightsActivity.class);
        Arrays.fill( filter, null );
        try{
            Object o_airp1 = airport1.getSelectedItem();
            Object o_airp2 = airport2.getSelectedItem();

            if (o_airp1==null && o_airp2 ==null){
                Toast.makeText(SelectAirportActivity.this, "Select at least one country", Toast.LENGTH_LONG).show();
            }
            else if (o_airp1!=null && o_airp2==null)
            {
                airp1 = airport1.getSelectedItem().toString();
                value1 = airp1.length();
                airp1 = airp1.substring(value1-3,value1);
                filter[0] = airp1;
                filter[2] = "departure";
                i.putExtra("airportCode", filter);
                startActivity(i);
            } else if (o_airp2!=null && o_airp1==null){
                airp2 = airport2.getSelectedItem().toString();
                value2 = airp2.length();
                airp2 = airp2.substring(value2-3,value2);
                filter[1] = airp2;
                filter[2] = "arrival";
                i.putExtra("airportCode", filter);
                startActivity(i);
            } else{
                 if (o_airp1.toString().equals(o_airp2.toString())){
                    Toast.makeText(SelectAirportActivity.this, "Countries must be different", Toast.LENGTH_LONG).show();
                }
                else{
                     airp1 = airport1.getSelectedItem().toString();
                     airp2 = airport2.getSelectedItem().toString();
                     value1 = airp1.length();
                     value2 = airp2.length();
                     airp1 = airp1.substring(value1-3,value1);
                     airp2 = airp2.substring(value2-3,value2);
                     filter[0] = airp1;
                     filter[1] = airp2;
                     filter[2] = "both";
                     i.putExtra("airportCode", filter);
                     startActivity(i);
                 }

                }
            }
         catch (Exception e){
            Toast.makeText(SelectAirportActivity.this, "Try again", Toast.LENGTH_LONG).show();
        }

    }
    public void OriginDest (){
        /// country Origin to find the City
        country1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String item = parent.getItemAtPosition(pos).toString();
                switch(item){
                    case "Canada" :
                        adapter = ArrayAdapter.createFromResource(SelectAirportActivity.this, R.array.Canada, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        city1.setAdapter(adapter);
                        break;
                    case "Brazil" :
                        adapter = ArrayAdapter.createFromResource(SelectAirportActivity.this, R.array.Brazil, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        city1.setAdapter(adapter);
                        break;
                    case "Russia" :
                        adapter = ArrayAdapter.createFromResource(SelectAirportActivity.this, R.array.Russia, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        city1.setAdapter(adapter);
                        break;
                    default:
                        city1.setAdapter(null);
                        airport1.setAdapter(null);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        /// country Destination to find the City
        country2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String item = parent.getItemAtPosition(pos).toString();
                switch(item){
                    case "Canada" :
                        adapter = ArrayAdapter.createFromResource(SelectAirportActivity.this, R.array.Canada, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        city2.setAdapter(adapter);
                        break;
                    case "Brazil" :
                        adapter = ArrayAdapter.createFromResource(SelectAirportActivity.this, R.array.Brazil, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        city2.setAdapter(adapter);
                        break;
                    case "Russia" :
                        adapter = ArrayAdapter.createFromResource(SelectAirportActivity.this, R.array.Russia, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        city2.setAdapter(adapter);
                        break;
                    default:
                        city2.setAdapter(null);
                        airport2.setAdapter(null);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /// City Origin to find the airport

        city1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String item = parent.getItemAtPosition(pos).toString();
                switch(item){
                    case "Toronto" :
                        adapter = ArrayAdapter.createFromResource(SelectAirportActivity.this, R.array.Toronto, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        airport1.setAdapter(adapter);
                        break;
                    case "Vancouver" :
                        adapter = ArrayAdapter.createFromResource(SelectAirportActivity.this, R.array.Vancouver, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        airport1.setAdapter(adapter);
                        break;
                    case "Calgary" :
                        adapter = ArrayAdapter.createFromResource(SelectAirportActivity.this, R.array.Calgary, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        airport1.setAdapter(adapter);
                        break;
                    case "Sao Paulo" :
                        adapter = ArrayAdapter.createFromResource(SelectAirportActivity.this, R.array.SaoPaulo, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        airport1.setAdapter(adapter);
                        break;
                    case "Rio de Janeiro" :
                        adapter = ArrayAdapter.createFromResource(SelectAirportActivity.this, R.array.RioDeJaneiro, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        airport1.setAdapter(adapter);
                        break;
                    case "Belo Horizonte" :
                        adapter = ArrayAdapter.createFromResource(SelectAirportActivity.this, R.array.BeloHorizonte, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        airport1.setAdapter(adapter);
                        break;
                    case "Moscow" :
                        adapter = ArrayAdapter.createFromResource(SelectAirportActivity.this, R.array.Moscow, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        airport1.setAdapter(adapter);
                        break;
                    default:
                        airport1.setAdapter(null);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /// City Destination to find the airport

        city2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String item = parent.getItemAtPosition(pos).toString();
                switch(item){
                    case "Toronto" :
                        adapter = ArrayAdapter.createFromResource(SelectAirportActivity.this, R.array.Toronto, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        airport2.setAdapter(adapter);
                        break;
                    case "Vancouver" :
                        adapter = ArrayAdapter.createFromResource(SelectAirportActivity.this, R.array.Vancouver, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        airport2.setAdapter(adapter);
                        break;
                    case "Calgary" :
                        adapter = ArrayAdapter.createFromResource(SelectAirportActivity.this, R.array.Calgary, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        airport2.setAdapter(adapter);
                        break;
                    case "Sao Paulo" :
                        adapter = ArrayAdapter.createFromResource(SelectAirportActivity.this, R.array.SaoPaulo, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        airport2.setAdapter(adapter);
                        break;
                    case "Rio de Janeiro" :
                        adapter = ArrayAdapter.createFromResource(SelectAirportActivity.this, R.array.RioDeJaneiro, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        airport2.setAdapter(adapter);
                        break;
                    case "Belo Horizonte" :
                        adapter = ArrayAdapter.createFromResource(SelectAirportActivity.this, R.array.BeloHorizonte, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        airport2.setAdapter(adapter);
                        break;
                    case "Moscow" :
                        adapter = ArrayAdapter.createFromResource(SelectAirportActivity.this, R.array.Moscow, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        airport2.setAdapter(adapter);
                        break;
                    default:
                        airport2.setAdapter(null);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
}
