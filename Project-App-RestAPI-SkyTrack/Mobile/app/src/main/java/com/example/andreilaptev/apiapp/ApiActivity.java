package com.example.andreilaptev.apiapp;

import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/*Developed by Andrei Laptev*/
public class ApiActivity extends AppCompatActivity {

    TextView textView;
    String arr, dep;
    String mainUrl = "http://aviation-edge.com/v2/public/flights?key=f491c7-b3c7a4&";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);
    }

    public void getApi(View view) {

        ApiAsyncTasks aTask = new ApiAsyncTasks();
        aTask.execute();
    }


    public void sendSMS(View view) {
	    SMS sms = new SMS(this,"437 999 6464", "Test message");
	    sms.send();
    }


    public class ApiAsyncTasks extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            //Hardcode airports

            dep ="ATL";
            arr = "JFK";
//comment

            // implement API in background and store the response in current variable
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {

                    String fullUrl = mainUrl + "&depIata=" + dep + "&arrIata=" + arr;
                    url = new URL(fullUrl);

                    urlConnection = (HttpURLConnection) url
                            .openConnection();

                    InputStream in = urlConnection.getInputStream();

                    InputStreamReader isr = new InputStreamReader(in);

                    int data = isr.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isr.read();
                        System.out.print(current);

                    }
                    // return the data to onPostExecute method
                    return current;

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
            return current;

        }

        @Override
        protected void onPostExecute(String output) {

            textView = findViewById(R.id.output);
            textView.setText(output);

        }
    }
}
