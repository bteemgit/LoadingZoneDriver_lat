
package bteem.com.loadingzonedriver.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobList {

    @SerializedName("job_id")
    @Expose
    private Integer jobId;
    @SerializedName("job_title")
    @Expose
    private String jobTitle;
    @SerializedName("from_location")
    @Expose
    private FromLocation fromLocation;
    @SerializedName("to_location")
    @Expose
    private ToLocation toLocation;
    @SerializedName("material")
    @Expose
    private Material material;
    @SerializedName("material_description")
    @Expose
    private String materialDescription;
    @SerializedName("material_weight")
    @Expose
    private MaterialWeight materialWeight;
    @SerializedName("preferred_loading_date")
    @Expose
    private String preferredLoadingDate;
    @SerializedName("preferred_loading_time")
    @Expose
    private String preferredLoadingTime;
    @SerializedName("loading_date")
    @Expose
    private String loadingDate;
    @SerializedName("loading_time")
    @Expose
    private String loadingTime;
    @SerializedName("date_updated")
    @Expose
    private String dateUpdated;
    @SerializedName("payment_type")
    @Expose
    private PaymentType paymentType;
    @SerializedName("truck_type")
    @Expose
    private TruckType truckType;
    @SerializedName("truck_size")
    @Expose
    private TruckSize truckSize;
    @SerializedName("location_distance")
    @Expose
    private Integer locationDistance;
    @SerializedName("date_requested")
    @Expose
    private String dateRequested;
    @SerializedName("date_requested_relative")
    @Expose
    private String dateRequestedRelative;
    @SerializedName("quotation_count")
    @Expose
    private String quotationCount;
    @SerializedName("has_active_quotations")
    @Expose
    private Object hasActiveQuotations;
    @SerializedName("job_status")
    @Expose
    private JobStatus jobStatus;
    @SerializedName("assigned_vehicle")
    @Expose
    private AssignedVehicle assignedVehicle;
    @SerializedName("load_status")
    @Expose
    private LoadStatus loadStatus;
    @SerializedName("service_provider")
    @Expose
    private ServiceProvider serviceProvider;
    @SerializedName("customer")
    @Expose
    private Customer customer;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public FromLocation getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(FromLocation fromLocation) {
        this.fromLocation = fromLocation;
    }

    public ToLocation getToLocation() {
        return toLocation;
    }

    public void setToLocation(ToLocation toLocation) {
        this.toLocation = toLocation;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public MaterialWeight getMaterialWeight() {
        return materialWeight;
    }

    public void setMaterialWeight(MaterialWeight materialWeight) {
        this.materialWeight = materialWeight;
    }

    public String getPreferredLoadingDate() {
        return preferredLoadingDate;
    }

    public void setPreferredLoadingDate(String preferredLoadingDate) {
        this.preferredLoadingDate = preferredLoadingDate;
    }

    public String getPreferredLoadingTime() {
        return preferredLoadingTime;
    }

    public void setPreferredLoadingTime(String preferredLoadingTime) {
        this.preferredLoadingTime = preferredLoadingTime;
    }

    public String getLoadingDate() {
        return loadingDate;
    }

    public void setLoadingDate(String loadingDate) {
        this.loadingDate = loadingDate;
    }

    public String getLoadingTime() {
        return loadingTime;
    }

    public void setLoadingTime(String loadingTime) {
        this.loadingTime = loadingTime;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public TruckType getTruckType() {
        return truckType;
    }

    public void setTruckType(TruckType truckType) {
        this.truckType = truckType;
    }

    public TruckSize getTruckSize() {
        return truckSize;
    }

    public void setTruckSize(TruckSize truckSize) {
        this.truckSize = truckSize;
    }

    public Integer getLocationDistance() {
        return locationDistance;
    }

    public void setLocationDistance(Integer locationDistance) {
        this.locationDistance = locationDistance;
    }

    public String getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(String dateRequested) {
        this.dateRequested = dateRequested;
    }

    public String getDateRequestedRelative() {
        return dateRequestedRelative;
    }

    public void setDateRequestedRelative(String dateRequestedRelative) {
        this.dateRequestedRelative = dateRequestedRelative;
    }

    public String getQuotationCount() {
        return quotationCount;
    }

    public void setQuotationCount(String quotationCount) {
        this.quotationCount = quotationCount;
    }

    public Object getHasActiveQuotations() {
        return hasActiveQuotations;
    }

    public void setHasActiveQuotations(Object hasActiveQuotations) {
        this.hasActiveQuotations = hasActiveQuotations;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public AssignedVehicle getAssignedVehicle() {
        return assignedVehicle;
    }

    public void setAssignedVehicle(AssignedVehicle assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }

    public LoadStatus getLoadStatus() {
        return loadStatus;
    }

    public void setLoadStatus(LoadStatus loadStatus) {
        this.loadStatus = loadStatus;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
