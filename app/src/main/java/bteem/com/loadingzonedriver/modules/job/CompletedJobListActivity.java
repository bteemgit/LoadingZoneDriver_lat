package bteem.com.loadingzonedriver.modules.job;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bteem.com.loadingzonedriver.R;
import bteem.com.loadingzonedriver.global.AppController;
import bteem.com.loadingzonedriver.global.BaseActivity;
import bteem.com.loadingzonedriver.global.GloablMethods;
import bteem.com.loadingzonedriver.global.MessageConstants;

import bteem.com.loadingzonedriver.modules.home.PostedJobDetailsActivity;
import bteem.com.loadingzonedriver.modules.home.PostedJobListAdapter;
import bteem.com.loadingzonedriver.recyclerview.EndlessRecyclerView;
import bteem.com.loadingzonedriver.recyclerview.RecyclerItemClickListener;
import bteem.com.loadingzonedriver.retrofit.ApiClient;
import bteem.com.loadingzonedriver.retrofit.ApiInterface;
import bteem.com.loadingzonedriver.retrofit.model.JobList;
import bteem.com.loadingzonedriver.retrofit.model.PostedJobResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class CompletedJobListActivity extends BaseActivity {
    private ApiInterface apiService;
    @BindView(R.id.recyclerViewHomePostedJob)
    EndlessRecyclerView endlessRecyclerViewPostedJob;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.progressBarFooter)
    ProgressBar progressBar;
    @BindView(R.id.rootView)
    RelativeLayout relativeLayoutRoot;
    private CompletedJobListAdapter completedJobListAdapter;
    private List<JobList> jobList = new ArrayList<>();
    private int limit = 30;
    private int offset = 1;
    private boolean hasReachedTop = false;
    private EndlessRecyclerView.PaginationListener paginationListener = new EndlessRecyclerView.PaginationListener() {
        @Override
        public void onReachedBottom() {
            if (isConnectingToInternet(getApplicationContext()))
            {
                getJobPosted();
            }
            else {
                showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);
            }
        }

        @Override
        public void onReachedTop() {
            hasReachedTop = true;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completedjob);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Completed Job");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        refreshLayout.setRefreshing(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        endlessRecyclerViewPostedJob.setLayoutManager(layoutManager);
        setUpListeners();

        if (isConnectingToInternet(getApplicationContext()))
        {
            getJobPosted();
        }
        else {
            showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);
        }

    }

    private void setUpListeners() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        endlessRecyclerViewPostedJob.setLayoutManager(layoutManager);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
