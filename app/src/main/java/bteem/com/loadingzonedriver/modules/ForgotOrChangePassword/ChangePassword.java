package bteem.com.loadingzonedriver.modules.ForgotOrChangePassword;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import org.json.JSONObject;

import bteem.com.loadingzonedriver.R;
import bteem.com.loadingzonedriver.global.AppController;
import bteem.com.loadingzonedriver.global.BaseActivity;
import bteem.com.loadingzonedriver.global.GloablMethods;
import bteem.com.loadingzonedriver.global.MessageConstants;
import bteem.com.loadingzonedriver.global.SessionManager;
import bteem.com.loadingzonedriver.modules.login.LoginActivity;
import bteem.com.loadingzonedriver.retrofit.ApiClient;
import bteem.com.loadingzonedriver.retrofit.ApiInterface;
import bteem.com.loadingzonedriver.retrofit.model.ChangePasswordResponse;
import retrofit2.Call;
import retrofit2.Callback;

public class ChangePassword extends BaseActivity implements View.OnClickListener {

    private EditText editTextOldPassword, editTextNewPassword, editTextConfirmPassword;
    private Button changePassword;
    String user_type_id = null;
    ProgressDialog progressDialog;
    LinearLayout linearLayout;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        editTextOldPassword = (EditText) findViewById(R.id.editTextOldPassword);
        editTextNewPassword = (EditText) findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        changePassword = (Button) findViewById(R.id.buttonChangePassword);
        linearLayout = (LinearLayout) findViewById(R.id.id_linearChange);
        progressDialog = new ProgressDialog(ChangePassword.this);

        sessionManager = new SessionManager(getApplicationContext());
        // click on listner
        changePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonChangePassword:
                String oldpassword = editTextOldPassword.getText().toString().trim();
                String Newpassword = editTextNewPassword.getText().toString().trim();
                String ConPassword = editTextConfirmPassword.getText().toString().trim();
                // for differeating the service provider

                if (validate(oldpassword, Newpassword, ConPassword)) {

                    if (Newpassword.equals(ConPassword)) {
                        //signup api call


                        Boolean a = isConnectingToInternet(ChangePassword.this);
                        if (a) {
                            change_password(oldpassword, Newpassword, ConPassword);
                        } else {
                            Snackbar snackbar = Snackbar
                                    .make(linearLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                                    .setAction("RETRY", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(getApplicationContext(), ChangePassword.class);
                                            startActivity(i);
                                        }
                                    });

                            View sbView = snackbar.getView();

                            //sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.complete_job_back_color));
                            sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.samkara_submit_button));
// Changing message text color
                            snackbar.setActionTextColor(Color.WHITE);
// Changing action button text color

                            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.WHITE);
                            snackbar.show();
                        }


                    } else {
                        showBaseToast(MessageConstants.PASSWORD_MISMATCH);
                    }

                } else {
                    showBaseToast(MessageConstants.PROVIDE_BASIC_INFO);
                }
                break;
        }

    }


    private boolean validate(String... strings) {
        for (String string : strings) {
            if (string == null) return false;
            if (string.length() < 1) return false;
        }
        return true;
    }

    private void change_password(String oldPassword, String newPassword, String conPassword) {


        progressDialog.setMessage("loading");
        progressDialog.show();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<ChangePasswordResponse> call = apiService.ChangePassword(GloablMethods.API_HEADER + acess_token, oldPassword, newPassword, conPassword);
        call.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, retrofit2.Response<ChangePasswordResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    finish();
                }

                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Toast.makeText(getApplicationContext(), meta.getString("message"), Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Log.d("exception",e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
            }

        });
    }
}
