package org.vansoft.karmanyak.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.vansoft.karmanyak.R;
import org.vansoft.karmanyak.customElement.ReedemBottomSheet;
import org.vansoft.karmanyak.database.DatabaseHelper;
import org.vansoft.karmanyak.model.User;
import org.vansoft.karmanyak.solidity.Solidity;
import org.web3j.crypto.Credentials;

/**
 * A simple {@link Fragment} subclass.
 */
public class RewardFragment extends Fragment {


    CardView cardView;
    ReedemBottomSheet bottomSheet;
    DatabaseHelper databaseHelper;

    public RewardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reward, container, false);
        cardView = view.findViewById(R.id.donate_for_flood_card_id);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();
            }
        });
        databaseHelper = DatabaseHelper.getINSTANCE(getContext());
        return view;
    }

    private void showBottomSheet() {

        bottomSheet = new ReedemBottomSheet();
        if (getFragmentManager() != null) {
            bottomSheet.show(getFragmentManager(),ReedemBottomSheet.TAG);
            bottomSheet.setRewardClickEvents(new ReedemBottomSheet.RewardClickEvents() {
                @Override
                public void onYesBtnClicked() {
                    bottomSheet.setBtnVisibility(View.INVISIBLE);
                    MyAsyncTask myAsyncTask = new MyAsyncTask(getContext());
                    myAsyncTask.execute();
                }

                @Override
                public void onNoBtnClicked() {
                    bottomSheet.dismiss();
                }
            });
        }
    }


    class MyAsyncTask extends AsyncTask<String,String,String> {
        Context context;
        User user;
        public MyAsyncTask(Context context){
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bottomSheet.setProgressBarVisibility(View.VISIBLE);
            user = databaseHelper.userDao().getAllUSer().get(0);
        }

        @Override
        protected String doInBackground(String... strings) {
            Solidity solidity = new Solidity(context);
            Credentials credentials = solidity.getCredentialsFromWallet(user.getPassword(),user.getFilePath());
            return solidity.ReedemByUser(credentials,120);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            bottomSheet.setProgressBarVisibility(View.INVISIBLE);
            if(s!=null){
                bottomSheet.changeMessageText("Transaction Successful Your Account is Deducted By 120 Tokens.:-)");
                return;
            }
            bottomSheet.changeMessageText("Transaction Cannot Be Completed :-(");
        }
    }


}
