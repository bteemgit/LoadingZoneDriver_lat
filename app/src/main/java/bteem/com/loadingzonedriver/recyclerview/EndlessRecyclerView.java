package bteem.com.loadingzonedriver.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by admin on 7/6/2017.
 */

public class EndlessRecyclerView extends RecyclerView {
    int pastVisibleItems, visibleItemCount, totalItemCount;
    PaginationListener paginationListener;
    private boolean haveMoreItem = false;

    public EndlessRecyclerView(Context context) {
        super(context);
    }

    public EndlessRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EndlessRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setHaveMoreItem(boolean haveMore) {
        this.haveMoreItem = haveMore;
    }

    public void addPaginationListener(PaginationListener listener) {
        this.paginationListener = listener;
    }


    @Override
    public void onScrolled(int dx, int dy) {
        System.out.println("newsfeed" + haveMoreItem);
        if (haveMoreItem) {
            visibleItemCount = getLayoutManager().getChildCount();
            totalItemCount = getLayoutManager().getItemCount();
            pastVisibleItems = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
            if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                haveMoreItem = false;
                Log.v("...", "Last Item Wow !");
                if (paginationListener != null)
                    paginationListener.onReachedBottom();
            }
        }
        super.onScrolled(dx, dy);
    }

//    public void setAdapter(GroupViewDonationsAdapter groupViewDonationsAdapter) {
//
//    }

    public interface PaginationListener {
        void onReachedBottom();
        void onReachedTop();
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {

        velocityY *= 2; // (between 0 for no fling, and 1 for normal fling, or more for faster fling)
        return super.fling(velocityX, velocityY);
    }
}
