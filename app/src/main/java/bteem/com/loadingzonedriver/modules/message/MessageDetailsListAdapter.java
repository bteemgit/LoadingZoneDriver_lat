package bteem.com.loadingzonedriver.modules.message;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.List;

import bteem.com.loadingzonedriver.R;
import bteem.com.loadingzonedriver.retrofit.model.Message;

/**
 * Created by admin on 7/13/2017.
 */

public class MessageDetailsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Message> mList;
    private Context context;

    public MessageDetailsListAdapter(List<Message> mList,Context context) {
        this.mList = mList;
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Message.TYPE_OTHER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_user_others, parent, false);
                return new SelfUserViewHolder(view);
            case Message.TYPE_USER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_user_self, parent, false);
                return new OtherUserViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = mList.get(position);
        if (message != null) {
            switch (message.getSenderType()) {
                case Message.TYPE_OTHER:
                    ((SelfUserViewHolder) holder).textView_self_message.setText(message.getMessage());
                    ((SelfUserViewHolder) holder).textView_self_time.setText(message.getSentTime());

                    break;
                case Message.TYPE_USER:
                    ((OtherUserViewHolder) holder).textView_other_message.setText(message.getMessage());
                    ((OtherUserViewHolder) holder).textView_other_time.setText(message.getSentTime());
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList != null) {
            Message message = mList.get(position);
            if (message != null) {
                if (message.getSenderType()==0)
                    return Message.TYPE_USER;
                else
                    return  Message.TYPE_OTHER;
            }
        }
        return 0;
    }

    public static class SelfUserViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_self_message;
        private TextView textView_self_time;
        private TextView textView_self_date;

        public SelfUserViewHolder(View itemView) {
            super(itemView);
            textView_self_message = (TextView) itemView.findViewById(R.id.text_self_message);
            textView_self_time = (TextView) itemView.findViewById(R.id.time_text);

        }
    }

    public static class OtherUserViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_other_message;
        private TextView textView_other_time;
        private TextView textView_other_date;
        public OtherUserViewHolder(View itemView) {
            super(itemView);
            textView_other_message = (TextView) itemView.findViewById(R.id.text_other_message);
            textView_other_time = (TextView) itemView.findViewById(R.id.time_text);


        }
    }
}
