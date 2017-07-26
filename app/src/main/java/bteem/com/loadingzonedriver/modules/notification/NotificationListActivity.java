package bteem.com.loadingzonedriver.modules.notification;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;



import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bteem.com.loadingzonedriver.R;
import bteem.com.loadingzonedriver.global.AppController;
import bteem.com.loadingzonedriver.global.BaseActivity;
import bteem.com.loadingzonedriver.global.GloablMethods;
import bteem.com.loadingzonedriver.global.MessageConstants;

import bteem.com.loadingzonedriver.recyclerview.EndlessRecyclerView;
import bteem.com.loadingzonedriver.recyclerview.RecyclerItemClickListener;
import bteem.com.loadingzonedriver.retrofit.ApiClient;
import bteem.com.loadingzonedriver.retrofit.ApiInterface;
import bteem.com.loadingzonedriver.retrofit.model.Meta;
import bteem.com.loadingzonedriver.retrofit.model.NotificationList;
import bteem.com.loadingzonedriver.retrofit.model.NottificationListResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class NotificationListActivity extends BaseActivity {
    @BindView(R.id.recyclerViewNottificationList)
    EndlessRecyclerView endlessRecyclerViewNottificationList;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.progressBarFooter)
    ProgressBar progressBar;
    @BindView(R.id.rootView)
    RelativeLayout relativeLayoutRoot;
    private int limit = 30;
    private int offset = 1;
    private boolean hasReachedTop = false;
    ApiInterface apiService;
    private List<NotificationList> nottificationList = new ArrayList<>();
    NotificationListAdapter notificationListAdapter;
    public static String isFrom="Nottification";
    private EndlessRecyclerView.PaginationListener paginationListener = new EndlessRecyclerView.PaginationListener() {
        @Override
        public void onReachedBottom() {

            getNottificationList();
        }

        @Override
        public void onReachedTop() {
            hasReachedTop = true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        ButterKnife.bind(this);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Notification");
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        refreshLayout.setRefreshing(false);
        setUpListeners();
        if (isConnectingToInternet(getApplicationContext()))
            getNottificationList();
        else {
            showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setUpListeners() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        endlessRecyclerViewNottificationList.setLayoutManager(layoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
// refreshLayout.setRefreshing(true);
                offset = 1;
                getNottificationList();
            }
        });

        endlessRecyclerViewNottificationList.addPaginationListener(paginationListener);
        endlessRecyclerViewNottificationList.addOnItemTouchListener(new RecyclerItemClickListener(NotificationListActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

              /*  int nottification_id = nottificationList.get(position).getNotificationId();
                String notification_meta_name = nottificationList.get(position).getNotificationMeta().getName();
                if (isConnectingToInternet(NottificationListActivity.this)) {
                    ReadNottification(nottification_id);
                } else {
                    showSnakBar(relativeLayoutRoot,MessageConstants.INTERNET);
                }
                //for redirecting in to job details
                if (notification_meta_name.contains("job"))
                {
                    Intent i = new Intent(getApplicationContext(), MyJobtabViewActivity.class);
                    String job_id = String.valueOf(nottificationList.get(position).getNotificationMeta().getJobId());
                    i.putExtra("job_id", job_id);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else if (notification_meta_name.contains("quotation")) {
                    Intent i = new Intent(getApplicationContext(), QutationDetailsActivity.class);
                    String quotationId = String.valueOf(nottificationList.get(position).getNotificationMeta().getQuotationId());
                    i.putExtra("qutation_id", quotationId);
                    i.putExtra("isFrom",isFrom);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }*/

            }
        }));

    }

    public void getNottificationList
            () {

        if (offset == 1) {
            showProgressDialog(NotificationListActivity.this, "loading");
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<NottificationListResponse> call = apiService.NotificationList(GloablMethods.API_HEADER + acess_token, offset);
        call.enqueue(new Callback<NottificationListResponse>() {
            @Override
            public void onResponse(Call<NottificationListResponse> call, retrofit2.Response<NottificationListResponse> response) {


                refreshLayout.setRefreshing(false);
                hideProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    if (!response.body().getNotificationList().isEmpty()) {
                        List<NotificationList> notificationList = response.body().getNotificationList();
                        if (offset == 1) {
                            nottificationList = notificationList;
                            updateEndlessRecyclerView();
                            hideProgressDialog();
                        } else {
                            progressBar.setVisibility(View.VISIBLE);
                            for (NotificationList itemModel : notificationList) {
                                nottificationList.add(itemModel);
                            }
                        }
                        if (notificationList.size() < limit) {
                            endlessRecyclerViewNottificationList.setHaveMoreItem(false);
                        } else {
                            endlessRecyclerViewNottificationList.setHaveMoreItem(true);
                        }
                        notificationListAdapter.notifyDataSetChanged();
                        offset = offset + 1;
                    } else {
                        endlessRecyclerViewNottificationList.setHaveMoreItem(false);
                    }

                } else {
                    finish();
                    endlessRecyclerViewNottificationList.setHaveMoreItem(false);
                }
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        showSnakBar(relativeLayoutRoot,meta.getString("message"));

                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<NottificationListResponse> call, Throwable t) {
                // Log error here since request failed
                hideProgressDialog();
            }
        });
    }
    // while the user click on the nottification list ,update the status as read
    private void ReadNottification(int notification_id) {


        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<Meta> call = apiService.ReadNottification(GloablMethods.API_HEADER + acess_token, notification_id, notification_id);
        call.enqueue(new Callback<Meta>() {
            @Override
            public void onResponse(Call<Meta> call, retrofit2.Response<Meta> response) {


            }

            @Override
            public void onFailure(Call<Meta> call, Throwable t) {

            }

        });
    }
    private void updateEndlessRecyclerView() {
        notificationListAdapter = new NotificationListAdapter(nottificationList, R.layout.item_nottification_list, getApplicationContext());
        endlessRecyclerViewNottificationList.setAdapter(notificationListAdapter);
        // progressDialog.dismiss();
    }
}

