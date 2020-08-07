package org.vansoft.karmanyak.fragment;


import android.content.Context;
import android.location.Address;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.vansoft.karmanyak.R;
import org.vansoft.karmanyak.adapters.UpcommingEventAdapter;
import org.vansoft.karmanyak.customElement.BottomSheet;
import org.vansoft.karmanyak.model.Event;
import org.vansoft.karmanyak.solidity.Solidity;
import org.vansoft.karmanyak.utils.AllData;
import org.web3j.utils.Async;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    AllData allData;
    TextView userName,points,publicKey;
    RecyclerView upcommingRecyclerView;
    UpcommingEventAdapter upcommingEventAdapter;
    ProgressBar progressBar;
    CardView myCardView;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        allData = AllData.getInstance();
        userName = view.findViewById(R.id.username);
        points = view.findViewById(R.id.userpoint);
        upcommingRecyclerView = view.findViewById(R.id.upcommingEventsRecyclerViewId);
        progressBar = view.findViewById(R.id.point_progressBar_id);
        myCardView = view.findViewById(R.id.my_card_view_id);
        publicKey = view.findViewById(R.id.public_Address_user_id);


        userName.setText(allData.getUser().getEmail());
        publicKey.setText(allData.getUser().getPublicKey());
        myCardView.setOnClickListener(v -> upDateUserView());
       // allData.setPoint(10);
//        points.setText(String.valueOf(allData.getUser().getTokens()));
        upcommingEventAdapter = new UpcommingEventAdapter(getContext(), (ArrayList<Event>) allData.getUpcommingEventList());
        upcommingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        upcommingRecyclerView.setAdapter(upcommingEventAdapter);
        showProgress(View.INVISIBLE);
        upDateUserView();
        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        if(upcommingEventAdapter!=null){
            upcommingEventAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(upcommingEventAdapter!=null){
            upcommingEventAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(upcommingEventAdapter!=null){
            upcommingEventAdapter.notifyDataSetChanged();
        }
    }

    public void upDateUserView(){
        MyAsyncTask myAsyncTask = new MyAsyncTask(allData.getUser().getPublicKey(),getContext());
        myAsyncTask.execute();
    }

    class MyAsyncTask extends AsyncTask<String,String,String> {
        String address;
        Context context;
        public MyAsyncTask(String userAddress,Context context){
            this.address = userAddress;
            this.context=context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... strings) {
            Solidity solidity = new Solidity(context);
            return solidity.getBalance(address);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            showProgress(View.INVISIBLE);
            if(s==null){
                Toast.makeText(context,"Unable To Update Balance!",Toast.LENGTH_LONG).show();
                return;
            }
            updateBalance(s);
        }
    }

    private void updateBalance(String s) {
        points.setText(s);
    }

    private void showProgress(int i) {
        progressBar.setVisibility(i);
    }


}
