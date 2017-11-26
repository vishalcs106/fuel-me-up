package com.android.fuelmeup.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.fuelmeup.R;
import com.android.fuelmeup.model.FuelPrice;
import com.android.fuelmeup.presenter.FuelPricePresenter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


import butterknife.ButterKnife;


public class FuelPriceFragment extends Fragment implements FuelPriceViewInterface{

    Context mContext;
    TextView petrolPriceTextView;
    TextView dieselPriceTextView;
    TextView placeTextView;
    TextView headingTextView;
    LineChart lineChart;
    public FuelPriceFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuel_price, container, false);
        ButterKnife.bind(mContext, view);
        petrolPriceTextView = view.findViewById(R.id.petrolPriceTextView);
        headingTextView = view.findViewById(R.id.headingTextView);
        dieselPriceTextView = view.findViewById(R.id.dieselPriceTextView);
        placeTextView = view.findViewById(R.id.placeTextView);
        lineChart = view.findViewById(R.id.lineChart);
        initChart();
        return view;
    }

    private void initChart() {
        lineChart.setVisibleYRangeMaximum(150, YAxis.AxisDependency.LEFT);
        lineChart.setTouchEnabled(false);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.setDrawGridBackground(false);
        lineChart.getDescription().setText("Fuel Price Trends");
        lineChart.getDescription().setTextSize(8f);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getAxisRight().setEnabled(false);
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setDrawGridLines(false);
        lineChart.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance){
        FuelPricePresenter presenter = new FuelPricePresenter(FuelPriceFragment.this);
        presenter.getNearestCity(((MainFragment)getParentFragment()).location, getContext());
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
    public void onPetrolPriceSuccess(FuelPrice fuelPrice) {
        petrolPriceTextView.setText("Petrol "+fuelPrice.today.price);
        headingTextView.setText(fuelPrice.today.date);
        placeTextView.setText(fuelPrice.cityName);
        displayChart(fuelPrice.getPastPrice(), fuelPrice.type);
    }

    private void displayChart(List<FuelPrice.DatePrice> pastPrice, String fuelType) {
        List<Entry> entries = new ArrayList<>();
        for (FuelPrice.DatePrice data : pastPrice) {
            try {
                String dateString = data.date;
                DateFormat df = new SimpleDateFormat("EEEEE, MMMMM d, yyyy");
                Date date = df.parse(dateString);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                entries.add(new Entry(cal.get(Calendar.DAY_OF_MONTH), Float.parseFloat(data.price)));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        Collections.sort(entries, new EntryXComparator());
        if(lineChart.getData() != null){
            LineDataSet existingDataSet = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            String label = existingDataSet.getLabel();
            LineDataSet newDataSet;
            if(label.equals(FuelPrice.TYPE_PETROL)){
                newDataSet = new LineDataSet(entries, FuelPrice.TYPE_DIESEL);
                newDataSet.setColor(Color.parseColor("#0277BD"));
            } else {
                newDataSet = new LineDataSet(entries, FuelPrice.TYPE_PETROL);
                newDataSet.setColor(Color.parseColor("#009688"));
            }
            newDataSet.setValueTextSize(9f);
            newDataSet.setLineWidth(3f);
            LineData lineData = new LineData(existingDataSet,newDataSet);
            lineChart.setData(lineData);
        } else {
            LineDataSet dataSet = new LineDataSet(entries, fuelType);
            if (fuelType.equals(FuelPrice.TYPE_PETROL)) {
                dataSet.setColor(Color.parseColor("#009688"));
            } else {
                dataSet.setColor(Color.parseColor("#0277BD"));
            }
            dataSet.setValueTextSize(9f);
            dataSet.setLineWidth(3f);
            LineData lineData = new LineData(dataSet);
            lineChart.setData(lineData);
        }
        invalidateGraph();
    }

    private void invalidateGraph() {
        Legend point = lineChart.getLegend();
        point.setForm(Legend.LegendForm.LINE);
        point.setTextSize(12f);
        lineChart.invalidate();
    }

    @Override
    public void onPetrolPriceError(String message) {

    }

    @Override
    public void onDieselPriceSuccess(FuelPrice fuelPrice) {
        dieselPriceTextView.setText("Diesel "+fuelPrice.today.price);
        headingTextView.setText(fuelPrice.today.date);
        placeTextView.setText(fuelPrice.cityName);
        displayChart(fuelPrice.getPastPrice(), fuelPrice.type);
    }

    @Override
    public void onDieselPriceError(String message) {

    }
}
