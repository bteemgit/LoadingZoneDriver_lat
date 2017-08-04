package bteem.com.loadingzonedriver.modules.home;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import bteem.com.loadingzonedriver.R;
import bteem.com.loadingzonedriver.global.AppController;
import bteem.com.loadingzonedriver.global.BaseActivity;

import bteem.com.loadingzonedriver.global.GloablMethods;
import bteem.com.loadingzonedriver.global.LocationTrack;
import bteem.com.loadingzonedriver.global.MessageConstants;
import bteem.com.loadingzonedriver.retrofit.ApiClient;
import bteem.com.loadingzonedriver.retrofit.ApiInterface;
import bteem.com.loadingzonedriver.retrofit.model.DriverStatusUpdateRsponse;
import bteem.com.loadingzonedriver.retrofit.model.MessageCreateResponse;
import bteem.com.loadingzonedriver.retrofit.model.TruckUpdateStatusresponse;
import bteem.com.loadingzonedriver.view.CircleTransformation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;



public class PostedJobDetailsActivity extends BaseActivity {
    @NonNull
    @BindView(R.id.textCustomerName)
    TextView textViewCutomerName;
    @NonNull
    @BindView(R.id.textCustomerEmail)
    TextView textViewCutomerEmail;
    @NonNull
    @BindView(R.id.textCustomerMobile)
    TextView textViewCutomerMobile;
    @NonNull
    @BindView(R.id.text_From)
    TextView textViewJob_From;
    @NonNull
    @BindView(R.id.text_To)
    TextView textViewJob_To;
    @NonNull
    @BindView(R.id.textTotalDist)
    TextView textViewJobTotalDist;
    @NonNull
    @BindView(R.id.textRequestedDate)
    TextView textViewRequestedDate;
    @NonNull
    @BindView(R.id.textJobDate)
    TextView textViewJobDate;
    @NonNull
    @BindView(R.id.textQutoation)
    TextView textViewQutoation;
    @NonNull
    @BindView(R.id.textLoadingMaterial)
    TextView textViewLoadingMaterial;
    @NonNull
    @BindView(R.id.textLoadingMat_Weight)
    TextView textLoadingMat_Weight;
    @NonNull
    @BindView(R.id.textStartTime)
    TextView textStartTime;
    @NonNull
    @BindView(R.id.textEndTime)
    TextView textEndTime;
    @NonNull
    @BindView(R.id.textTruckName)
    TextView textTruckName;

    @NonNull
    @BindView(R.id.textTotalDistance)
    TextView textViewTotalDistance;
    @NonNull
    @BindView(R.id.textJobFrom)
    TextView textViewJobFrom;
    @NonNull
    @BindView(R.id.texJobTo)
    TextView textViewJobTo;
    @NonNull
    @BindView(R.id.texLoadingDate)
    TextView textViewLoadingDate;
    @NonNull
    @BindView(R.id.textTruckSize)
    TextView textViewTruckSize;
    @NonNull
    @BindView(R.id.textTruckType)
    TextView textViewTruckType;
    @NonNull
    @BindView(R.id.textPaymentMode)
    TextView textViewPaymentMode;
    @NonNull
    @BindView(R.id.textCurrency)
    TextView textViewCurrency;
    @NonNull
    @BindView(R.id.ivCustomerProfilePhoto)
    ImageView ivCustomerProfilePhoto;
    @NonNull
    @BindView(R.id.rootview)
    RelativeLayout rootView;
    @NonNull
    @BindView(R.id.textDriverUpdate)
    TextView textDriverUpdate;
    @NonNull
    @BindView(R.id.relativeTruckUpdate)
    RelativeLayout relativeTruckUpdate;

    //Fab ------------

    @NonNull
    @BindView(R.id.material_design_android_floating_action_menu)
    FloatingActionMenu floatingActionMenu;

    @NonNull
    @BindView(R.id.fabmessage_customer)
    FloatingActionButton  fabmsgCustomer;

