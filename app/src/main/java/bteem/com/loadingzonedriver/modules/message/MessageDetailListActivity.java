package bteem.com.loadingzonedriver.modules.message;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bteem.com.loadingzonedriver.R;
import bteem.com.loadingzonedriver.global.AppController;
import bteem.com.loadingzonedriver.global.BaseActivity;
import bteem.com.loadingzonedriver.global.GloablMethods;
import bteem.com.loadingzonedriver.global.MessageConstants;
import bteem.com.loadingzonedriver.modules.view.ColorGenerator;
import bteem.com.loadingzonedriver.modules.view.TextDrawable;
import bteem.com.loadingzonedriver.retrofit.ApiClient;
import bteem.com.loadingzonedriver.retrofit.ApiInterface;
import bteem.com.loadingzonedriver.retrofit.model.Message;
import bteem.com.loadingzonedriver.retrofit.model.MessageDetailsResponse;
import bteem.com.loadingzonedriver.retrofit.model.ReplyMessageResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageDetailListActivity extends BaseActivity {
    private ApiInterface apiService;
    private boolean Isprogress = true;
    List<Message> messageList = new ArrayList<>();
    String messge_thread_id, messageUser, messgeSubj, messageRefer;

    @NonNull
    @BindView(R.id.recyclerView_messages)
    RecyclerView mRecyclerView;
    @NonNull
    @BindView(R.id.chat_edit_text1)
    EditText chatEditText;
    @NonNull
    @BindView(R.id.enter_chat1)
    ImageView enterChatView;
    @NonNull
    @BindView(R.id.imageLogo)
    ImageView img_subject;
    @NonNull
    @BindView(R.id.text_messageReference)
    TextView text_reference;
    @NonNull
    @BindView(R.id.text_messageSubject)
    TextView text_subject;
    @NonNull
    @BindView(R.id.rootView)
    RelativeLayout relativeLayout;

    private EditText.OnKeyListener keyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            // If the event is a key-down event on the "enter" button
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Perform action on key press
                EditText editText = (EditText) v;

                if (v == chatEditText) {

                    if (isConnectingToInternet(getApplicationContext())) {
                        sendMessage(editText.getText().toString(), messge_thread_id);
                    } else {

                        showSnakBar(relativeLayout, MessageConstants.INTERNET);
                    }
                }
                chatEditText.setText("");

                return true;
            }
            return false;

        }
    };
    private ImageView.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == enterChatView) {
                if (isConnectingToInternet(getApplicationContext())) {
                    sendMessage(chatEditText.getText().toString(), messge_thread_id);
                } else {

                    showSnakBar(relativeLayout, MessageConstants.INTERNET);
                }
            }

            chatEditText.setText("");

        }
    };
    private final TextWatcher watcher1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (chatEditText.getText().toString().equals("")) {

            } else {
                enterChatView.setImageResource(R.drawable.ic_writesomething);

            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() == 0) {
                enterChatView.setImageResource(R.drawable.ic_writesomething);
            } else {
                enterChatView.setImageResource(R.drawable.ic_writesomethingblue);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);
// receiving data from message list activity on item click
        messge_thread_id = getIntent().getStringExtra("messge_thread_id");
        messageUser = getIntent().getStringExtra("messge_user");
        messgeSubj = getIntent().getStringExtra("messge_subj");
        messageRefer = getIntent().getStringExtra("messge_refer");
        getSupportActionBar().setTitle(messageUser);
        text_reference.setText(messageRefer);
        text_subject.setText(messgeSubj);
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(messgeSubj);
        String firstLetter = String.valueOf(messgeSubj.charAt(0));
        TextDrawable drawable1 = TextDrawable.builder().beginConfig().fontSize(45)
                .useFont(Typeface.defaultFromStyle(Typeface.NORMAL))
                .withBorder(2).endConfig()
                .buildRound(firstLetter.toUpperCase(), color
                );
        img_subject.setImageDrawable(drawable1);
        // set on click listner
        chatEditText.setOnKeyListener(keyListener);

        enterChatView.setOnClickListener(clickListener);

        chatEditText.addTextChangedListener(watcher1);

        // getting extsiting message list
        if (isConnectingToInternet(getApplicationContext())) {
            getMessageList(messge_thread_id, true);
        } else {
            showSnakBar(relativeLayout, MessageConstants.INTERNET);
        }
    }

    // back button action
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //getting the message
    public void getMessageList(String message_thread_id, final boolean Isprogress) {

        if (Isprogress) {
            showProgressDialog(getApplicationContext(), "loading messages..");
        }
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<MessageDetailsResponse> call = apiService.MessageListDetails(GloablMethods.API_HEADER + acess_token, message_thread_id);
        call.enqueue(new Callback<MessageDetailsResponse>() {


            @Override
            public void onResponse(Call<MessageDetailsResponse> call, Response<MessageDetailsResponse> response) {
                if (Isprogress) {
                    hideProgressDialog();
                }
                if (response.isSuccessful()) {
                    messageList = response.body().getMessages();
                    MessageDetailsListAdapter adapter = new MessageDetailsListAdapter(messageList,getApplicationContext());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), OrientationHelper.VERTICAL, false);
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.setAdapter(adapter);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(relativeLayout, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<MessageDetailsResponse> call, Throwable t) {
                if (Isprogress) {
                    hideProgressDialog();
                }
            }
        });

    }

    public void sendMessage
            (String message, final String messge_thread_id) {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        String acess_token = AppController.getString(getApplicationContext(), "acess_token");
        Call<ReplyMessageResponse> call = apiService.ReplyMessage(GloablMethods.API_HEADER + acess_token, message, messge_thread_id);
        call.enqueue(new Callback<ReplyMessageResponse>() {
            @Override
            public void onResponse(Call<ReplyMessageResponse> call, Response<ReplyMessageResponse> response) {

                //   progressDialog.dismiss();

                if (response.isSuccessful())

                {
                    if (isConnectingToInternet(getApplicationContext())) {
                        getMessageList(messge_thread_id, false);
                    } else {
                        showSnakBar(relativeLayout, MessageConstants.INTERNET);
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        JSONObject meta = jObjError.getJSONObject("meta");
                        Snackbar snackbar = Snackbar
                                .make(relativeLayout, meta.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReplyMessageResponse> call, Throwable t) {
                // Log error here since request failed
                //   progressDialog.dismiss();

            }
        });
    }

}
