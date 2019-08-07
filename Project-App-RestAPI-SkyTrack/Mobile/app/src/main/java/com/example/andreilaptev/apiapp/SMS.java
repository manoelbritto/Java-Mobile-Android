package com.example.andreilaptev.apiapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.widget.Toast;
/*Developed by Andrzej Popowski*/
public class SMS extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
	private String phoneNo;
	private String message;
	private Context context;

    public SMS(Context context, String phoneNo, String message) {
        this.context = context;
	    this.phoneNo = phoneNo;
	    this.message = message;
    }


    public void send() {
        try {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) this.context,
                        Manifest.permission.SEND_SMS)) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(this.phoneNo, null, this.message, null, null);
                    Toast.makeText(this.context, "Text message sent",
                            Toast.LENGTH_LONG).show();
                } else {
                    ActivityCompat.requestPermissions((Activity) this.context,
                            new String[]{ Manifest.permission.SEND_SMS },
                            MY_PERMISSIONS_REQUEST_SEND_SMS);
                }
            } else {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(this.phoneNo, null, this.message, null, null);
                Toast.makeText(this.context, "Text message sent",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    try {
                        smsManager.sendTextMessage(this.phoneNo, null, this.message, null, null);
                    } catch (Exception e) {
                        Toast.makeText(this.context, e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(this.context, "Text message sent",
                            Toast.LENGTH_LONG).show();
                } else {
                    return;
                }
            }
        }

    }
}
