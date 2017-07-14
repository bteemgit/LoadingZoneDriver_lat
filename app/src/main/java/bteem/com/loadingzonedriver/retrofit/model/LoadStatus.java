
package bteem.com.loadingzonedriver.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoadStatus {

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
    private Object locationLatitude;
    @SerializedName("location_longitude")
    @Expose
    private Object locationLongitude;
    @SerializedName("location_name")
    @Expose
    private Object locationName;
    @SerializedName("date_time")
    @Expose
    private String dateTime;

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

    public Object getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(Object locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public Object getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(Object locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public Object getLocationName() {
        return locationName;
    }

    public void setLocationName(Object locationName) {
        this.locationName = locationName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
