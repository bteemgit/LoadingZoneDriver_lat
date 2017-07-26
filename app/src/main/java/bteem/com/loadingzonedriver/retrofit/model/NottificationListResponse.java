
package bteem.com.loadingzonedriver.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NottificationListResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("notification_list")
    @Expose
    private List<NotificationList> notificationList = null;
    @SerializedName("_links")
    @Expose
    private Links links;
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("notification_count")
    @Expose
    private NotificationCount notificationCount;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<NotificationList> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<NotificationList> notificationList) {
        this.notificationList = notificationList;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public NotificationCount getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationCount(NotificationCount notificationCount) {
        this.notificationCount = notificationCount;
    }

}
