package bteem.com.loadingzonedriver.modules.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bteem.com.loadingzonedriver.R;
import bteem.com.loadingzonedriver.retrofit.model.JobList;
import bteem.com.loadingzonedriver.view.CircleTransformation;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 7/6/2017.
 */

public class PostedJobListAdapter extends RecyclerView.Adapter<PostedJobListAdapter.ViewHolder> {

    private List<JobList> jobList = new ArrayList<>();
    private int rowLayout;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @NonNull
        @BindView(R.id.textCustomerName)
        TextView textViewCustomerName;
        @NonNull
        @BindView(R.id.textLocationFrom)
        TextView textViewLocationFrom;
        @NonNull
        @BindView(R.id.textAssignedtruckName)
        TextView textAssignedtruckName;
        @NonNull
        @BindView(R.id.textLocationTo)

        TextView textViewLocationTo;
        @NonNull
        @BindView(R.id.textTruckType)
        TextView textViewTruckType;
        @NonNull
        @BindView(R.id.textTruckDimension)
        TextView textViewTruckDimension;
        @NonNull
        @BindView(R.id.textTruckDate)
        TextView textViewTruckDate;
        @NonNull
        @BindView(R.id.textTruckBudget)
        TextView textViewTruckBudget;
        @NonNull
        @BindView(R.id.imageViewCustomPic)
        ImageView imageViewCustomPic;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public PostedJobListAdapter(List<JobList> jobList, int rowLayout, Context context) {
        this.jobList = jobList;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public PostedJobListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PostedJobListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostedJobListAdapter.ViewHolder holder, int position) {

        holder.textViewCustomerName.setText(jobList.get(position).getCustomer().getName());
        holder.textViewLocationTo.setText(jobList.get(position).getToLocation().getName());
        holder.textViewLocationFrom.setText(jobList.get(position).getFromLocation().getName());
        holder.textViewTruckType.setText(jobList.get(position).getTruckType().getTruckTypeName());
        holder.textViewTruckDimension.setText(jobList.get(position).getTruckSize().getTruckSizeDimension());
      holder.textAssignedtruckName.setText(jobList.get(position).getAssignedVehicle().getVehicleDetails().getCustomName());
//        holder.textViewTruckBudget.setText(jobList.get(position).getBudget());

        Picasso.with(context)
                .load(jobList.get(position).getCustomer().getProfilePic())
                .resize(70, 70)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(holder.imageViewCustomPic);
    }

    @Override
    public int getItemCount() {
        if (jobList != null) {
            return jobList.size();
        }
        return 0;
    }
}


