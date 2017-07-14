package bteem.com.loadingzonedriver.modules;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bteem.com.loadingzonedriver.R;
import bteem.com.loadingzonedriver.global.AppController;
import bteem.com.loadingzonedriver.global.GloablMethods;
import bteem.com.loadingzonedriver.global.MessageConstants;
import bteem.com.loadingzonedriver.modules.home.*;
import bteem.com.loadingzonedriver.recyclerview.EndlessRecyclerView;
import bteem.com.loadingzonedriver.retrofit.ApiClient;
import bteem.com.loadingzonedriver.retrofit.ApiInterface;
import bteem.com.loadingzonedriver.retrofit.model.JobList;
import bteem.com.loadingzonedriver.retrofit.model.PostedJobResponse;
import bteem.com.loadingzonedriver.global.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;

import static bteem.com.loadingzonedriver.global.BaseActivity.isConnectingToInternet;

public class completedjob extends AppCompatActivity {



    private bteem.com.loadingzonedriver.modules.home.PostedJobListAdapter postedJobListAdapter;
    private List<JobList> jobList = new ArrayList<>();
    private int limit = 30;
    private int offset = 1;
    private boolean hasReachedTop = false;
    private EndlessRecyclerView.PaginationListener paginationListener = new EndlessRecyclerView.PaginationListener() {
        @Override
        public void onReachedBottom() {
            if (isConnectingToInternet(getApplicationContext()))
            {
               // getJobPosted();
            }
            else {
               // showSnakBar(, MessageConstants.INTERNET);
                Toast.makeText(completedjob.this, "reached bottom...", Toast.LENGTH_SHORT).show();
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

    }





    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
