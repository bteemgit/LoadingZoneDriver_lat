
package bteem.com.loadingzonedriver.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessageListResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("message_threads")
    @Expose
    private List<MessageThread> messageThreads = null;
    @SerializedName("_links")
    @Expose
    private Links links;
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<MessageThread> getMessageThreads() {
        return messageThreads;
    }

    public void setMessageThreads(List<MessageThread> messageThreads) {
        this.messageThreads = messageThreads;
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

}
