package bteem.com.loadingzonedriver.modules.login;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONObject;

import bteem.com.loadingzonedriver.R;
import bteem.com.loadingzonedriver.global.AppController;
import bteem.com.loadingzonedriver.global.BaseActivity;
import bteem.com.loadingzonedriver.global.GloablMethods;
import bteem.com.loadingzonedriver.global.MessageConstants;
import bteem.com.loadingzonedriver.global.SessionManager;
import bteem.com.loadingzonedriver.modules.home.HomeActivity;
import bteem.com.loadingzonedriver.retrofit.ApiClient;
import bteem.com.loadingzonedriver.retrofit.ApiInterface;
import bteem.com.loadingzonedriver.retrofit.model.LoginResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

import static bteem.com.loadingzonedriver.global.BaseActivity.isConnectingToInternet;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.editUserName)
    EditText editUserName;
    @BindView(R.id.buttonLogin)
    Button buttonLogin;
    @BindView(R.id.rootView)
    ConstraintLayout rootView;
    private SessionManager session;
    private ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_02);
        //buuterknife for injecting the views
        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        //If the session is logged in move to Home
        session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {
            Log.d("login Isuue", "sessionfalse");
            Intent intent1 = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent1);
            finish();
        } else {
            Log.d("login Isuue", "sessionfalse");
        }

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
//            }
//            } else {
//                showSnakBar(rootView, MessageConstants.INVALID_EMAIL);
//            }
        } else {
            showSnakBar(rootView, MessageConstants.PROVIDE_BASIC_INFO);
        }
    }

    //api call for singin
    public void Signin(String username, String password, String usertype) {

        showProgressDialog(LoginActivity.this, "Loading");
        Call<LoginResponse> call = apiService.Signin(username, password, usertype);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getMeta().getStatus().equals(true)) {

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
                Log.d("exception>>>", t.getMessage());

            }
        });

    }
}
