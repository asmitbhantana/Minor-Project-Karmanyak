package org.vansoft.karmanyak.activitie;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.vansoft.karmanyak.R;
import org.vansoft.karmanyak.customElement.BottomCurvedNavigationView;
import org.vansoft.karmanyak.customElement.BottomSheet;
import org.vansoft.karmanyak.database.DatabaseHelper;
import org.vansoft.karmanyak.fragment.EventsFragment;
import org.vansoft.karmanyak.fragment.LocationFragment;
import org.vansoft.karmanyak.fragment.ProfileFragment;
import org.vansoft.karmanyak.fragment.RewardFragment;
import org.vansoft.karmanyak.solidity.Solidity;
import org.vansoft.karmanyak.utils.AllData;
import org.web3j.crypto.Credentials;

import java.security.Provider;
import java.security.Security;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements LocationFragment.OnFragmentInteractionListener {
    private static final int ACTIVITY_CHOOSE_FILE = 222;
    FragmentManager fm;
    Fragment locationFragment,rewardFragment,eventFragment,profileFragemnt;

    @BindView(R.id.content_main)
    FrameLayout contentFrame;

    @BindView(R.id.botttm_nav_bar_id)
    BottomCurvedNavigationView bottomCurvedNavigationView;

    @BindView(R.id.toolbarId)
    Toolbar toolbar;

    @BindView(R.id.qrScannerBtnId)
   public FloatingActionButton qrScannerBtn;

    //for map

    String[] permission;
    final int  LOCATION_PERMISSION_REQUEST_CODE = 122;
    final int  CORSE_LOCATION_PERMISSION_REQUEST_CODE = 123;
    final int STORAGE_WRITABLE_CODE = 124;
    final int STORAGE_READABLE_CODE = 125;

    private AllData allData;
    Boolean locationPermissionGranted = false;
    private boolean filePermissionGranted = false;
    private boolean corseLocationGranted = false;
    private boolean writefilePermissionGranted = false;
    private boolean readPermissionGranted = false;

    BottomSheet bottomSheet = null;
    private String passwordProvided;

    DatabaseHelper databaseHelper;

    enum REQUEST_TYPE {createWallet,getPrivateKey,getPublicKey}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = DatabaseHelper.getINSTANCE(this);
        init();
        setupBouncyCastle();
    }

    @OnClick(R.id.qrScannerBtnId)
    public void qrScannerBtnIdClick(){
        Intent intent = new Intent(this,QrScanActivity.class);
        startActivity(intent);
    }

    private void init() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_menu_white));
        allData = AllData.getInstance();
        allData.setUpcommingEventList(databaseHelper.eventDao().getAllEvent());
        allData.setUser(databaseHelper.userDao().getAllUSer().get(0));
        getLocationPremission();
//        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.tool_bar_bg));
        fm = getSupportFragmentManager();
        if(locationFragment==null){
            locationFragment = new LocationFragment();
        }
        loadFragment(locationFragment);
        bottomCurvedNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.location_nav_menu_id:
                        if(locationFragment==null){
                            locationFragment = new LocationFragment();
                        }
                        loadFragment(locationFragment);
                        getSupportActionBar().setTitle("Nearby Events");
                        break;
                    case R.id.reward_nav_menu_id:
                        if(rewardFragment==null){
                            rewardFragment = new RewardFragment();
                        }
                        loadFragment(rewardFragment);
                        getSupportActionBar().setTitle("Reedem Rewards");

                        break;
                    case R.id  .event_nav_menu_id:
                        if(eventFragment==null){
                            eventFragment = new EventsFragment();
                        }
                        loadFragment(eventFragment);
                        getSupportActionBar().setTitle("Event List");
                        break;
                    case R.id.profile_nav_menu_id:
                        if(profileFragemnt==null){
                            profileFragemnt = new ProfileFragment();
                        }
                        loadFragment(profileFragemnt);
                        getSupportActionBar().setTitle("User Profile");
                        checkForUserCredential();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_CHOOSE_FILE && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