// refreshLayout.setRefreshing(true);
                offset = 1;
                getJobPosted();
            }
        });

        endlessRecyclerViewPostedJob.addPaginationListener(paginationListener);
        endlessRecyclerViewPostedJob.addOnItemTouchListener(new RecyclerItemClickListener(CompletedJobListActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent i=new Intent(CompletedJobListActivity.this,CompletedJobDetailsActivity.class);
                String JobId = String.valueOf(jobList.get(position).getJobId());
                String vehicle_id= String.valueOf(jobList.get(position).getAssignedVehicle().getVehicleDetails().getProviderVehicleId());
               /* String name = jobList.get(position).getCustomer().getName();
                String email = jobList.get(position).getCustomer().getEmail();
                String phone1 = jobList.get(position).getCustomer().getPhone1();
                String profilepic = jobList.get(position).getCustomer().getProfilePic();
                String FromLoc_latt = jobList.get(position).getFromLocation().getLatitude();
                String FromLoc_long = jobList.get(position).getFromLocation().getLongitude();
                String FromLoc_name = jobList.get(position).getFromLocation().getName();
                String ToLoc_latt = jobList.get(position).getToLocation().getLatitude();
                String ToLoc_long = jobList.get(position).getToLocation().getLongitude();
                String ToLoc_name = jobList.get(position).getToLocation().getName();
                String Material_name = jobList.get(position).getMaterial().getMaterialName();
                Integer Material_id = jobList.get(position).getMaterial().getMaterialId();
                String vehicle_id= String.valueOf(jobList.get(position).getAssignedVehicle().getVehicleDetails().getProviderVehicleId());
                String TruckSize_dimension = jobList.get(position).getTruckSize().getTruckSizeDimension();
                String truck_name=jobList.get(position).getAssignedVehicle().getVehicleDetails().getCustomName();
                String driver_id= String.valueOf(jobList.get(position).getAssignedVehicle().getJobDriverId());
                String MaterialDescription = jobList.get(position).getMaterialDescription();
//                String weight =jobList.get(position).getWeight();
//                String DateOfLoading = jobList.get(position).getDateOfLoading();
                String PaymentType_name = jobList.get(position).getPaymentType().getPaymentTypeName();
                Integer PaymentType_id = jobList.get(position).getPaymentType().getPaymentTypeId();
                String TruckType_name = jobList.get(position).getTruckType().getTruckTypeName();
                //  String TruckType_id = jobList.get(position).getTruckType().getTruckTypeId();

                Integer TruckSize_id = jobList.get(position).getTruckSize().getTruckSizeId();
                //  String Currency_name = jobList.get(position).getCurrency().getCurrencyName();
                String LocationDistance = String.valueOf(jobList.get(position).getLocationDistance());
                String DateRequested = jobList.get(position).getDateRequested();
                String DateRequestedRelative = jobList.get(position).getDateRequestedRelative();
                // String Budget = jobList.get(position).getBudget();
                String QuotationCount = jobList.get(position).getQuotationCount();
                //   String HasActiveQuotations = jobList.get(position).getHasActiveQuotations();
                String JobStatus = jobList.get(position).getJobStatus().getName();
                String expected_start_date=jobList.get(position).getAssignedVehicle().getExpectedStartDate();
                String expected_end_date=jobList.get(position).getAssignedVehicle().getExpectedEndDate();*/

                i.putExtra("isFrom","CompletedJob");
                i.putExtra("JobId",JobId);
                i.putExtra("vehicle_id",vehicle_id);
               /* i.putExtra("name",name);
                i.putExtra("email",email);
                i.putExtra("phone1",phone1);
                i.putExtra("profilepic",profilepic);
                i.putExtra("FromLoc_latt",FromLoc_latt);
                i.putExtra("FromLoc_long",FromLoc_long);
                i.putExtra("FromLoc_name",FromLoc_name);
                i.putExtra("ToLoc_latt",ToLoc_latt);
                i.putExtra("ToLoc_long",ToLoc_long);
                i.putExtra("ToLoc_name",ToLoc_name);
                i.putExtra("Material_name",Material_name);
                i.putExtra("Material_id",Material_id);
                i.putExtra("MaterialDescription",MaterialDescription);
                i.putExtra("expected_start_date",expected_start_date);
                i.putExtra("expected_end_date",expected_end_date);
                i.putExtra("PaymentType_name",PaymentType_name);
                i.putExtra("PaymentType_id",PaymentType_id);
                i.putExtra("TruckType_name",TruckType_name);
                i.putExtra("driver_id",driver_id);
                i.putExtra("TruckSize_id",TruckSize_id);
                i.putExtra("TruckSize_dimension",TruckSize_dimension);
                i.putExtra("truck_name",truck_name);
                i.putExtra("LocationDistance",LocationDistance);
                i.putExtra("DateRequested",DateRequested);
                i.putExtra("DateRequestedRelative",DateRequestedRelative);
                //   i.putExtra("Budget",Budget);
                i.putExtra("QuotationCount",QuotationCount);
                i.putExtra("vehicle_id",vehicle_id);
                i.putExtra("JobStatus",JobStatus);*/
                startActivity(i);
            }
        }));

    }

    // Getting the job posted by the customer
    public void getJobPosted
    () {

        if (offset == 1) {
            showProgressDialog(CompletedJobListActivity.this, "loading");
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
        apiService = ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<PostedJobResponse> call = apiService.CompletedJob(GloablMethods.API_HEADER + acess_token, offset);
        call.enqueue(new Callback<PostedJobResponse>() {
            @Override
            public void onResponse(Call<PostedJobResponse> call, retrofit2.Response<PostedJobResponse> response) {
                refreshLayout.setRefreshing(false);
                hideProgressDialog();
                if (response.isSuccessful() ) {
                    if (!response.body().getJobList().isEmpty()) {
                        List<JobList> JobList = response.body().getJobList();
                        if (offset == 1) {
                            jobList = JobList;
                            updateEndlessRecyclerView();
                            hideProgressDialog();
                        } else {
                            progressBar.setVisibility(View.VISIBLE);
                            for (JobList itemModel : JobList) {
                                jobList.add(itemModel);
                            }
                        }
                        if (JobList.size() < limit) {
                            endlessRecyclerViewPostedJob.setHaveMoreItem(false);
                        } else {
                            endlessRecyclerViewPostedJob.setHaveMoreItem(true);
                        }
                        completedJobListAdapter.notifyDataSetChanged();
                        offset = offset + 1;
                    } else {
                        endlessRecyclerViewPostedJob.setHaveMoreItem(false);
                    }

                } else {
                    finish();
                    endlessRecyclerViewPostedJob.setHaveMoreItem(false);
                }
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        showSnakBar(relativeLayoutRoot, meta.getString("message"));

                    } catch (Exception e) {
                        Log.d("exception",e.getMessage());
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PostedJobResponse> call, Throwable t) {
                // Log error here since request failed
                hideProgressDialog();
            }
        });
    }


    private void updateEndlessRecyclerView() {
        completedJobListAdapter = new CompletedJobListAdapter(jobList, R.layout.item_home_postedjob, getApplicationContext());
        endlessRecyclerViewPostedJob.setAdapter(completedJobListAdapter);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
