package com.android.fuelmeup.view;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.fuelmeup.R;
import com.android.fuelmeup.model.FuelStation;
import com.android.fuelmeup.presenter.FuelStationsPresenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import butterknife.ButterKnife;

public class FuelStationsFragment extends Fragment  implements OnMapReadyCallback,
        FuelStationsViewInterface{
    GoogleMap mMap;

    Location location;
    LatLng currentLatLng;
    Context mContext;
    FuelStationsPresenter fuelStationsPresenter;
    RecyclerView fuelStationsRecyclerView;
    public FuelStationsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        fuelStationsPresenter = new FuelStationsPresenter(FuelStationsFragment.this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuel_stations, container, false);
        ButterKnife.bind(mContext, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance){
        initializeMap();
        fuelStationsRecyclerView = view.findViewById(R.id.fuelsStationsRecyclerView);
        fuelStationsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        location = ((MainFragment)getParentFragment()).location;
        if(location != null) {
            currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(currentLatLng).icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.current_location_map_pointer_small)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            getNearbyGasStations();
        } else {
            Toast.makeText(mContext, "Could not locate you", Toast.LENGTH_LONG).show();
        }
    }

    private void getNearbyGasStations() {
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                location.getLatitude()+","+location.getLongitude()+
                "&radius=1000&rank_by=distance&type=gas_station&key=" +
                "AIzaSyDrkRmAYiVl6v9iQrBcvtgEwGJdPs06Ai4";
        fuelStationsPresenter.getLocations(url);
    }

    private void initializeMap() {
        if (mMap == null) {
            SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager().
                    findFragmentById(R.id.map);
            mapFrag.getMapAsync(this);

        }
    }

    @Override
    public void onFuelStationsComplete() {

    }

    @Override
    public void onFuelStationsError(String message) {

    }

    @Override
    public void onFuelStationsSuccess(ArrayList<FuelStation> result) {
        result = calculateDistances(result);
        FuelStationsAdapter adapter = new FuelStationsAdapter(result);
        fuelStationsRecyclerView.setAdapter(adapter);
    }

    private ArrayList<FuelStation> calculateDistances(ArrayList<FuelStation> result) {
        ArrayList<FuelStation> stations = new ArrayList<>();
        for(FuelStation fuelStation : result){
            LatLng newLatLng = new LatLng(fuelStation.getGeometry().getLocation().getLat(),
                    fuelStation.getGeometry().getLocation().getLng());
            mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.ic_gas)));
            String distance = FuelStation.calculateDistance(currentLatLng, newLatLng)+"m";
            fuelStation.distance = distance;
            stations.add(fuelStation);
        }
        return stations;
    }
}
