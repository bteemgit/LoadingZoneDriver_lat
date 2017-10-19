package bteem.com.loadingzonedriver.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 10/16/2017.
 */

public class ScheduledStatus {
    public Integer getScheduled_status_id() {
        return scheduled_status_id;
    }

    public void setScheduled_status_id(Integer scheduled_status_id) {
        this.scheduled_status_id = scheduled_status_id;
    }

    public String getScheduled_status_text() {
        return scheduled_status_text;
    }

    public void setScheduled_status_text(String scheduled_status_text) {
        this.scheduled_status_text = scheduled_status_text;
    }

    public String getScheduled_status_name() {
        return scheduled_status_name;
    }

    public void setScheduled_status_name(String scheduled_status_name) {
        this.scheduled_status_name = scheduled_status_name;
    }

    @SerializedName("scheduled_status_id")
    @Expose
    private Integer scheduled_status_id;
    @SerializedName("scheduled_status_text")
    @Expose
    private String scheduled_status_text;
    @SerializedName("scheduled_status_name")
    @Expose
    private String scheduled_status_name;
}
