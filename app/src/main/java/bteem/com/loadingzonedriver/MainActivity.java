package bteem.com.loadingzonedriver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import bteem.com.loadingzonedriver.modules.home.HomeActivity;
import bteem.com.loadingzonedriver.modules.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = this.getIntent();
        if (intent != null && intent.getExtras() != null && intent.getExtras().containsKey("name") && (intent.getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) == 0) {
            String name = this.getIntent().getExtras().getString("name");
            String jobID = this.getIntent().getExtras().getString("job_id");
                Intent intent1 = new Intent(MainActivity.this, HomeActivity.class);
                intent1.putExtra("job_id", jobID);
                startActivity(intent1);
            finish();
        }
        else {

            Thread background = new Thread() {
                public void run() {

                    try {
                        // Thread will sleep for 5 seconds
                        sleep(5 * 1000);

                        // After 5 seconds redirect to another intent
                        // After 5 seconds redirect to another intent

                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                        //Remove activity
                        finish();

                    } catch (Exception e) {

                    }
                }
            };

            // start thread
            background.start();
        }




    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}