//            String FilePath = RealPathUtil.getRealPathFromURI_API11to18(this,data.getData()); // should the path be here in this string
            Log.d("Path  ",data.getData().getPath());
            updateUser(data.getData().getPath().replace("/document/raw:",""));
        }
    }

    private void updateUser(String filePath) {
        //databaseHelper.userDao().deletePreviousUser();
        Log.d("data ", String.valueOf(databaseHelper.userDao().getAllUSer().size()));
        allData.getUser().setFilePath(filePath);
//        databaseHelper.userDao().updateUser(allData.getUser());
        allData.getUser().setPassword(passwordProvided);
        databaseHelper.userDao().updateUser(allData.getUser());
//        Solidity solidity = new Solidity(this);
//        Credentials credentials = solidity.getCredentialsFromWallet(passwordProvided,filePath);
//        String publicKey = solidity.getPublicKeyFromCredential(credentials);
//        allData.getUser().setPublicKey(publicKey);
        MyAsyncTask myAsyncTask = new MyAsyncTask(passwordProvided,filePath,this,REQUEST_TYPE.getPublicKey);
        myAsyncTask.execute();
    }

    private void updateUserPublicKey(String publicKey){
        Log.d("key ","public = "+publicKey);
        allData.getUser().setPublicKey(publicKey);
        databaseHelper.userDao().updateUser(allData.getUser());
        //Log.d("key",databaseHelper.userDao().getAllUSer().get(0).getPublicKey());
        bottomSheet.dismiss();
    }

    private void checkForUserCredential() {
        if(allData.getUser().getFilePath() == null){
            bottomSheet = new BottomSheet();
            bottomSheet.show(getSupportFragmentManager(),BottomSheet.TAG);
            bottomSheet.setOnClikedListners(new BottomSheet.onClickedListner() {
                @Override
                public void onProvideButtonClicked() {
                    if(passwordProvided!=null) {
                        if (passwordProvided.length()>0)
                        {
                            chooseFileIntent();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"Please Enter Valid Password!",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onPasswordSubmitted(String s) {
                    passwordProvided = s;
                }

                @Override
                public void onNewWalletCreatedClicked() {
                    if(passwordProvided!=null) {
                        if (passwordProvided.length()>0)
                        {
                            MyAsyncTask myAsyncTask = new MyAsyncTask(passwordProvided," ",getApplicationContext(),REQUEST_TYPE.createWallet);
                            myAsyncTask.execute();                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"Please Enter Valid Password!",Toast.LENGTH_LONG).show();
                    }

                  //  Log.d("wallet",fileName);

                }
            });
        }
    }

    private void chooseFileIntent() {
        Intent chooseFile;
        Intent intent;
        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("*/*");
        intent = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
    }

    class MyAsyncTask extends AsyncTask<String,String,String>{
        String password,destination;
        Context context;
        REQUEST_TYPE request_type;
        public  MyAsyncTask(String password,String destination,Context context,REQUEST_TYPE request_type){
            this.password = password;
            this.destination = destination;
            this.context = context;
            this.request_type = request_type;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            Solidity solidity = new Solidity(context);
            if(request_type==REQUEST_TYPE.createWallet){
                //String path = getFilesDir().getCanonicalFile().getPath();
                return solidity.createWallet(password);
            }
            else if(request_type==REQUEST_TYPE.getPublicKey){
                Credentials credentials =solidity.getCredentialsFromWallet(password,destination);
                return solidity.getPublicKeyFromCredential(credentials);
            }
            else if(request_type==REQUEST_TYPE.getPrivateKey){
                Credentials credentials = solidity.getCredentialsFromWallet(password,destination);
                return solidity.getPrivateKeyFromCredentials(credentials);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(request_type==REQUEST_TYPE.getPrivateKey){

            }else if(request_type==REQUEST_TYPE.getPublicKey){
                updateUserPublicKey(s);
            }else if(request_type==REQUEST_TYPE.createWallet){
                Log.d("File = ",s);
                hideBottomSheet();
                updateUser(s);
            }

        }
    }

    private void hideBottomSheet() {
        bottomSheet.dismiss();
    }

    private void setupBouncyCastle() {
        final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (provider == null) {
            // Web3j will set up the provider lazily when it's first used.
            return;
        }
        if (provider.getClass().equals(BouncyCastleProvider.class)) {
            // BC with same package name, shouldn't happen in real life.
            return;
        }
        // Android registers its own BC provider. As it might be outdated and might not include
        // all needed ciphers, we substitute it with a known BC bundled in the app.
        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
        // of that it's possible to have another BC implementation loaded in VM.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    private void loadFragment(Fragment fragment) {

// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.commit(); // save the changes
//            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_black_24dp));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_bar_menu,menu);
        return true;
    }

    private void getLocationPremission(){
        permission = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){

        }else {
            ActivityCompat.requestPermissions(this,
                    permission,LOCATION_PERMISSION_REQUEST_CODE);
        }
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            corseLocationGranted = true;
        }else {
            ActivityCompat.requestPermissions(this,
                    permission,CORSE_LOCATION_PERMISSION_REQUEST_CODE);
        }
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
            writefilePermissionGranted = true;
        }else {
            ActivityCompat.requestPermissions(this,
                    permission,STORAGE_WRITABLE_CODE);
        }
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
            readPermissionGranted = true;
        }else {
            ActivityCompat.requestPermissions(this,
                    permission,STORAGE_READABLE_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        locationPermissionGranted =false;
        filePermissionGranted = false;
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:
                if(grantResults.length >0){
                    for(int i =0 ;i<grantResults.length;i++){
                        if((grantResults[i] == PackageManager.PERMISSION_GRANTED)){
                            locationPermissionGranted = false;
                            return;
                        }
                        locationPermissionGranted = true;
                        //initilize map
                    }
                }
                break;

            case CORSE_LOCATION_PERMISSION_REQUEST_CODE:
                if(grantResults.length>0){
                    for (int result :
                            grantResults) {
                        if(result==PackageManager.PERMISSION_GRANTED){
                            corseLocationGranted=false;
                        }
                    }
                    corseLocationGranted=true;
                }
                break;
            case STORAGE_WRITABLE_CODE:
                if(grantResults.length>0){
                    for (int result :
                            grantResults) {
                        if(result==PackageManager.PERMISSION_GRANTED){
                            writefilePermissionGranted=false;
                        }
                    }
                    writefilePermissionGranted=true;
                }
                break;

            case STORAGE_READABLE_CODE:
                if(grantResults.length>0){
                    for (int result :
                            grantResults) {
                        if(result==PackageManager.PERMISSION_GRANTED){
                            readPermissionGranted=false;
                        }
                    }
                    readPermissionGranted=true;
                }
                break;


        }
    }

    @Override
    public void askLocationPermission() {
        getLocationPremission();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        databaseHelper = DatabaseHelper.getINSTANCE(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        databaseHelper = DatabaseHelper.getINSTANCE(this);

    }
}
