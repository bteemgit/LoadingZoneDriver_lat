
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
    @SerializedName("blocking_status")
    @Expose
    private String blockingStatus;
    @SerializedName("location_latitude")
    @Expose
    private Object locationLatitude;
    @SerializedName("location_longitude")
    @Expose
    private Object locationLongitude;
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

    public String getBlockingStatus() {
        return blockingStatus;
    }

    public void setBlockingStatus(String blockingStatus) {
        this.blockingStatus = blockingStatus;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
