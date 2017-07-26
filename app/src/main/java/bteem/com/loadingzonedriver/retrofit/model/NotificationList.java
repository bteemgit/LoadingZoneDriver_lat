
package bteem.com.loadingzonedriver.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationList {

    @SerializedName("notification_id")
    @Expose
    private Integer notificationId;
    @SerializedName("notification_type")
    @Expose
    private String notificationType;
    @SerializedName("notification_meta")
    @Expose
    private NotificationMeta notificationMeta;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("notification_date")
    @Expose
    private String notificationDate;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public NotificationMeta getNotificationMeta() {
        return notificationMeta;
    }

    public void setNotificationMeta(NotificationMeta notificationMeta) {
        this.notificationMeta = notificationMeta;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
