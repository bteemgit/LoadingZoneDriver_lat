
package bteem.com.loadingzonedriver.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TruckSize {

    @SerializedName("truck_size_id")
    @Expose
    private Integer truckSizeId;
    @SerializedName("truck_size_dimension")
    @Expose
    private String truckSizeDimension;

    public Integer getTruckSizeId() {
        return truckSizeId;
    }

    public void setTruckSizeId(Integer truckSizeId) {
        this.truckSizeId = truckSizeId;
    }

    public String getTruckSizeDimension() {
        return truckSizeDimension;
    }

    public void setTruckSizeDimension(String truckSizeDimension) {
        this.truckSizeDimension = truckSizeDimension;
    }

}
