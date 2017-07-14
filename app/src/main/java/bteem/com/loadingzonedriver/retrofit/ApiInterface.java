package bteem.com.loadingzonedriver.retrofit;

import bteem.com.loadingzonedriver.global.GloablMethods;
import bteem.com.loadingzonedriver.retrofit.model.DriverProfileRsponse;
import bteem.com.loadingzonedriver.retrofit.model.DriverStatusUpdateRsponse;
import bteem.com.loadingzonedriver.retrofit.model.LoginResponse;
import bteem.com.loadingzonedriver.retrofit.model.Meta;
import bteem.com.loadingzonedriver.retrofit.model.PostedJobResponse;
import bteem.com.loadingzonedriver.retrofit.model.TruckUpdateStatusresponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by admin on 5/15/2017.
 */

public interface ApiInterface {
    @FormUrlEncoded
    @POST("main/login")
    Call<LoginResponse> Signin(@Field("username") String username, @Field("password") String password, @Field("user_type_id") String user_type);

    @GET("driver/jobs")
    Call<PostedJobResponse> PostedJobList(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Query("page") int page);

    @GET("vehicle/running-status")
    Call<DriverStatusUpdateRsponse> TruckRunningStatus(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Query("vehicle_id") String vehicle_id);

    @GET("driver")
    Call<DriverProfileRsponse> DriverProfile(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token);
    @Multipart
    @POST("driver/avatar")
    Call<DriverProfileRsponse> UploadprofilePic(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Part MultipartBody.Part file);
    @FormUrlEncoded
    @PUT("driver/{driver_id}")
    Call<DriverProfileRsponse>UpdateDriver(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token,@Path("driver_id") String driver_id,@Field("driver_name") String driver_name, @Field("driver_phone") String driver_phone, @Field("driver_email") String driver_email, @Field("driver_address") String driver_address);
    @FormUrlEncoded
    @POST("job/start-job")
    Call<TruckUpdateStatusresponse> StartToCustomerLocation(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token,@Field("job_id") String job_id);
    @FormUrlEncoded
    @POST("job/load-goods")
    Call<TruckUpdateStatusresponse> LoadingMaterial(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token,@Field("job_id") String job_id);
    @FormUrlEncoded
    @POST("job/load-on-the-way")
    Call<TruckUpdateStatusresponse> OnthewayToDestination(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token,@Field("job_id") String job_id);
    @FormUrlEncoded
    @POST("job/complete-job")
    Call<TruckUpdateStatusresponse> TruckUnLoad(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token,@Field("job_id") String job_id);
    @FormUrlEncoded
    @POST("job/returning-back")
    Call<TruckUpdateStatusresponse> BackToDestination(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token,@Field("job_id") String job_id);
    @FormUrlEncoded
    @POST("job/reached-back")
    Call<TruckUpdateStatusresponse> ReachedOrigion(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token,@Field("job_id") String job_id);


    @FormUrlEncoded
    @POST("main/logout")
    Call<Meta> Logout(@Header(GloablMethods.HEADER_AUTHORIZATION) String acces_token, @Field("device_token") String device_token);

}
