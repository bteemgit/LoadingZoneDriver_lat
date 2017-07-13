
package bteem.com.loadingzonedriver.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MaterialWeight {

    @SerializedName("material_weight_id")
    @Expose
    private Integer materialWeightId;
    @SerializedName("material_weight_mt")
    @Expose
    private Integer materialWeightMt;
    @SerializedName("material_weight_text")
    @Expose
    private String materialWeightText;

    public Integer getMaterialWeightId() {
        return materialWeightId;
    }

    public void setMaterialWeightId(Integer materialWeightId) {
        this.materialWeightId = materialWeightId;
    }

    public Integer getMaterialWeightMt() {
        return materialWeightMt;
    }

    public void setMaterialWeightMt(Integer materialWeightMt) {
        this.materialWeightMt = materialWeightMt;
    }

    public String getMaterialWeightText() {
        return materialWeightText;
    }

    public void setMaterialWeightText(String materialWeightText) {
        this.materialWeightText = materialWeightText;
    }

}
