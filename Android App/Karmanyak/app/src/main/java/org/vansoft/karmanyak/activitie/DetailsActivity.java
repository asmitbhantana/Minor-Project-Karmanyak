package org.vansoft.karmanyak.activitie;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.vansoft.karmanyak.R;
import org.vansoft.karmanyak.model.Event;
import org.vansoft.karmanyak.utils.AllData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {


    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundle";
    @BindView(R.id.eventTypeId)
    TextView eventType;
    @BindView(R.id.eventHeadingId)
    TextView eventHeading;
    @BindView(R.id.eventStartDateId)
    TextView eventStartDate;
    @BindView(R.id.eventEndDateId)
    TextView eventEndDate;
    @BindView(R.id.eventManagerId)
    TextView eventManager;
    @BindView(R.id.eventRewardId)
    TextView eventReward;
    @BindView(R.id.eventAboutId)
    TextView eventAbout;

    @BindView(R.id.eventMapViewId)
    MapView eventMapView;

    @BindView(R.id.eventGoingBtnId)
    Button eventGoingBtn;



    @BindView(R.id.eventImageViewId)
    ImageView eventImageView;


    Event event;
    AllData allData;

     GoogleMap mGoogleMap;
    LatLng eventLatlang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        allData = AllData.getInstance();

        if(getIntent().hasExtra("event_list_item")){
            int position = getIntent().getIntExtra("event_list_item",0);
            event = allData.getEventList().get(position);
        }

        eventType.setText(event.getEventType());
        eventHeading.setText(event.getEventName());
        eventAbout.setText(event.getEventDesc());
        eventEndDate.setText(event.getEventEndDate().substring(0,event.getEventEndDate().indexOf("T")));
        eventStartDate.setText(event.getEventDate().substring(0,event.getEventDate().indexOf("T")));
        eventManager.setText(event.getEventManager());
        eventReward.setText(String.valueOf(event.getReward()));
        eventGoingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] latLang = event.getEventLocation().split(",");
                final double lat;
                final double lang;
                lat = Double.parseDouble(latLang[0]);

                lang = Double.parseDouble(latLang[1].substring(0,latLang[1].length()-1));
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+lat+","+lang));
                startActivity(intent);
            }
        });

        Glide.with(this).load(event.getEventImageSrc()).into(eventImageView);

        setUpMaps(savedInstanceState);
    }

    private void setUpMaps(Bundle bundle) {

        Bundle mapBundle = null;
        if (bundle != null) {
            mapBundle = bundle.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        eventMapView.onCreate(mapBundle);
        eventMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                addMarker();
                setCameraView();
            }
        });


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if(mapBundle==null){
            mapBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY,mapBundle);
        }
        eventMapView.onSaveInstanceState(mapBundle);
    }


    void addMarker(){
        String[] latLang = event.getEventLocation().split(",");
        final double lat;
        final double lang;
        lat = Double.parseDouble(latLang[0]);

        lang = Double.parseDouble(latLang[1].substring(0,latLang[1].length()-1));
        eventLatlang = new LatLng(lat,lang);
        mGoogleMap.addMarker(new MarkerOptions()
                .position(eventLatlang)
                .title(event.getEventName()));

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+lat+","+lang));
                startActivity(intent);
                return true;
            }
        });

    }
    private void setCameraView() {
        if(eventLatlang!=null){
            LatLngBounds mMapBoundary;
            // Set a boundary to start
            double bottomBoundary = eventLatlang.latitude- .05;
            double leftBoundary = eventLatlang.longitude - .05;
            double topBoundary = eventLatlang.latitude + .05;
            double rightBoundary = eventLatlang.longitude  + .05;

            mMapBoundary = new LatLngBounds(
                    new LatLng(bottomBoundary, leftBoundary),
                    new LatLng(topBoundary, rightBoundary)
            );

            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mMapBoundary, 0));

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        eventMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        eventMapView.onStop();
    }



    @Override
    public void onResume() {
        super.onResume();
        if(eventMapView!=null)
            eventMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        eventMapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        eventMapView.onLowMemory();
    }


}
