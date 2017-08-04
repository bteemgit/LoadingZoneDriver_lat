
package bteem.com.loadingzonedriver.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignedVehicle {

    @SerializedName("vehicle_block_id")
    @Expose
    private Integer vehicleBlockId;
    @SerializedName("job_driver_id")
    @Expose
    private Integer jobDriverId;
    @SerializedName("job_id")
    @Expose
    private Integer jobId;
    @SerializedName("expected_start_date")
    @Expose
    private String expectedStartDate;
    @SerializedName("expected_start_time")
    @Expose
    private String expectedStartTime;
    @SerializedName("job_starting_date")
    @Expose
    private String jobStartingDate;
    @SerializedName("job_starting_time")
    @Expose
    private String jobStartingTime;
    @SerializedName("expected_end_date")
    @Expose
    private String expectedEndDate;
    @SerializedName("expected_end_time")
    @Expose
    private String expectedEndTime;
    @SerializedName("job_ended_date")
    @Expose
    private String jobEndedDate;
    @SerializedName("job_ended_time")
    @Expose
    private String jobEndedTime;
    @SerializedName("vehicle_details")
    @Expose
    private VehicleDetails vehicleDetails;
    @SerializedName("active_status")
    @Expose
    private String activeStatus;

    public Integer getVehicleBlockId() {
        return vehicleBlockId;
    }

    public void setVehicleBlockId(Integer vehicleBlockId) {
        this.vehicleBlockId = vehicleBlockId;
    }

    public Integer getJobDriverId() {
        return jobDriverId;
    }

    public void setJobDriverId(Integer jobDriverId) {
        this.jobDriverId = jobDriverId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getExpectedStartDate() {
        return expectedStartDate;
    }

    public void setExpectedStartDate(String expectedStartDate) {
        this.expectedStartDate = expectedStartDate;
    }

    public String getExpectedStartTime() {
        return expectedStartTime;
    }

    public void setExpectedStartTime(String expectedStartTime) {
        this.expectedStartTime = expectedStartTime;
    }

    public String getJobStartingDate() {
        return jobStartingDate;
    }

    public void setJobStartingDate(String jobStartingDate) {
        this.jobStartingDate = jobStartingDate;
    }

    public String getJobStartingTime() {
        return jobStartingTime;
    }

    public void setJobStartingTime(String jobStartingTime) {
        this.jobStartingTime = jobStartingTime;
    }

    public String getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(String expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public String getExpectedEndTime() {
        return expectedEndTime;
    }

    public void setExpectedEndTime(String expectedEndTime) {
        this.expectedEndTime = expectedEndTime;
    }

    public String getJobEndedDate() {
        return jobEndedDate;
    }

    public void setJobEndedDate(String jobEndedDate) {
        this.jobEndedDate = jobEndedDate;
    }

    public String getJobEndedTime() {
        return jobEndedTime;
    }

    public void setJobEndedTime(String jobEndedTime) {
        this.jobEndedTime = jobEndedTime;
    }

    public VehicleDetails getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(VehicleDetails vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

}
