package com.example.andreilaptev.apiapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/*Developed by Manoel B. Burgos Filho*/
public class ListFlightsActivity extends AppCompatActivity {
    TextView textView;
    String arr, dep, type;
    String mainUrl = "http://aviation-edge.com/v2/public/flights?key=put your key here"; // limited access with this key

    private String[] valueFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_flights);
        valueFind = getIntent().getExtras().getStringArray("airportCode");
        dep = valueFind[0];
        arr = valueFind[1];
        type = valueFind[2];

        ApiAsyncTasks aTask = new ApiAsyncTasks();
        aTask.execute();
    }

    public class ApiAsyncTasks extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String fullUrl="";
            String current = "";
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    if (type.equals("both")){
                        fullUrl = mainUrl + "&depIata=" + dep + "&arrIata=" + arr;
                    } else if (type.equals("arrival"))
                    {
                        fullUrl = mainUrl + "&arrIata=" + arr;
                    }else if (type.equals("departure")){
                        fullUrl = mainUrl + "&depIata=" + dep ;
                    }

                    url = new URL(fullUrl);

                    urlConnection = (HttpURLConnection) url
                            .openConnection();

                    InputStream in = urlConnection.getInputStream();

                    InputStream content = new BufferedInputStream(
                            urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                        urlConnection.disconnect();
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }

            return stringBuilder.toString();

        }

        @Override
        protected void onPostExecute(String output) {
            List<Flight> listFlight = new ArrayList<>();

            ListView listViewFlight = (ListView)findViewById(R.id.listView);
            textView = findViewById(R.id.txtMessage);
            try {

                JSONArray jsonArray = new JSONArray(output);
               //---print out the content of the json feed---
                for (int i = 0; i < jsonArray.length(); i++) {
                    Flight flight = new Flight();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONObject directionObj = new JSONObject(jsonObject.getString("geography"));;
                    JSONObject latitudeObj = new JSONObject(jsonObject.getString("arrival"));;
                    JSONObject longitudeObj = new JSONObject(jsonObject.getString("departure"));;
                    JSONObject speedObj = new JSONObject(jsonObject.getString("speed"));;
                    flight.setLatitude(directionObj.getString("latitude"));
                    flight.setLongitude(directionObj.getString("longitude"));
                    flight.setAltitude(directionObj.getString("altitude"));
                    flight.setDeparture(longitudeObj.getString("iataCode"));
                    flight.setArrival(latitudeObj.getString("iataCode"));
                    flight.setSpeed(speedObj.getString("horizontal"));
                    flight.setStatus(jsonObject.getString("status"));

                    listFlight.add(flight);
                }
                FlightData adapter = new FlightData(ListFlightsActivity.this,listFlight);
                listViewFlight.setAdapter(adapter);

                listViewFlight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                        //getting the right values from the custom listview and send it to next class
                        Flight gps_data = (Flight) parent.getItemAtPosition(pos);
                        String values [] = new String[4];
                        values[0] = gps_data.getLatitude();
                        values[1] = gps_data.getLongitude();
                        values[2] = gps_data.getAltitude();
                        values[3] = gps_data.getSpeed();
                        Intent i = new Intent(ListFlightsActivity.this,SMSActivity.class);
                        i.putExtra("gps_list", values);
                        startActivity(i);
                    }
                });
            } catch (Exception e) {
                textView.setText("No Record found !!!");

            }

        }
    }
}
