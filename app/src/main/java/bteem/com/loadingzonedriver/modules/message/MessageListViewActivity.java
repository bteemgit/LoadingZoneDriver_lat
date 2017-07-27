package bteem.com.loadingzonedriver.modules.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;



import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bteem.com.loadingzonedriver.R;
import bteem.com.loadingzonedriver.global.AppController;
import bteem.com.loadingzonedriver.global.BaseActivity;
import bteem.com.loadingzonedriver.global.GloablMethods;
import bteem.com.loadingzonedriver.global.MessageConstants;
import bteem.com.loadingzonedriver.modules.message.MessageDetailListActivity;
import bteem.com.loadingzonedriver.modules.message.MessageListadapter;
import bteem.com.loadingzonedriver.recyclerview.EndlessRecyclerView;
import bteem.com.loadingzonedriver.recyclerview.RecyclerItemClickListener;
import bteem.com.loadingzonedriver.recyclerview.SimpleDividerItemDecoration;
import bteem.com.loadingzonedriver.retrofit.ApiClient;
import bteem.com.loadingzonedriver.retrofit.ApiInterface;
import bteem.com.loadingzonedriver.retrofit.model.MessageListResponse;
import bteem.com.loadingzonedriver.retrofit.model.MessageThread;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageListViewActivity extends BaseActivity implements MessageListadapter.SelectedMessageList{

    @NonNull
    @BindView(R.id.recyclerview_messageList)
    EndlessRecyclerView recyclerViewMessageList;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.progressBarFooter)
    ProgressBar progressBar;
    @BindView(R.id.parent)
    RelativeLayout relativeLayoutRoot;
    ApiInterface apiService;
    List<MessageThread> messageThreadList = new ArrayList<>();
    private int limit = 30;
    private int offset = 1;
    private boolean hasReachedTop = false;
    private ActionModeCallback actionModeCallback;
    public static int RESULTOK = 50;
    private ActionMode actionMode;
    MessageListadapter messageListAdapter;
    private EndlessRecyclerView.PaginationListener paginationListener = new EndlessRecyclerView.PaginationListener() {
        @Override
        public void onReachedBottom() {
            getMessageList();
        }

        @Override
        public void onReachedTop() {
            hasReachedTop = true;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list_view);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Messages");
        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ButterKnife.bind(this);
        actionModeCallback = new ActionModeCallback();
        apiService = ApiClient.getClient().create(ApiInterface.class);
        refreshLayout.setRefreshing(false);
        setUpListeners();
        if (isConnectingToInternet(MessageListViewActivity.this)) {
            getMessageList();
        } else {
            showSnakBar(relativeLayoutRoot, MessageConstants.INTERNET);
        }

    }


    // back button action
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setUpListeners() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewMessageList.setLayoutManager(layoutManager);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                refreshLayout.setRefreshing(true);
                offset = 1;
                getMessageList();
            }
        });
        recyclerViewMessageList.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
        recyclerViewMessageList.addPaginationListener(paginationListener);
        recyclerViewMessageList.addOnItemTouchListener(new RecyclerItemClickListener(MessageListViewActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String meesge_thread_id = String.valueOf(messageThreadList.get(position).getMessageThreadId());
                String meesge_user = messageThreadList.get(position).getRecipient().getName();
                String meesge_subj = messageThreadList.get(position).getSubject();
                String meesge_refer = messageThreadList.get(position).getReferenceName();
                Intent i = new Intent(getApplicationContext(), MessageDetailListActivity.class);
                i.putExtra("messge_thread_id", meesge_thread_id);
                i.putExtra("messge_user", meesge_user);
                i.putExtra("messge_subj", meesge_subj);
                i.putExtra("messge_refer", meesge_refer);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        }));

    }

    public void getMessageList() {
        if (offset == 1) {
            showProgressDialog(getApplicationContext(),"loading...");
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
        apiService = ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<MessageListResponse> call = apiService.MessageList(GloablMethods.API_HEADER + acess_token, offset);
        call.enqueue(new Callback<MessageListResponse>() {
            @Override
            public void onResponse(Call<MessageListResponse> call, Response<MessageListResponse> response) {
                refreshLayout.setRefreshing(false);
                hideProgressDialog();
                if (response.isSuccessful() && response.body() != null) {
                    if (!response.body().getMessageThreads().isEmpty()) {
                        List<MessageThread> ThreadList = response.body().getMessageThreads();
                        if (ThreadList.size()>0)
                            if (offset == 1) {
                                messageThreadList = ThreadList;
                                updateEndlessRecyclerView();
                                hideProgressDialog();
                            } else {
                                progressBar.setVisibility(View.VISIBLE);
                                for (MessageThread itemModel : ThreadList) {
                                    messageThreadList.add(itemModel);
                                }
                            }
                        if (ThreadList.size() < limit) {
                            recyclerViewMessageList.setHaveMoreItem(false);
                        } else {
                            recyclerViewMessageList.setHaveMoreItem(true);
                        }
                        messageListAdapter.notifyDataSetChanged();
                        offset = offset + 1;
                    } else {
                        recyclerViewMessageList.setHaveMoreItem(false);
                    }
                } else {
                    // GlobalMethods.showToast(ContestListActivity.this, getString(R.string.try_again), 1);
                    finish();
                    recyclerViewMessageList.setHaveMoreItem(false);
                }
                if (!response.isSuccessful()) {

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(relativeLayoutRoot, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MessageListResponse> call, Throwable t) {
                // Log error here since request failed
                hideProgressDialog();
            }
        });
    }
    private void updateEndlessRecyclerView() {
        messageListAdapter = new MessageListadapter(MessageListViewActivity.this, messageThreadList, MessageListViewActivity.this);
        recyclerViewMessageList.setAdapter(messageListAdapter);
        // progressDialog.dismiss();
    }

    @Override
    public void onRowModelClicked(int position) {
        enableActionMode(position);
    }
    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);

    }
    private void toggleSelection(int position) {
        if (messageListAdapter != null) {
            messageListAdapter.toggleSelection(position);
            int count = messageListAdapter.getSelectedItemCount();
            if (count == 0) {
                actionMode.finish();
            } else {
                actionMode.setTitle(String.valueOf(count));
                actionMode.invalidate();
            }
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);

            // disable swipe refresh if action mode is enabled
            // swipeRefreshLayout.setEnabled(false);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_tick:
                    // delete all the selected messages
                    // deleteMessages();
                    if (messageListAdapter != null)  {
                        ArrayList<String> selectedMessageThreadId = selectedMessageThreadId();

                        // call for deletig the selected list
                        finish();
                        mode.finish();
                        return true;
                    }


                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (messageListAdapter != null ) {
                messageListAdapter.clearSelections();
                actionMode = null;
                recyclerViewMessageList.post(new Runnable() {
                    @Override
                    public void run() {
                        messageListAdapter.resetAnimationIndex();

                    }
                });
            }
        }
    }

    // getteing slected message list from the adpater
    private ArrayList<String> selectedMessageThreadId() {
        // mAdapter.resetAnimationIndex();
        ArrayList<String> selectedItemPositions =
                messageListAdapter.getSelectedItems();
        return selectedItemPositions;
    }
}
