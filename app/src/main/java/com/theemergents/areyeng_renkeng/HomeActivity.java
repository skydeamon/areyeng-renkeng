package com.theemergents.areyeng_renkeng;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.theemergents.areyeng_renkeng.R;
import com.theemergents.areyeng_renkeng.service.BackgroundLocationService;
import com.theemergents.areyeng_renkeng.utils.CheckLocationPermission;

public class HomeActivity extends AppCompatActivity implements GoogleMap.OnMyLocationButtonClickListener {

    Messenger mService = null;
    boolean locationServiceIsBound;
    //String locationEnabledByUser = getIntent().getStringExtra("location_reply");

    SearchView searchView;
    private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        searchView = (SearchView) findViewById(R.id.searchView);
        MapView mapView = (MapView) findViewById(R.id.mapView);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap nMap) {
                gMap = nMap;
            }
        });

        if (CheckLocationPermission.isGranted(this)) {
            gMap.setMyLocationEnabled(true);
            gMap.setOnMyLocationButtonClickListener(this);
        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getBaseContext(), query, Toast.LENGTH_LONG).show();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getBaseContext(), newText, Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }



    //Service to background location to detect taxi movement
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = new Messenger(service);
            locationServiceIsBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            mService = null;
            locationServiceIsBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, BackgroundLocationService.class), mConnection,
                Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (locationServiceIsBound) {
            unbindService(mConnection);
            locationServiceIsBound = false;
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }
}