package com.theemergents.areyeng_renkeng;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.internal.FallbackServiceBroker;
import com.google.android.gms.maps.GoogleMap;
import com.theemergents.areyeng_renkeng.utils.PermissionUtils;

public class LocationTurnOnActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_turnon);
        Button yesBtn =  (Button) findViewById(R.id.yes);
        yesBtn.setOnClickListener(this);
        Button noBtn =  (Button) findViewById(R.id.no);
        noBtn.setOnClickListener(this);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes: {
                enableMyLocation();
                break;
            }
            case R.id.no: {
                launchHomeActivity(false);
                break;
            }
        }
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
        launchHomeActivity(true);
    }

    private void launchHomeActivity(Boolean locationReply){
        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
        intent.putExtra("location_reply", locationReply);
        startActivity(intent);
    }

}