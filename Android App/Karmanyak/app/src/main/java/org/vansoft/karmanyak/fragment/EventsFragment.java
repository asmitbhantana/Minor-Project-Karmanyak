package org.vansoft.karmanyak.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.vansoft.karmanyak.R;
import org.vansoft.karmanyak.activitie.DetailsActivity;
import org.vansoft.karmanyak.activitie.QrScanActivity;
import org.vansoft.karmanyak.adapters.EventAdapter;
import org.vansoft.karmanyak.database.DatabaseHelper;
import org.vansoft.karmanyak.model.Event;
import org.vansoft.karmanyak.utils.AllData;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {
    AllData allData;
    @BindView(R.id.eventsRecyclerViewId)
    RecyclerView eventsRecyclerView;

    EventAdapter eventAdapter;
    DatabaseHelper databaseHelper;

    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_events, container, false);
        ButterKnife.bind(this,view);
        allData = AllData.getInstance();
        databaseHelper=DatabaseHelper.getINSTANCE(getContext());
        init();
        return view;

    }

    private void init() {
        eventAdapter = new EventAdapter(getContext());
        eventAdapter.setButtonClicked(new EventAdapter.onButtonClicked() {
            @Override
            public void onCheckInBtnClicked(int position) {
                Intent intent = new Intent(getContext(),QrScanActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("event_list_item",position);
                startActivity(intent);
            }

            @Override
            public void onVisitLaterBtnClicked(int position) {
                //eventAdapter.notifyDataSetChanged();
                Event event = allData.getEventList().get(position);
                if(!allData.isUpcommingEvent(event)){
                    allData.addUpcomminEventList(event);
                    databaseHelper.eventDao().addEvent(event);
                    Log.d("Added","  "+ event.getEventName());
                }else {
                    allData.removeUpcommingEvent(event);
                    databaseHelper.eventDao().deleteEvent(event);
                    Log.d("Removed","  "+ event.getEventName());
                }
                eventAdapter.notifyDataSetChanged();


            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        eventsRecyclerView.setAdapter(eventAdapter);
        eventsRecyclerView.setLayoutManager(linearLayoutManager);
    }


}
