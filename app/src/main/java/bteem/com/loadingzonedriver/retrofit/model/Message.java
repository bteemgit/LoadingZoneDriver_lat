
package bteem.com.loadingzonedriver.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {
    public static final int TYPE_USER=0;
    public static final int TYPE_OTHER=1;
    @SerializedName("message_id")
    @Expose
    private Integer messageId;
    @SerializedName("message_thread_id")
    @Expose
    private Integer messageThreadId;
    @SerializedName("sender_user_id")
    @Expose
    private Integer senderUserId;
    @SerializedName("receiver_user_id")
    @Expose
    private Integer receiverUserId;
    @SerializedName("sender_type")
    @Expose
    private Integer senderType;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("read_by")
    @Expose
    private Integer readBy;
    @SerializedName("sent_time")
    @Expose
    private String sentTime;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getMessageThreadId() {
        return messageThreadId;
    }

    public void setMessageThreadId(Integer messageThreadId) {
        this.messageThreadId = messageThreadId;
    }

    public Integer getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(Integer senderUserId) {
        this.senderUserId = senderUserId;
    }

    public Integer getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(Integer receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public Integer getSenderType() {
        return senderType;
    }

    public void setSenderType(Integer senderType) {
        this.senderType = senderType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getReadBy() {
        return readBy;
    }

    public void setReadBy(Integer readBy) {
        this.readBy = readBy;
    }

    public String getSentTime() {
        return sentTime;
    }

    public void setSentTime(String sentTime) {
        this.sentTime = sentTime;
    }

}
