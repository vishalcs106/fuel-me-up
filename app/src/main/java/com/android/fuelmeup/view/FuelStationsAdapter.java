package com.android.fuelmeup.view;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.fuelmeup.R;
import com.android.fuelmeup.model.FuelStation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vishal on 24-11-2017.
 */

public class FuelStationsAdapter extends RecyclerView.Adapter<FuelStationsAdapter.ViewHolder> {
    ArrayList<FuelStation> stations = new ArrayList<>();

    public FuelStationsAdapter(ArrayList<FuelStation> stations) {
        this.stations = stations;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_fuelstations_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FuelStation station = stations.get(position);
        holder.nameTextView.setText(station.getName());
        holder.distanceTextView.setText(station.getDistance());
        holder.navigateButton.setTag(position);
        holder.navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (int) v.getTag();
                FuelStation station = stations.get(i);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="
                                +station.getGeometry().getLocation().getLat()+","+
                                station.getGeometry().getLocation().getLng()));
                intent.setClassName("com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity");
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nameTextView) TextView nameTextView;
        @BindView(R.id.distanceTextView) TextView distanceTextView;
        @BindView(R.id.navigateButton)
        ImageView navigateButton;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
