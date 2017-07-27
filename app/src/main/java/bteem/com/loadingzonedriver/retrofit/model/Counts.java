
package bteem.com.loadingzonedriver.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Counts {

    @SerializedName("total_count")
    @Expose
    private String totalCount;
    @SerializedName("read_count")
    @Expose
    private String readCount;
    @SerializedName("unread_count")
    @Expose
    private String unreadCount;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getReadCount() {
        return readCount;
    }

    public void setReadCount(String readCount) {
        this.readCount = readCount;
    }

    public String getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(String unreadCount) {
        this.unreadCount = unreadCount;
    }

}
