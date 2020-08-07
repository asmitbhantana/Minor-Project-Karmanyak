package org.vansoft.karmanyak.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.vansoft.karmanyak.R;
import org.vansoft.karmanyak.model.Event;

import java.util.ArrayList;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpcommingEventAdapter extends RecyclerView.Adapter<UpcommingEventAdapter.VH> {
    Context context;
    ArrayList<Event> upcommingEvents;

    public UpcommingEventAdapter(Context context, ArrayList<Event> upcommingEvents) {
        this.context = context;
        this.upcommingEvents = upcommingEvents;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.upcomming_event_item,viewGroup,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.upcommingEventText.setText(upcommingEvents.get(i).getEventName());
      //  vh.daysremaining.setText(upcommingEvents.get(i).getEventDate());
    }

    @Override
    public int getItemCount() {
        return upcommingEvents.size();
    }

    public class VH extends RecyclerView.ViewHolder {

        @BindView(R.id.upcommingEventTextId)
        TextView upcommingEventText;
        @BindView(R.id.daysRemainingTextId)
        TextView daysremaining;


        public VH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
