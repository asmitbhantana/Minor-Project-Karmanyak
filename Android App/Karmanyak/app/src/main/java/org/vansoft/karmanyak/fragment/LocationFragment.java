package org.vansoft.karmanyak.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.clustering.ClusterManager;

import org.vansoft.karmanyak.R;
import org.vansoft.karmanyak.model.ClusterMarker;
import org.vansoft.karmanyak.model.Event;
import org.vansoft.karmanyak.utils.AllData;
import org.vansoft.karmanyak.utils.MyClusterManagerRenderer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LocationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String MAPVIEW_BUNDLE_KEY = "mapview-saved-key";
    //location
    @BindView(R.id.mapView)
    MapView mapView;
    FusedLocationProviderClient mFusedLocationClient;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton locateFab;

    //Map
    GoogleMap mGoogleMap;
    private ClusterManager<ClusterMarker> mClusterManager;
    private MyClusterManagerRenderer mClusterManagerRenderer;
    Location location;


    //AllData
    AllData allData;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    //Dumbby data
    private ArrayList<ClusterMarker> clusterMarkers = new ArrayList<>();

    public LocationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationFragment newInstance(String param1, String param2) {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        ButterKnife.bind(this, view);
        initData();
        initMap(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        return view;
    }

    private void initData() {
        allData = AllData.getInstance();
    }

    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            mListener.askLocationPermission();
            return;
        }
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    location = task.getResult();
                    setCameraView();
                }
            }
        });
    }

    private void addMapMarkers() {
        if (mGoogleMap != null) {


//            allData.getEventList();
//            LatLng sydney = new LatLng(-33.852, 151.211);
//            googleMap.addMarker(new MarkerOptions().position(sydney)
//                    .title("Marker in Sydney"));

//            if (mClusterManager == null) {
//                mClusterManager = new ClusterManager<>(getActivity().getApplicationContext(), mGoogleMap);
//            }
//            if (mClusterManagerRenderer == null) {
//                mClusterManagerRenderer = new MyClusterManagerRenderer(getActivity(), mGoogleMap, mClusterManager);
//
//            }
//            mClusterManager.setRenderer(mClusterManagerRenderer);
            for (Event e : allData.getEventList()) {
                try {
                    String title = e.getEventName();
                    String body = " Rewards: " + e.getReward();
//                    int avatar = R.drawable.location_marker;
                    float ltd = Float.parseFloat(e.getEventLocation().substring(0, e.getEventLocation().indexOf(",")).replace(" ",""));
                    float lng = Float.parseFloat(e.getEventLocation().substring(e.getEventLocation().indexOf(",")+1).replace(" ",""));
                    LatLng latLng = new LatLng(ltd,lng);
                    mGoogleMap.addMarker(new MarkerOptions().
                            position(latLng).
                            title(title+ body)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker)));
                    //Log.d("data","lat "+ltd+" long "+lng);
//                    ClusterMarker clusterMarker = new ClusterMarker(
//                            new LatLng(ltd, lng)
//                            , title,
//                            body,
//                            e,
//                            avatar
//                    );
//                    mClusterManager.addItem(clusterMarker);
//                    clusterMarkers.add(clusterMarker);
                }catch (NullPointerException eq){
                    Log.e("data", "addMapMarkers: NullPointerException: " + eq.getMessage() );
//                }
//
            }
//            mClusterManager.cluster();
            setCameraView();
            }
        }
    }

    @OnClick(R.id.floatingActionButton)
    public void onLocationWanted(){
        setCameraView();
    }

    private void setCameraView() {
        if(location!=null){
            LatLngBounds mMapBoundary;
            // Set a boundary to start
            double bottomBoundary = location.getLatitude() - .05;
            double leftBoundary = location.getLongitude() - .05;
            double topBoundary = location.getLatitude() + .05;
            double rightBoundary = location.getLongitude() + .05;

            mMapBoundary = new LatLngBounds(
                    new LatLng(bottomBoundary, leftBoundary),
                    new LatLng(topBoundary, rightBoundary)
            );

            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mMapBoundary, 0));

        }
    }

    public void initMap(Bundle bundle) {
// Gets to GoogleMap from the MapView and does initialization stuff\
        Bundle mapBundle = null;
        if (bundle != null) {
            mapBundle = bundle.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapView.onCreate(mapBundle);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                getLastKnownLocation();
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    mListener.askLocationPermission();
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                addMapMarkers();
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
        mapView.onSaveInstanceState(mapBundle);
    }

    //    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mapView!=null)
            mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void askLocationPermission();
        void onFragmentInteraction(Uri uri);
    }
}
