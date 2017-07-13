
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
    @SerializedName("job_starting_date")
    @Expose
    private Object jobStartingDate;
    @SerializedName("expected_end_date")
    @Expose
    private String expectedEndDate;
    @SerializedName("job_ended_date")
    @Expose
    private Object jobEndedDate;
    @SerializedName("vehicle_details")
    @Expose
    private VehicleDetails vehicleDetails;

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

    public Object getJobStartingDate() {
        return jobStartingDate;
    }

    public void setJobStartingDate(Object jobStartingDate) {
        this.jobStartingDate = jobStartingDate;
    }

    public String getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(String expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public Object getJobEndedDate() {
        return jobEndedDate;
    }

    public void setJobEndedDate(Object jobEndedDate) {
        this.jobEndedDate = jobEndedDate;
    }

    public VehicleDetails getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(VehicleDetails vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

}
