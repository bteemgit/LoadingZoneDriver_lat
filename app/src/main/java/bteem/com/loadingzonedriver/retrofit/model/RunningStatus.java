
package bteem.com.loadingzonedriver.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RunningStatus {

    @SerializedName("running_status_id")
    @Expose
    private Integer runningStatusId;
    @SerializedName("running_status_name")
    @Expose
    private String runningStatusName;
    @SerializedName("running_status_text")
    @Expose
    private String runningStatusText;

    public Integer getRunningStatusId() {
        return runningStatusId;
    }

    public void setRunningStatusId(Integer runningStatusId) {
        this.runningStatusId = runningStatusId;
    }

    public String getRunningStatusName() {
        return runningStatusName;
    }

    public void setRunningStatusName(String runningStatusName) {
        this.runningStatusName = runningStatusName;
    }

    public String getRunningStatusText() {
        return runningStatusText;
    }

    public void setRunningStatusText(String runningStatusText) {
        this.runningStatusText = runningStatusText;
    }

}
