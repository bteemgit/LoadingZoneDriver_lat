package bteem.com.loadingzonedriver.modules.notification;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import bteem.com.loadingzonedriver.R;
import bteem.com.loadingzonedriver.modules.view.ColorGenerator;
import bteem.com.loadingzonedriver.modules.view.TextDrawable;
import bteem.com.loadingzonedriver.retrofit.model.NotificationList;

/**
 * Created by admin on 7/20/2017.
 */

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {

    private List<NotificationList> nottificationList = new ArrayList<>();
    private int rowLayout;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewNottificationMsg;
        RelativeLayout linearLayoutNottification;
        TextView textNotificationDate;
        TextView textJobTitle;

        ImageView img;

        public ViewHolder(View v) {
            super(v);

            textViewNottificationMsg = (TextView) v.findViewById(R.id.text_nottifiction_msg);
            textNotificationDate = (TextView) v.findViewById(R.id.text_notificationDate);
            linearLayoutNottification = (RelativeLayout) v.findViewById(R.id.liner_nottifiction);
            textJobTitle = (TextView) v.findViewById(R.id.text_jobTitle);

            img = (ImageView) v.findViewById(R.id.imageLogo_quot);


        }

        @Override
        public void onClick(View v) {

        }
    }

    public NotificationListAdapter(List<NotificationList> nottificationList, int rowLayout, Context context) {
        this.nottificationList = nottificationList;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public NotificationListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new NotificationListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        // For setting the material Textdrawable
        ColorGenerator generator = ColorGenerator.MATERIAL;
        String Notification_msg = nottificationList.get(position).getMessage();
        String Date = nottificationList.get(position).getNotificationDate();
        String jobTitle = nottificationList.get(position).getNotificationMeta().getJobTitle();
        String Notification_status = nottificationList.get(position).getStatus();
        holder.textJobTitle.setText(jobTitle);
        holder.textNotificationDate.setText(Date);
        holder.textViewNottificationMsg.setText(Notification_msg);
        generator = ColorGenerator.MATERIAL;
        String job_title = nottificationList.get(position).getNotificationMeta().getJobTitle();
        // int  color = generator.getColor(job_title);
        int  color = generator.getRandomColor();
        if (job_title!=null)
        {
            String firstLetter = String.valueOf(job_title.charAt(0));
            TextDrawable drawable1 = TextDrawable.builder().beginConfig().fontSize(45)
                    .useFont(Typeface.defaultFromStyle(Typeface.NORMAL))
                    .withBorder(0).endConfig()
                    .buildRound(firstLetter.toUpperCase(), color
                    );
            holder.img.setImageDrawable(drawable1);
            //code to set data in textview as initCap
            String var = (String) holder.textJobTitle.getText();
            String output = var.substring(0,1).toUpperCase() + var.substring(1);
            holder.textJobTitle.setText(output);
        }
        else {
            return ;
        }

    }

    @Override
    public int getItemCount() {
        if (nottificationList != null) {
            return nottificationList.size();
        }
        return 0;
    }
}

