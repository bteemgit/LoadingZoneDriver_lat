package bteem.com.loadingzonedriver.modules.profile;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;

import bteem.com.loadingzonedriver.R;
import bteem.com.loadingzonedriver.global.AppController;
import bteem.com.loadingzonedriver.global.BaseActivity;
import bteem.com.loadingzonedriver.global.GloablMethods;
import bteem.com.loadingzonedriver.global.MessageConstants;
import bteem.com.loadingzonedriver.permision.PermissionsActivity;
import bteem.com.loadingzonedriver.permision.PermissionsChecker;
import bteem.com.loadingzonedriver.retrofit.ApiClient;
import bteem.com.loadingzonedriver.retrofit.ApiInterface;
import bteem.com.loadingzonedriver.retrofit.model.DriverProfileRsponse;
import bteem.com.loadingzonedriver.view.CircleTransformation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverProfileAcivity extends BaseActivity {
    @BindView(R.id.ivDriverProfilePhoto)
    ImageView imageViewDriverProfileImage;
    @NonNull
    @BindView(R.id.btnEditProfilePic)
    Button btnEditProfilePic;
    @NonNull
    @BindView(R.id.btnEditProfilePicUpload)
    Button btnEditProfilePicUpload;
    @NonNull
    @BindView(R.id.btnEditOtherData)
    Button btnEditOtherData;

    @NonNull
    @BindView(R.id.fabEdit)
    FloatingActionButton fabEdit;


    @BindView(R.id.fabDriverAddDetails)
    FloatingActionButton fabDriverAdd;
    @NonNull
    @BindView(R.id.editDriverEmail)
    EditText editTextDriverEmail;
    @NonNull
    @BindView(R.id.editDriverName)
    EditText editTextDriverName;
    @NonNull
    @BindView(R.id.editDriverLocation)
    EditText editTextDriverAdress;
    @NonNull
    @BindView(R.id.editDriverMobile)
    EditText editTextDriverMobile;
    @NonNull
    @BindView(R.id.rootView)
    RelativeLayout rootView;
    private ApiInterface apiService;
    private int avatarSize;
    String imagePath, driver_id;
    PermissionsChecker checker;
    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile_activity_02);
        this.avatarSize = getResources().getDimensionPixelSize(R.dimen.user_profile_avatar_size);
        ButterKnife.bind(this);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Profile");
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        checker = new PermissionsChecker(this);
        getDriverProfile();
    }


    @OnClick(R.id.fabEdit)
    public void fabEditClick(){
        fabDriverAdd.setVisibility(View.VISIBLE);
        editTextDriverEmail.setFocusableInTouchMode(true);
        editTextDriverEmail.requestFocus();
        editTextDriverAdress.setFocusableInTouchMode(true);
        editTextDriverAdress.requestFocus();
        editTextDriverMobile.setFocusableInTouchMode(true);
        editTextDriverMobile.requestFocus();
        editTextDriverName.setFocusableInTouchMode(true);
        editTextDriverName.requestFocus();
        btnEditOtherData.setVisibility(View.GONE);

    }

    @OnClick(R.id.btnEditProfilePic)
    public void changeProfilePic() {
        View v = new View(getApplicationContext());
        showImagePopup(v);
    }

    @OnClick(R.id.btnEditProfilePicUpload)
    public void uploadImageToServer() {
        if (isConnectingToInternet(DriverProfileAcivity.this)) {
            uploadImage();
        } else {
            showSnakBar(rootView, MessageConstants.INTERNET);
        }
    }

    @OnClick(R.id.btnEditOtherData)
    public void editProfiledata() {
        fabDriverAdd.setVisibility(View.VISIBLE);
        editTextDriverEmail.setFocusableInTouchMode(true);
        editTextDriverEmail.requestFocus();
        editTextDriverAdress.setFocusableInTouchMode(true);
        editTextDriverAdress.requestFocus();
        editTextDriverMobile.setFocusableInTouchMode(true);
        editTextDriverMobile.requestFocus();
        editTextDriverName.setFocusableInTouchMode(true);
        editTextDriverName.requestFocus();
        btnEditOtherData.setVisibility(View.GONE);
    }

    // Update the Driver
    @NonNull
    @OnClick(R.id.fabDriverAddDetails)
    public void addTruck() {
        String driverEmail = editTextDriverEmail.getText().toString().trim();
        String driverName = editTextDriverName.getText().toString().trim();
        String driverAdress = editTextDriverAdress.getText().toString().trim();
        String driverMobile = editTextDriverMobile.getText().toString().trim();
        if (isConnectingToInternet(DriverProfileAcivity.this)) {
            UpdateDriver(driver_id, driverName, driverMobile, driverEmail, driverAdress);
        } else {
            showSnakBar(rootView, MessageConstants.INTERNET);
        }

    }

    //:.....................profile pic upload

    /**
     * Showing Image Picker
     */
    public void showImagePopup(View view) {
        if (checker.lacksPermissions(PERMISSIONS_READ_STORAGE)) {
            startPermissionsActivity(PERMISSIONS_READ_STORAGE);
        } else {
            // File System.
            final Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_PICK);

            // Chooser of file system options.
            final Intent chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.string_choose_image));
            startActivityForResult(chooserIntent, 1010);
        }
    }

    /***
     * OnResult of Image Picked
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1010) {
            if (data == null) {
                //  buttonImageuplaod.setVisibility(View.GONE);
                Snackbar.make(rootView, R.string.string_unable_to_pick_image, Snackbar.LENGTH_INDEFINITE).show();
                return;
            }
            Uri selectedImageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);
                btnEditProfilePicUpload.setVisibility(View.VISIBLE);
                btnEditProfilePic.setVisibility(View.GONE);
//                Picasso.with(getApplicationContext()).load(new File(imagePath))
//                        .into(imageViewProfile);
                Picasso.with(DriverProfileAcivity.this)
                        .load(new File(imagePath))
                        .resize(avatarSize, avatarSize)
                        .centerCrop()
                        .transform(new CircleTransformation())
                        .into(imageViewDriverProfileImage);

                Snackbar.make(rootView, R.string.string_reselect, Snackbar.LENGTH_LONG).show();
                cursor.close();
            } else {
                Snackbar.make(rootView, R.string.string_unable_to_load_image, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void startPermissionsActivity(String[] permission) {
        PermissionsActivity.startActivityForResult(this, 0, permission);
    }
    //:-----------Driver Update Api-------------:\\

    private void UpdateDriver(String driver_id, String driver_name, String driver_phone, String driver_email, String driver_address) {
        showProgressDialog(DriverProfileAcivity.this, "Updatimg Driver,please wait...");
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<DriverProfileRsponse> call = apiService.UpdateDriver(GloablMethods.API_HEADER + acess_token, driver_id, driver_name, driver_phone, driver_email, driver_address);
        call.enqueue(new Callback<DriverProfileRsponse>() {
            @Override
            public void onResponse(Call<DriverProfileRsponse> call, Response<DriverProfileRsponse> response) {
                hideProgressDialog();
                if (response.isSuccessful())

                {
                    btnEditOtherData.setVisibility(View.VISIBLE);
                    editTextDriverEmail.setText(response.body().getDriverEmail());
                    editTextDriverName.setText(response.body().getDriverName());
                    editTextDriverAdress.setText(response.body().getDriverAddress());
                    editTextDriverMobile.setText(response.body().getDriverPhone());
                    editTextDriverEmail.setText(response.body().getDriverEmail());
                    editTextDriverEmail.setFocusable(false);
                    editTextDriverAdress.setFocusable(false);
                    editTextDriverMobile.setFocusable(false);
                    editTextDriverName.setFocusable(false);
                    fabDriverAdd.setVisibility(View.GONE);
                    showSnakBar(rootView, "Driver Added Successfully");
                }
                if (!response.isSuccessful()) {
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
            public void onFailure(Call<DriverProfileRsponse> call, Throwable t) {
                // Log error here since request failed
                hideProgressDialog();

            }
        });
    }

    private void uploadImage() {
        showProgressDialog(DriverProfileAcivity.this, "Uploading The Image...");
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //File creating from selected URL
        File file = new File(imagePath);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("profile_pic", file.getName(), requestFile);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<DriverProfileRsponse> resultCall = apiService.UploadprofilePic(GloablMethods.API_HEADER + acess_token, body);
        // finally, execute the request
        resultCall.enqueue(new Callback<DriverProfileRsponse>() {
            @Override
            public void onResponse(Call<DriverProfileRsponse> call, Response<DriverProfileRsponse> response) {
                hideProgressDialog();
                // Response Success or Fail
                if (response.isSuccessful()) {
                    AppController.setString(getApplicationContext(), "pic", response.body().getDriverPic());

                    btnEditProfilePicUpload.setVisibility(View.GONE);
                    btnEditProfilePic.setVisibility(View.VISIBLE);
                    Picasso.with(DriverProfileAcivity.this)
                            .load(new File(response.body().getDriverPic()))
                            .resize(80, 80)
                            .centerCrop()
                            .transform(new CircleTransformation())
                            .into(imageViewDriverProfileImage);
                    Snackbar.make(rootView, R.string.string_upload_success, Snackbar.LENGTH_LONG).show();


                } else {
                    Snackbar.make(rootView, R.string.string_upload_fail, Snackbar.LENGTH_LONG).show();
                }

                /**
                 * Update Views
                 */
                imagePath = "";

            }

            @Override
            public void onFailure(Call<DriverProfileRsponse> call, Throwable t) {
                hideProgressDialog();
            }
        });
    }

    // Getting driver profile
    public void getDriverProfile
    () {
        showProgressDialog(getApplicationContext(), "loading");
        apiService = ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<DriverProfileRsponse> call = apiService.DriverProfile(GloablMethods.API_HEADER + acess_token);
        call.enqueue(new Callback<DriverProfileRsponse>() {
            @Override
            public void onResponse(Call<DriverProfileRsponse> call, retrofit2.Response<DriverProfileRsponse> response) {

                hideProgressDialog();
                if (response.isSuccessful()) {
                    driver_id = String.valueOf(response.body().getDriverId());
                    editTextDriverEmail.setText(response.body().getDriverEmail());
                    editTextDriverName.setText(response.body().getDriverName());
                    editTextDriverAdress.setText(response.body().getDriverAddress());
                    editTextDriverMobile.setText(response.body().getDriverPhone());
                    editTextDriverEmail.setText(response.body().getDriverEmail());
                    editTextDriverEmail.setFocusable(false);
                    editTextDriverAdress.setFocusable(false);
                    editTextDriverMobile.setFocusable(false);
                    editTextDriverName.setFocusable(false);


                    Picasso.with(DriverProfileAcivity.this)
                            .load(new File(response.body().getDriverPic()))
                            .resize(80, 80)
                            .centerCrop()
                            .transform(new CircleTransformation())
                            .into(imageViewDriverProfileImage);


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
            public void onFailure(Call<DriverProfileRsponse> call, Throwable t) {
                // Log error here since request failed
                hideProgressDialog();
            }
        });
    }

}