    @NonNull
    @BindView(R.id.fabcall_customer)
    FloatingActionButton  fabcallCustomer;

    @NonNull
    @BindView(R.id.fabmessage_provider)
    FloatingActionButton  fabmsgProvider;

    @NonNull
    @BindView(R.id.fabcall_provider)
    FloatingActionButton  fabcallProvider;

    //--------------

    Boolean isGPS_Enabled;
    //GPS
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;


    double longitude;
    double latitude;
   //----------------

    private static int REQUEST_CODE = 41;
    String JobId, isFrom, driver_id;
    private ApiInterface apiService;
    String truck_status,vehicle_id;

    View view;
    String CutomerMobile= null;
    String providerPhoneno = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted_job_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Job Detail");

//back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        JobId = getIntent().getStringExtra("JobId");
        isFrom = getIntent().getStringExtra("isFrom");
        String profilepic = getIntent().getStringExtra("profilepic");
        String CutomerName = getIntent().getStringExtra("name");
        String CutomerEmail = getIntent().getStringExtra("email");
         CutomerMobile = getIntent().getStringExtra("phone1");
        String Job_From = getIntent().getStringExtra("FromLoc_name");
        String Job_To = getIntent().getStringExtra("ToLoc_name");
        String JobTotalDist = getIntent().getStringExtra("LocationDistance");
        String RequestedDate = getIntent().getStringExtra("DateRequested");
        String JobDate = getIntent().getStringExtra("DateOfLoading");
        String QutoationCount = getIntent().getStringExtra("QuotationCount");
        String LoadingMaterial = getIntent().getStringExtra("Material_name");
        String TotalDistance = getIntent().getStringExtra("LocationDistance");
        String JobFrom = getIntent().getStringExtra("FromLoc_name");
        String JobTo = getIntent().getStringExtra("ToLoc_name");
        String TruckSize = getIntent().getStringExtra("TruckSize_dimension");
        String TruckType = getIntent().getStringExtra("TruckType_name");
        String PaymentMode = getIntent().getStringExtra("PaymentType_name");
        String expected_start_date = getIntent().getStringExtra("expected_start_date");
        String expected_end_date = getIntent().getStringExtra("expected_end_date");



        String expected_start_time = getIntent().getStringExtra("expected_start_time");
        String expected_end_time = getIntent().getStringExtra("expected_end_time");

        String StartDateAndTime =expected_start_date+" "+expected_start_time;
        String EndDateAndTime =expected_end_date+" "+expected_end_time;

        String truck_name = getIntent().getStringExtra("truck_name");

        providerPhoneno = getIntent().getStringExtra("provider_phone_no");

        String material_weight = getIntent().getStringExtra("Materialweight");

        driver_id = getIntent().getStringExtra("driver_id");
        vehicle_id=getIntent().getStringExtra("vehicle_id");


        textViewCutomerName.setText(CutomerName);
        textViewCutomerEmail.setText(CutomerEmail);
        textViewCutomerMobile.setText(CutomerMobile);
        textViewJob_From.setText(Job_From);
        textViewJob_To.setText(Job_To);
        textViewJobTotalDist.setText(JobTotalDist);
        textViewRequestedDate.setText(RequestedDate);
        textViewJobDate.setText(JobDate);
        textViewQutoation.setText(QutoationCount);
        textStartTime.setText(StartDateAndTime);
        textEndTime.setText(EndDateAndTime);
        textViewLoadingMaterial.setText(LoadingMaterial);
        textTruckName.setText(truck_name);
        textViewTotalDistance.setText(TotalDistance);
        textViewJobFrom.setText(JobFrom);
        textViewJobTo.setText(JobTo);
        textViewTruckSize.setText(TruckSize);
        textViewTruckType.setText(TruckType);
        textViewPaymentMode.setText(PaymentMode);

        textLoadingMat_Weight.setText(material_weight);
        Picasso.with(PostedJobDetailsActivity.this)
                .load(profilepic)
                .resize(80, 80)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(ivCustomerProfilePhoto);

