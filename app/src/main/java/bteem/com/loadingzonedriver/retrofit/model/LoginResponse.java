
package bteem.com.loadingzonedriver.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("fcm_update")
    @Expose
    private Boolean fcmUpdate;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Boolean getFcmUpdate() {
        return fcmUpdate;
    }

    public void setFcmUpdate(Boolean fcmUpdate) {
        this.fcmUpdate = fcmUpdate;
    }

}
