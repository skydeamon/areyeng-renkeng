package com.theemergents.areyeng_renkeng.ui.start;

import android.os.Bundle;
import android.os.Messenger;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.theemergents.areyeng_renkeng.R;
import com.theemergents.areyeng_renkeng.utils.CheckLocationPermission;

public class StartFragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener {

    Messenger mService = null;
    boolean locationServiceIsBound;
    SearchView searchView;
    private StartViewModel startViewModel;
    private GoogleMap gMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        startViewModel =
                new ViewModelProvider(this).get(StartViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        startViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        searchView = (SearchView) root.findViewById(R.id.searchView);
        MapView mapView = (MapView) root.findViewById(R.id.mapView);
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
                Toast.makeText(root.getContext(), query, Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(root.getContext(), newText, Toast.LENGTH_LONG).show();
                return false;
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }
}

}