        getTruckUpdation(vehicle_id);

//BG Dimming while Fab open
        view = findViewById(R.id.background_dimmer);
        floatingActionMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {

                if (opened) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }

            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(PostedJobDetailsActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        locationTrack.stopListener();
    }*/






    // for sending messages to Customer
    @NonNull
    @OnClick(R.id.fabmessage_customer)
    public void messageCustomer() {
        messageDilog();
        btnSentMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = et_subject.getText().toString();
                String message = et_message.getText().toString();
                String message_type_id = "6"; //Driver to Customer based on Job Post"
                if (message.length() > 0 && subject.length() > 0) {
                    if (isConnectingToInternet(PostedJobDetailsActivity.this)) {
                        sendMessage(subject, message, message_type_id);

                    } else {
                        showSnakBar(rootView, MessageConstants.INTERNET);
                    }
                } else {
                    showSnakBar(rootView, "Please type messge");
                }
            }
        });
    }

    // for sending new message to customer or drvier
    private void sendMessage(String subject, String message, String message_type_id) {

        showProgressDialog(PostedJobDetailsActivity.this, "sending...");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<MessageCreateResponse> call = apiService.CreateMessage(GloablMethods.API_HEADER + acess_token, JobId, message_type_id, subject, message);
        call.enqueue(new Callback<MessageCreateResponse>() {
            @Override
            public void onResponse(Call<MessageCreateResponse> call, Response<MessageCreateResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful())

                {
//                    if (response.body().getMeta().getStatus().equals("true")) {
                    et_message.setText("");
                    et_subject.setText("");
                    showSnakBar(rootView, "Message sent");
                    finish();
//                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        showSnakBar(rootView, meta.getString("message"));


                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }


            }

            @Override
            public void onFailure(Call<MessageCreateResponse> call, Throwable t) {
                hideProgressDialog();
            }
        });
    }

   /* //fab clicks
    @OnClick(R.id.fabmessage_customer)
    void fabmsgcustomer_click() {
        Toast.makeText(this, "fabmessage_customer", Toast.LENGTH_SHORT).show();

    }
*/

    @OnClick(R.id.fabcall_customer)
    void fabcallcustomer_click() {
        if (isPermissionGranted()) {
            call_action(CutomerMobile);
        }
    }

    public void call_action(String PhoneNo) {

        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + PhoneNo ));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Log.e("Calling a Phone Number", "Call failed", activityException);
        }
       /* try
        {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", postedUserMobile, null));
            startActivity(intent);
        }
        catch (Exception e)
        {
            System.out.print(e);
        }*/

    }


    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }

    @OnClick(R.id.fabmessage_provider)
    void fabmessageprovider_click() {
        messageDilog();
        btnSentMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = et_subject.getText().toString();
                String message = et_message.getText().toString();
                String message_type_id = "5"; //Driver to Provider based on Job Post
                if (message.length() > 0 && subject.length() > 0) {
                    if (isConnectingToInternet(PostedJobDetailsActivity.this)) {
                        sendMessage(subject, message, message_type_id);


                    } else {
                        showSnakBar(rootView, MessageConstants.INTERNET);
                    }
                } else {
                    showSnakBar(rootView, "Please type messge");
                }
            }
        });
    }


    @OnClick(R.id.fabcall_provider)
    void fabcallprovider_click() {
        if (isPermissionGranted()) {
            call_action(providerPhoneno);
        }
    }





    @Override
    protected void onResume() {
        super.onResume();
        if (driver_id != null)
            getTruckUpdation(vehicle_id);

    }


    @NonNull
    @OnClick(R.id.relativeTruckUpdate)
    public void updateTruck() {

        getCurrentLocation();

        if(isGPS_Enabled == true) {
            if (truck_status!=null) {
                if (truck_status.equals("free")) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    TruckStartToCustomerLocation(JobId,longitude,latitude);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(PostedJobDetailsActivity.this);
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();



                }
                if (truck_status.equals("initiated")) {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    LoadingMaterials(JobId,longitude,latitude);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(PostedJobDetailsActivity.this);
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();





                }
                if (truck_status.equals("loading")) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    TruckOntheWayToDestination(JobId,longitude,latitude);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(PostedJobDetailsActivity.this);
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();



                }
                if (truck_status.equals("in-service")) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    TruckUnLoadingGoods(JobId,longitude,latitude);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(PostedJobDetailsActivity.this);
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();



                }
                if (truck_status.equals("unloading")) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    BackFromDestination(JobId,longitude,latitude);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(PostedJobDetailsActivity.this);
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();




                }
                if (truck_status.equals("return")) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    ReachedOrigin(JobId,longitude,latitude);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(PostedJobDetailsActivity.this);
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            }
        }
