package bteem.com.loadingzonedriver.modules.home;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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

import bteem.com.loadingzonedriver.R;
import bteem.com.loadingzonedriver.global.AppController;
import bteem.com.loadingzonedriver.global.BaseActivity;
import bteem.com.loadingzonedriver.global.GloablMethods;
import bteem.com.loadingzonedriver.retrofit.ApiClient;
import bteem.com.loadingzonedriver.retrofit.ApiInterface;
import bteem.com.loadingzonedriver.retrofit.model.DriverStatusUpdateRsponse;
import bteem.com.loadingzonedriver.retrofit.model.TruckUpdateStatusresponse;
import bteem.com.loadingzonedriver.view.CircleTransformation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

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
        String Qutoation = getIntent().getStringExtra("QuotationCount");
        String LoadingMaterial = getIntent().getStringExtra("Material_name");
        String TotalDistance = getIntent().getStringExtra("LocationDistance");
        String JobFrom = getIntent().getStringExtra("FromLoc_name");
        String JobTo = getIntent().getStringExtra("ToLoc_name");
        String TruckSize = getIntent().getStringExtra("TruckSize_dimension");
        String TruckType = getIntent().getStringExtra("TruckType_name");
        String PaymentMode = getIntent().getStringExtra("PaymentType_name");
        String expected_start_date = getIntent().getStringExtra("expected_start_date");
        String expected_end_date = getIntent().getStringExtra("expected_end_date");
        String truck_name = getIntent().getStringExtra("truck_name");

        providerPhoneno = getIntent().getStringExtra("provider_phone_no");

        driver_id = getIntent().getStringExtra("driver_id");
        vehicle_id=getIntent().getStringExtra("vehicle_id");
        String[] splited_sDate = expected_start_date.split("\\s+");
        String[] splited_eDate = expected_end_date.split("\\s+");

        textViewCutomerName.setText(CutomerName);
        textViewCutomerEmail.setText(CutomerEmail);
        textViewCutomerMobile.setText(CutomerMobile);
        textViewJob_From.setText(Job_From);
        textViewJob_To.setText(Job_To);
        textViewJobTotalDist.setText(JobTotalDist);
        textViewRequestedDate.setText(RequestedDate);
        textViewJobDate.setText(splited_eDate[0]);
        textViewQutoation.setText(splited_sDate[0]);
        textStartTime.setText(splited_sDate[1]);
        textEndTime.setText(splited_eDate[1]);
        textViewLoadingMaterial.setText(LoadingMaterial);
        textTruckName.setText(truck_name);
        //  textViewBudget.setText(Budget);
        textViewTotalDistance.setText(TotalDistance);
        textViewJobFrom.setText(JobFrom);
        textViewJobTo.setText(JobTo);
        //  textViewLoadingDate.setText(LoadingDate);
        textViewTruckSize.setText(TruckSize);
        textViewTruckType.setText(TruckType);
        textViewPaymentMode.setText(PaymentMode);
        //textViewCurrency.setText(Currency);
        Picasso.with(PostedJobDetailsActivity.this)
                .load(new File(profilepic))
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




    //fab clicks
    @OnClick(R.id.fabmessage_customer)
    void fabmsgcustomer_click() {
        Toast.makeText(this, "fabmessage_customer", Toast.LENGTH_SHORT).show();

    }


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
        Toast.makeText(this, "fabmessage_customer", Toast.LENGTH_SHORT).show();
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
        if (truck_status!=null) {
            if (truck_status.equals("free")) {
                TruckStartToCustomerLocation(JobId);
            }
            if (truck_status.equals("initiated")) {
                LoadingMaterials(JobId);
            }
            if (truck_status.equals("loading")) {
                TruckOntheWayToDestination(JobId);
            }
            if (truck_status.equals("in-service")) {
                TruckUnLoadingGoods(JobId);
            }
            if (truck_status.equals("unloading")) {
                BackFromDestination(JobId);
            }
            if (truck_status.equals("return")) {
                ReachedOrigin(JobId);
            }
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
    public void TruckStartToCustomerLocation(String job_id) {

        showProgressDialog(PostedJobDetailsActivity.this, "Loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckUpdateStatusresponse> call = apiService.StartToCustomerLocation(GloablMethods.API_HEADER +acess_token, job_id);
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
    public void LoadingMaterials(String job_id) {

        showProgressDialog(PostedJobDetailsActivity.this, "Loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckUpdateStatusresponse> call = apiService.LoadingMaterial(GloablMethods.API_HEADER +acess_token, job_id);
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
    public void TruckOntheWayToDestination(String job_id) {

        showProgressDialog(PostedJobDetailsActivity.this, "Loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckUpdateStatusresponse> call = apiService.OnthewayToDestination(GloablMethods.API_HEADER +acess_token, job_id);
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
    public void TruckUnLoadingGoods(String job_id) {

        showProgressDialog(PostedJobDetailsActivity.this, "Loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckUpdateStatusresponse> call = apiService.TruckUnLoad(GloablMethods.API_HEADER +acess_token, job_id);
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
    public void BackFromDestination(String job_id) {

        showProgressDialog(PostedJobDetailsActivity.this, "Loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckUpdateStatusresponse> call = apiService.BackToDestination(GloablMethods.API_HEADER +acess_token, job_id);
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
    public void ReachedOrigin(String job_id) {

        showProgressDialog(PostedJobDetailsActivity.this, "Loading");
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<TruckUpdateStatusresponse> call = apiService.ReachedOrigion(GloablMethods.API_HEADER +acess_token, job_id);
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
