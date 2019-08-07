package com.example.andreilaptev.apiapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
/*Developed by Manoel B. Burgos Filho*/
public class FlightData extends ArrayAdapter<Flight>{

        private Activity context;
        private List<Flight> flightList;

        public FlightData(Activity context, List<Flight> flightList) {
            super(context, R.layout.list_layout, flightList);
            this.context = context;
            this.flightList = flightList;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

            TextView depart = (TextView)listViewItem.findViewById(R.id.txtdepartText);
            TextView arriv = (TextView)listViewItem.findViewById(R.id.txtArrivalText);
            TextView latitude = (TextView)listViewItem.findViewById(R.id.txtLatitudeText);
            TextView longitude = (TextView)listViewItem.findViewById(R.id.txtLongitudeText);
            TextView speed = (TextView)listViewItem.findViewById(R.id.txtSpeedText);
            TextView status = (TextView)listViewItem.findViewById(R.id.txtStatusText);
            TextView altitude = (TextView)listViewItem.findViewById(R.id.txtAltitudetxt);


            Flight flight = flightList.get(position);
            final String txtdepart = flight.getDeparture();
            final String txtarriv = flight.getArrival();
            final String txtlatitude = flight.getLatitude();
            final String txtlongitude = flight.getLongitude();
            final String txtspeed = flight.getSpeed();
            final String txtstatus = flight.getStatus();
            final String txtaltitude = flight.getAltitude();

            depart.setText(txtdepart);
            arriv.setText(txtarriv);
            latitude.setText(txtlatitude);
            longitude.setText(txtlongitude);
            speed.setText(txtspeed);
            status.setText(txtstatus);
            altitude.setText(txtaltitude);
            return listViewItem;
        }
}
