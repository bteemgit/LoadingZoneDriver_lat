package bteem.com.loadingzonedriver.modules.message;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bteem.com.loadingzonedriver.R;
import bteem.com.loadingzonedriver.global.FlipAnimator;
import bteem.com.loadingzonedriver.retrofit.model.MessageThread;
import bteem.com.loadingzonedriver.view.CircleTransformation;


/**
 * Created by admin on 7/13/2017.
 */

public class MessageListadapter extends RecyclerView.Adapter<MessageListadapter.MyViewHolder> {
    private Context mContext;
    List<MessageThread> messageThreadList=new ArrayList<>();
    private SelectedMessageList listener;
    private SparseBooleanArray selectedItems;

    // array used to perform multiple animation at once
    private SparseBooleanArray animationItemsIndex;
    private boolean reverseAllAnimations = false;
    private static int currentSelectedIndex = -1;



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView from, subject, message, iconText, timestamp;
        public ImageView iconImp, imgProfile;
        public LinearLayout messageContainer;
        public RelativeLayout iconContainer, iconBack, iconFront;


        LinearLayout messageLayout;
        TextView textViewMessageSub,textViewMessageRef,textViewMessageDays,textName;
        private ImageView spCircleImageView;

        public MyViewHolder(View view) {
            super(view);
            from = (TextView) view.findViewById(R.id.from);
            subject = (TextView) view.findViewById(R.id.txt_primary);
            message = (TextView) view.findViewById(R.id.txt_secondary);
            iconText = (TextView) view.findViewById(R.id.icon_text);
            timestamp = (TextView) view.findViewById(R.id.timestamp);
            iconBack = (RelativeLayout) view.findViewById(R.id.icon_back);
            iconFront = (RelativeLayout) view.findViewById(R.id.icon_front);
            iconImp = (ImageView) view.findViewById(R.id.icon_star);
            imgProfile = (ImageView) view.findViewById(R.id.icon_profile);
            messageContainer = (LinearLayout) view.findViewById(R.id.message_container);
            iconContainer = (RelativeLayout) view.findViewById(R.id.icon_container);
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onRowModelClicked(getAdapterPosition());
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }


    public MessageListadapter(Context mContext, List<MessageThread> messageThreadList, SelectedMessageList listener)  {
        this.mContext = mContext;
        this.messageThreadList = messageThreadList;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
        animationItemsIndex = new SparseBooleanArray();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        MessageThread data = messageThreadList.get(position);

        // displaying text view data
        holder.from.setText(data.getRecipient().getName());
        holder.subject.setText(data.getSubject());

        holder.message.setText(data.getCreatedDateRelative());
        String imageUrl=messageThreadList.get(position).getRecipient().getProfilePic();

       /* Picasso.with(mContext).load(imageUrl)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(holder.imgProfile);*/


        Picasso.with(mContext).load(imageUrl)
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(70, 70)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(holder.imgProfile);

        // change the row state to activated
        holder.itemView.setActivated(selectedItems.get(position, false));

        // handle icon animation
        applyIconAnimation(holder, position);

        // apply click events
        applyClickEvents(holder, position);
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {

        holder.messageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRowModelClicked(position);
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);

            }
        });

    }


    private void applyIconAnimation(MyViewHolder holder, int position) {
        if (selectedItems.get(position, false)) {
            holder.iconFront.setVisibility(View.GONE);
            resetIconYAxis(holder.iconBack);
            holder.iconBack.setVisibility(View.VISIBLE);
            holder.iconBack.setAlpha(1);
            if (currentSelectedIndex == position) {
                FlipAnimator.flipView(mContext, holder.iconBack, holder.iconFront, true);
                resetCurrentIndex();
            }
        } else {
            holder.iconBack.setVisibility(View.GONE);
            resetIconYAxis(holder.iconFront);
            holder.iconFront.setVisibility(View.VISIBLE);
            holder.iconFront.setAlpha(1);
            if ((reverseAllAnimations && animationItemsIndex.get(position, false)) || currentSelectedIndex == position) {
                FlipAnimator.flipView(mContext, holder.iconBack, holder.iconFront, false);
                resetCurrentIndex();
            }
        }
    }


    // As the views will be reused, sometimes the icon appears as
    // flipped because older view is reused. Reset the Y-axis to 0
    private void resetIconYAxis(View view) {
        if (view.getRotationY() != 0) {
            view.setRotationY(0);
        }
    }

    public void resetAnimationIndex() {
        reverseAllAnimations = false;
        animationItemsIndex.clear();
    }

    @Override
    public long getItemId(int position) {
        return messageThreadList.get(position).getMessageTypeId();
    }


    @Override
    public int getItemCount() {
        return messageThreadList.size();
    }

    public void toggleSelection(int pos) {
        currentSelectedIndex = pos;
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
            animationItemsIndex.delete(pos);
        } else {
            selectedItems.put(pos, true);
            animationItemsIndex.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        reverseAllAnimations = true;
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public ArrayList<String> getSelectedItems() {
        ArrayList<String> items =
                new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {

            // items.add(String.valueOf(messageThreadList.get(i).getMessages().get(i).getMessageId()));
            items.add(String.valueOf(messageThreadList.get(selectedItems.keyAt(i)).getMessageThreadId()));
        }
        return items;

    }


    private void resetCurrentIndex() {
        currentSelectedIndex = -1;
    }

    public interface SelectedMessageList {

        void onRowModelClicked(int position);
    }
}

