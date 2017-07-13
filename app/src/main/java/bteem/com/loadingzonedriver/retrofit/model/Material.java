
package bteem.com.loadingzonedriver.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Material {

    @SerializedName("material_id")
    @Expose
    private Integer materialId;
    @SerializedName("material_name")
    @Expose
    private String materialName;
    @SerializedName("material_pic")
    @Expose
    private Object materialPic;

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Object getMaterialPic() {
        return materialPic;
    }

    public void setMaterialPic(Object materialPic) {
        this.materialPic = materialPic;
    }

}
