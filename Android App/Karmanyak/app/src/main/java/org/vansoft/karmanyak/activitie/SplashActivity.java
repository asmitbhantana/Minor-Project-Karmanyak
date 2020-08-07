package org.vansoft.karmanyak.activitie;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.vansoft.karmanyak.R;
import org.vansoft.karmanyak.database.DatabaseHelper;
import org.vansoft.karmanyak.model.Event;
import org.vansoft.karmanyak.utils.AllData;
import org.vansoft.karmanyak.utils.Const;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    AllData allData;
    @BindView(R.id.layout)
    ConstraintLayout constraintLayout;
    View view;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        view = findViewById(R.id.layout);
        init();
        getData();
    }

    private void init() {
        allData = AllData.getInstance();
        databaseHelper = DatabaseHelper.getINSTANCE(this);
    }

    private void getData() {
        Call<List<Event>> events = Const.getApiService().getEvents();
        events.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
               // Log.e("data","Response"+response.body().toString());
                allData.setEventList(response.body());
             //   Log.e("data","Response"+  allData.getEventList().get(0).getEventName());
                getToNextActivity();
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                //Log.e("data","throwable"+t.getMessage());
                Snackbar.make(view,"Error "+t.getMessage(),Snackbar.LENGTH_INDEFINITE)
                        .setAction("Exit", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        }).show();
            }
        });

    }

    public void getToNextActivity(){

        if(!(databaseHelper.userDao().getAllUSer().size() >0)){
            Intent intent = new Intent(this,LoginRegisterActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }

    }


}
