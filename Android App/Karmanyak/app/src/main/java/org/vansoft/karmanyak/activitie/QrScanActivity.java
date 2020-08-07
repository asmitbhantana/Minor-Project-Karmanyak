package org.vansoft.karmanyak.activitie;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.qrcode.QRCodeReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.vansoft.karmanyak.R;
import org.vansoft.karmanyak.customElement.TranscationBottomSheet;
import org.vansoft.karmanyak.database.DatabaseHelper;
import org.vansoft.karmanyak.model.Event;
import org.vansoft.karmanyak.solidity.MyCoin;
import org.vansoft.karmanyak.solidity.Solidity;
import org.vansoft.karmanyak.utils.AllData;
import org.vansoft.karmanyak.utils.Const;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static butterknife.internal.Utils.arrayOf;

public class QrScanActivity extends AppCompatActivity {

    private static final int MY_CAMERA_REQUEST_CODE = 102;
    ZXingScannerView qrCodeScanner;
   OkHttpClient client;
   AllData allData;
   DatabaseHelper databaseHelper;
    TranscationBottomSheet transcationBottomSheet;
    private Result qrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);
        qrCodeScanner = findViewById(R.id.qrCodeScanner);
        client = new OkHttpClient();
        allData = AllData.getInstance();
        databaseHelper = DatabaseHelper.getINSTANCE(this);
        setScannerProperties();
    }

    private void setScannerProperties() {
        List<BarcodeFormat> formats = new ArrayList<>();
        formats.add(BarcodeFormat.QR_CODE);
        qrCodeScanner.setFormats(formats);
            qrCodeScanner.setAutoFocus(true);
            qrCodeScanner.setLaserColor(R.color.colorAccent);
            qrCodeScanner.setMaskColor(R.color.colorAccent);
//            if (Build.MANUFACTURER.equals(HUAWEI, ignoreCase = true))
//                qrCodeScanner.setAspectTolerance(0.5f)
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
                    MY_CAMERA_REQUEST_CODE);
            return;
        }
        qrCodeScanner.startCamera();
        qrCodeScanner.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result result) {
                qrCode = result;
                Log.d("data ",result.getText());
                Event e = null;
                if(!databaseHelper.eventDao().getEventByCode(result.getText()).isEmpty()){
                    e = databaseHelper.eventDao().getEventByCode(result.getText()).get(0);
                }
                if(e!=null&&e.getEventQrCode().equals(result.getText())){
                    Toast.makeText(getApplicationContext(),"Wait Processing!",Toast.LENGTH_LONG).show();
                    MyAsyncTask myAsyncTask = new MyAsyncTask(e.getReward(),getApplicationContext());
                    myAsyncTask.execute();
                }else {
                    Toast.makeText(getApplicationContext(),"Sorry You Haven't Registered For Event!",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }


    class MyAsyncTask extends AsyncTask<String,String,String>{
        int rewardPoint;
        Context context;
        public MyAsyncTask(int rewardPoint, Context context){
            this.rewardPoint = rewardPoint;
            this.context=context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            createLoadingScreeen();

        }

        @Override
        protected String doInBackground(String... strings) {
            Solidity solidity = new Solidity(context);
            return solidity.RewardUser(databaseHelper.userDao().getAllUSer().get(0).getPublicKey(),rewardPoint);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
                finishLoadingScreen("Transcation Was Successfully Done!");
                addCompletedEventOnDatabase();
                return;
            }
            finishLoadingScreen("Failed!");

        }
    }

    private void addCompletedEventOnDatabase() {

    }

    private void createLoadingScreeen() {
//         bottomSheet = new BottomSheet();
//            bottomSheet.show(getSupportFragmentManager(),BottomSheet.TAG);
        transcationBottomSheet = new TranscationBottomSheet();
        transcationBottomSheet.show(getSupportFragmentManager(),TranscationBottomSheet.TAG);
//        transcationBottomSheet.setMessageForTranscation("Loading.......");

    }

    private void finishLoadingScreen(String message) {
        transcationBottomSheet.setVisibilityForProgressBar(View.INVISIBLE);
        transcationBottomSheet.setMessageForTranscation(message);
    }


    @Override
    protected void onPause()
    {   super.onPause();
        qrCodeScanner.stopCamera();
    }

}
