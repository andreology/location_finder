package com.example.homework3;
//Andre barajas, Hunter
//Google maps location finder project
//Spring 2020
import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DecimalFormat;
import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
//Class to hold a list of locations for recycler view
public class LocationHolder extends RecyclerView.Adapter<LocationHolder.Holder>{
    private List<MarkerLocation> tInput;
    private DecimalFormat tCompute2Dec;
    private LayoutInflater tUseInflate;
    private SendListEventsCallBack tSetCallback;

    @Override public int getItemCount() { return tInput.size(); }

    public LocationHolder(Context context, List<MarkerLocation> data) {
        tInput = data;
        tUseInflate = LayoutInflater.from(context);
        tSetCallback = (SendListEventsCallBack) context;
        //set decimal to tenth place
        tCompute2Dec = new DecimalFormat("#.00");
    }

    @Override public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = tUseInflate.inflate(R.layout.locus, parent, false);
        return new Holder(view, this, tSetCallback);
    }

    @Override public void onBindViewHolder(Holder holder, int position) {
        MarkerLocation location = tInput.get(position);

        holder.tSetTitle.setText(location.mTitle);
        holder.tSetLatnLong.setText("Lat: "+tCompute2Dec.format(location.tLatandLong.latitude)+
                                    "\nLng: "+tCompute2Dec.format(location.tLatandLong.longitude));
        //save position of location
        holder.tLocusLocation = position;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public final TextView tSetTitle;
        public final LocationHolder tSetAdapt;
        public final TextView tSetLatnLong;
        public final SendListEventsCallBack tSetCallback;
        public int tLocusLocation;

        public Holder(View itemView, LocationHolder adapter, SendListEventsCallBack callback) {
            super(itemView);
            tSetCallback = callback;
            tSetTitle = itemView.findViewById(R.id.title_view);
            tSetLatnLong = itemView.findViewById(R.id.latlng_view);
            tSetAdapt = adapter;

            itemView.setOnClickListener((view) -> {
                tSetCallback.recyclerCallbackFn(tInput.get(tLocusLocation));
            });
        }
    }
}
