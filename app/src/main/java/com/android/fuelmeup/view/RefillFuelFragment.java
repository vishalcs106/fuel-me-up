package com.android.fuelmeup.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.fuelmeup.R;
import com.android.fuelmeup.model.RefillFuel;
import com.android.fuelmeup.presenter.RefillFuelPresenter;
import com.android.fuelmeup.utils.LocationUtils;
import com.android.fuelmeup.utils.UserUtils;
import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vishal on 21-11-2017.
 */

public class RefillFuelFragment extends Fragment implements RefillFuelViewInterface{
    Context mContext;
    EditText amountEditText, volumeEditText, locationEditText;
    Button submitButton;
    RadioGroup fuelTypeRadio;
    RefillFuel refill;
    String selectedFuelType;
    RefillFuelPresenter presenter;
    public RefillFuelFragment() {

    }

    public static RefillFuelFragment newInstance() {
        RefillFuelFragment fragment = new RefillFuelFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        presenter = new RefillFuelPresenter(mContext, RefillFuelFragment.this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_refill, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance){
        amountEditText = view.findViewById(R.id.amountEditText);
        volumeEditText = view.findViewById(R.id.fuelVolumeEditText);
        locationEditText = view.findViewById(R.id.locationEditText);
        submitButton = view.findViewById(R.id.submitButton);
        fuelTypeRadio = view.findViewById(R.id.fuelTypeRadioGroup);
        final LatLng latLng = ((MainFragment)getParentFragment()).currentLatLng;
        if(latLng != null)
            presenter.getFuelStationName(latLng.latitude, latLng.longitude);
        fuelTypeRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.petrolRadioButton){
                    selectedFuelType = RefillFuel.TYPE_PETROL;
                } else {
                    selectedFuelType = RefillFuel.TYPE_DIESEL;
                }
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!amountEditText.getText().toString().equals("") &&
                        !volumeEditText.getText().toString().equals("") &&
                        !locationEditText.getText().toString().equals("") &&
                        fuelTypeRadio.getCheckedRadioButtonId() != -1){
                    String latLngStr = latLng.latitude + "," + latLng.longitude;
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                    Date now = Calendar.getInstance().getTime();
                    String dateStr = df.format(now);
                    refill = new RefillFuel(selectedFuelType,
                            amountEditText.getText().toString(),
                            volumeEditText.getText().toString(),
                            latLngStr, locationEditText.getText().toString(),
                            UserUtils.getUdid(getContext()), dateStr);
                    presenter.saveRefillData(refill);
                } else {
                    Toast.makeText(getContext(), "Please enter all the fields", Toast.LENGTH_LONG).show();
                }
            }
        });
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
    public void onSaveSuccess() {
        Toast.makeText(getContext(), "You have Earned the cashback", Toast.LENGTH_LONG).show();
        amountEditText.setText("");
        volumeEditText.setText("");
    }

    @Override
    public void onLocationSuccess(String location) {
        locationEditText.setText(location);
    }


}
