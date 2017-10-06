
package bteem.com.loadingzonedriver.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverStatusUpdateRsponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("vehicle_status_id")
    @Expose
    private Integer vehicleStatusId;
    @SerializedName("vehicle_id")
    @Expose
    private Integer vehicleId;
    @SerializedName("running_status")
    @Expose
    private RunningStatus runningStatus;
    @SerializedName("location_latitude")
    @Expose
    private String locationLatitude;
    @SerializedName("location_longitude")
    @Expose
    private String locationLongitude;
    @SerializedName("location_name")
    @Expose
    private String locationName;
    @SerializedName("date_time")
    @Expose
    private String dateTime;




    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Integer getVehicleStatusId() {
        return vehicleStatusId;
    }

    public void setVehicleStatusId(Integer vehicleStatusId) {
        this.vehicleStatusId = vehicleStatusId;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public RunningStatus getRunningStatus() {
        return runningStatus;
    }

    public void setRunningStatus(RunningStatus runningStatus) {
        this.runningStatus = runningStatus;
    }

    public String getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(String locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public String getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(String locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
