package bteem.com.loadingzonedriver.modules.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import bteem.com.loadingzonedriver.R;
import bteem.com.loadingzonedriver.app.Config;
import bteem.com.loadingzonedriver.global.AppController;
import bteem.com.loadingzonedriver.global.BaseActivity;
import bteem.com.loadingzonedriver.global.GloablMethods;
import bteem.com.loadingzonedriver.global.MessageConstants;
import bteem.com.loadingzonedriver.global.SessionManager;
import bteem.com.loadingzonedriver.modules.ForgotOrChangePassword.ForgotPassword;
import bteem.com.loadingzonedriver.modules.home.HomeActivity;
import bteem.com.loadingzonedriver.retrofit.ApiClient;
import bteem.com.loadingzonedriver.retrofit.ApiInterface;
import bteem.com.loadingzonedriver.retrofit.model.LoginResponse;
import bteem.com.loadingzonedriver.utils.NotificationUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.editUserName)
    EditText editUserName;
    @BindView(R.id.buttonLogin)
    Button buttonLogin;
    @BindView(R.id.linearEye)
    LinearLayout linearLayoutEye;
    @NonNull
    @BindView(R.id.rootView)
    RelativeLayout rootView;
    @BindView(R.id.textViewForgotPassword)
    TextView textViewForgotPassword;
    private SessionManager session;
    private ApiInterface apiService;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    String regId;
    Boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //buuterknife for injecting the views
        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        //If the session is logged in move to Home
        session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {

            Intent intent1 = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent1);
            finish();
        } else {
           // Log.d("login Isuue", "sessionfalse");
        }


        ///firebase
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                }
            }
        };
        //manage password visibility
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                linearLayoutEye.setVisibility(View.VISIBLE);
                if (count == 0) {
                    linearLayoutEye.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        displayFirebaseRegId();
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @OnClick(R.id.textViewForgotPassword)
    public void ForgotPassword() {
        Intent i = new Intent(LoginActivity.this, ForgotPassword.class);
        startActivity(i);
    }

    @OnClick(R.id.buttonLogin)
    public void sigin() {
        String username = editUserName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String usertype = "3";//for identify the provider

        if (GloablMethods.validate(username, password)) {
//            if (isValidEmaillId(username)) {
            if (isConnectingToInternet(getApplicationContext()))
                Signin(username, password, usertype);
            else {
                showSnakBar(rootView, MessageConstants.INTERNET);
            }

        } else {
            showSnakBar(rootView, MessageConstants.PROVIDE_BASIC_INFO);
        }
    }
    //api call for singin
    public void Signin(String username, String password, String usertype) {

        showProgressDialog(LoginActivity.this, "Loading");
        Call<LoginResponse> call = apiService.Signin(username, password, usertype, regId);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMeta().getStatus().equals(true) || response.body().getMeta().getStatus().equals("true")) {

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        session.setLogin(true);
                        AppController.setString(getApplicationContext(), "customer_email", response.body().getData().getUsername());
                        AppController.setString(getApplicationContext(), "customer_name", response.body().getData().getName());
                        AppController.setString(getApplicationContext(), "acess_token", response.body().getData().getAccessToken());
                        AppController.setString(getApplicationContext(), "user_id", String.valueOf(response.body().getData().getUserId()));
                        AppController.setString(getApplicationContext(), "pic", response.body().getData().getProfilePic());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    } else {
                        Snackbar snackbar = Snackbar
                                .make(rootView, response.body().getMeta().getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();

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
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
              //  Log.d("exception>>>", t.getMessage());

            }
        });

    }

    @OnClick(R.id.linearEye)
    public void PasswordVisibility() {

        if (isPasswordVisible.equals(false)) {
            editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            isPasswordVisible = true;
        } else if (isPasswordVisible.equals(true)) {
            editTextPassword.setInputType(129);
            isPasswordVisible = false;
        }
    }
}