//-----------------------------------------------------------
    }

    private void getCurrentLocation() {
        //gps
        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
        locationTrack = new LocationTrack(PostedJobDetailsActivity.this);
        if (locationTrack.canGetLocation()) {
             longitude = locationTrack.getLongitude();
             latitude = locationTrack.getLatitude();
                isGPS_Enabled = true;
               /* String flag = String.valueOf(isGPS_Enabled);
                Toast.makeText(PostedJobDetailsActivity.this, flag, Toast.LENGTH_SHORT).show();*/


            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
        } else {

            locationTrack.showSettingsAlert();
        }
    }

    // Getting status updation of truck on runnning
    public void getTruckUpdation
    (String driver_id) {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<DriverStatusUpdateRsponse> call = apiService.TruckRunningStatus(GloablMethods.API_HEADER + acess_token, driver_id);
        call.enqueue(new Callback<DriverStatusUpdateRsponse>() {
            @Override
            public void onResponse(Call<DriverStatusUpdateRsponse> call, retrofit2.Response<DriverStatusUpdateRsponse> response) {

                hideProgressDialog();
                if (response.isSuccessful()) {
                    truck_status = response.body().getRunningStatus().getRunningStatusName();
                    if (truck_status.equals("free")) {
                        textDriverUpdate.setText("Start Job");
                    }
                    if (truck_status.equals("initiated")) {
                        textDriverUpdate.setText("Load the Goods");
                    }
                    if (truck_status.equals("loading")) {
                        textDriverUpdate.setText("Start Journey");
                    }
                    if (truck_status.equals("in-service")) {
                        textDriverUpdate.setText("Unload Goods");
                    }
                    if (truck_status.equals("unloading")) {
                        textDriverUpdate.setText("Complete the Job");
                    }
                    if (truck_status.equals("return")) {
                        textDriverUpdate.setText("Reached Provider Location");
                    }



                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        showSnakBar(rootView, meta.getString("message"));

                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());
                    }

                }

            }

            @Override
            public void onFailure(Call<DriverStatusUpdateRsponse> call, Throwable t) {
                // Log error here since request failed
                hideProgressDialog();
            }
        });
    }



    //api call truck started to the customer location
    public void TruckStartToCustomerLocation(String job_id,Double longitude,Double latitude) {

        showProgressDialog(PostedJobDetailsActivity.this, "Loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckUpdateStatusresponse> call = apiService.StartToCustomerLocation(GloablMethods.API_HEADER +acess_token, job_id,longitude,latitude);
        call.enqueue(new Callback<TruckUpdateStatusresponse>() {
            @Override
            public void onResponse(Call<TruckUpdateStatusresponse> call, retrofit2.Response<TruckUpdateStatusresponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMeta().getStatus().equals(true)) {

                        showSnakBar(rootView, response.body().getMeta().getMessage());
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(rootView, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<TruckUpdateStatusresponse> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(PostedJobDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("exception>>>", t.getMessage());

            }
        });

    }


    //api call for loading materials
    public void LoadingMaterials(String job_id,Double longitude,Double latitude) {

        showProgressDialog(PostedJobDetailsActivity.this, "Loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckUpdateStatusresponse> call = apiService.LoadingMaterial(GloablMethods.API_HEADER +acess_token, job_id,longitude,latitude);
        call.enqueue(new Callback<TruckUpdateStatusresponse>() {
            @Override
            public void onResponse(Call<TruckUpdateStatusresponse> call, retrofit2.Response<TruckUpdateStatusresponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMeta().getStatus().equals(true)) {

                        showSnakBar(rootView, response.body().getMeta().getMessage());
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(rootView, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<TruckUpdateStatusresponse> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(PostedJobDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("exception>>>", t.getMessage());

            }
        });

    }

    //api call for truck on the way to destination
    public void TruckOntheWayToDestination(String job_id,Double longitude,Double latitude) {

        showProgressDialog(PostedJobDetailsActivity.this, "Loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckUpdateStatusresponse> call = apiService.OnthewayToDestination(GloablMethods.API_HEADER +acess_token, job_id,longitude,latitude);
        call.enqueue(new Callback<TruckUpdateStatusresponse>() {
            @Override
            public void onResponse(Call<TruckUpdateStatusresponse> call, retrofit2.Response<TruckUpdateStatusresponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMeta().getStatus().equals(true)) {

                        showSnakBar(rootView, response.body().getMeta().getMessage());
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(rootView, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<TruckUpdateStatusresponse> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(PostedJobDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("exception>>>", t.getMessage());

            }
        });

    }

    //api call for truck on the way to destination
    public void TruckUnLoadingGoods(String job_id,Double longitude,Double latitude) {

        showProgressDialog(PostedJobDetailsActivity.this, "Loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckUpdateStatusresponse> call = apiService.TruckUnLoad(GloablMethods.API_HEADER +acess_token, job_id,longitude,latitude);
        call.enqueue(new Callback<TruckUpdateStatusresponse>() {
            @Override
            public void onResponse(Call<TruckUpdateStatusresponse> call, retrofit2.Response<TruckUpdateStatusresponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMeta().getStatus().equals(true)) {

                        showSnakBar(rootView, response.body().getMeta().getMessage());
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(rootView, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<TruckUpdateStatusresponse> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(PostedJobDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("exception>>>", t.getMessage());

            }
        });

    }

    //api call for truck on the way to destination
    public void BackFromDestination(String job_id,Double longitude,Double latitude) {

        showProgressDialog(PostedJobDetailsActivity.this, "Loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckUpdateStatusresponse> call = apiService.BackToDestination(GloablMethods.API_HEADER +acess_token, job_id,longitude,latitude);
        call.enqueue(new Callback<TruckUpdateStatusresponse>() {
            @Override
            public void onResponse(Call<TruckUpdateStatusresponse> call, retrofit2.Response<TruckUpdateStatusresponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMeta().getStatus().equals(true)) {

                        showSnakBar(rootView, response.body().getMeta().getMessage());
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(rootView, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<TruckUpdateStatusresponse> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(PostedJobDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("exception>>>", t.getMessage());

            }
        });

    }
    //api call for truck reached origion
    public void ReachedOrigin(String job_id,Double longitude,Double latitude) {

        showProgressDialog(PostedJobDetailsActivity.this, "Loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckUpdateStatusresponse> call = apiService.ReachedOrigion(GloablMethods.API_HEADER +acess_token, job_id,longitude,latitude);
        call.enqueue(new Callback<TruckUpdateStatusresponse>() {
            @Override
            public void onResponse(Call<TruckUpdateStatusresponse> call, retrofit2.Response<TruckUpdateStatusresponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMeta().getStatus().equals(true)) {

                        showSnakBar(rootView, response.body().getMeta().getMessage());
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(rootView, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<TruckUpdateStatusresponse> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(PostedJobDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("exception>>>", t.getMessage());

            }
        });

    }

}
