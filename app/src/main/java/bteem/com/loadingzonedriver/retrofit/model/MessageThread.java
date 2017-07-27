
package bteem.com.loadingzonedriver.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageThread {

    @SerializedName("message_thread_id")
    @Expose
    private Integer messageThreadId;
    @SerializedName("message_type_id")
    @Expose
    private Integer messageTypeId;
    @SerializedName("reference_id")
    @Expose
    private Integer referenceId;
    @SerializedName("reference_name")
    @Expose
    private String referenceName;
    @SerializedName("job_id")
    @Expose
    private Integer jobId;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("created_date_relative")
    @Expose
    private String createdDateRelative;
    @SerializedName("recipient")
    @Expose
    private Recipient recipient;
    @SerializedName("counts")
    @Expose
    private Counts counts;

    public Integer getMessageThreadId() {
        return messageThreadId;
    }

    public void setMessageThreadId(Integer messageThreadId) {
        this.messageThreadId = messageThreadId;
    }

    public Integer getMessageTypeId() {
        return messageTypeId;
    }

    public void setMessageTypeId(Integer messageTypeId) {
        this.messageTypeId = messageTypeId;
    }

    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDateRelative() {
        return createdDateRelative;
    }

    public void setCreatedDateRelative(String createdDateRelative) {
        this.createdDateRelative = createdDateRelative;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public Counts getCounts() {
        return counts;
    }

    public void setCounts(Counts counts) {
        this.counts = counts;
    }

}
