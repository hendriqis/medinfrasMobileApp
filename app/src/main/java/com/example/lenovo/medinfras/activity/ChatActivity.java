package com.example.lenovo.medinfras.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.medinfras.MainActivity;
import com.example.lenovo.medinfras.R;
import com.example.lenovo.medinfras.model.ChatMessage;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity {

    private static int SIGN_IN_REQUEST_CODE = 1;
    @BindView(R.id.chatRelativeLayout)
    RelativeLayout chatRelativeLayout;
    @BindView(R.id.fab_send)
    FloatingActionButton fabSend;
    @BindView(R.id.inputTextChat)
    EditText inputTextChat;
    @BindView(R.id.chatListView)
    ListView chatListView;

    private FirebaseListAdapter<ChatMessage> adapter;

    //notification
    NotificationCompat.Builder notification;
    private static final int uniqueID = 2312;
    private static final String CHANNEL_ID = "my_chn_id";
    private static Object firebasePush;
    NotificationChannel notificationChannel;


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuSignOut) {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>
                    () {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Snackbar.make(chatRelativeLayout, "You have been signed out", Snackbar
                            .LENGTH_SHORT).show();
                    startActivity(new Intent(ChatActivity.this, MainActivity.class));
                    finish();
                }
            });
        } else if (item.getItemId() == R.id.menuDeleteChat) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                    .getRoot();
            databaseReference.removeValue();
            Toast.makeText(this, "All chat deleted", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Snackbar.make(chatRelativeLayout, "Successfully signed in. Welcome!", Snackbar
                        .LENGTH_SHORT).show();
                displayChatMessage();
            } else {
                Snackbar.make(chatRelativeLayout, "We couldn't sign you in. Please try again " +
                        "later", Snackbar
                        .LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        chatRelativeLayout = (RelativeLayout) findViewById(R.id.chatRelativeLayout);
        fabSend = (FloatingActionButton) findViewById(R.id.fab_send);
        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.inputTextChat);
                firebasePush = FirebaseDatabase.getInstance().getReference().push().setValue(new
                        ChatMessage(input.getText
                        ().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getDisplayName()));
                input.setText("");

                //Notification triggered when the same uid press the send button
                adapter.registerDataSetObserver(new DataSetObserver() {
                    @Override
                    public void onChanged() {
                        if (FirebaseAuth.getInstance().getCurrentUser().getUid() != FirebaseAuth
                                .getInstance()
                                .getCurrentUser().getUid()) {
                            Toast.makeText(ChatActivity.this, "Same User", Toast.LENGTH_SHORT)
                                    .show();
                        } /*else {
                            showNotification();
                        }*/
                    }
                });
            }
        });

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),
                    SIGN_IN_REQUEST_CODE);
        } else {
            Snackbar.make(chatRelativeLayout, "Welcome " + FirebaseAuth.getInstance()
                            .getCurrentUser().getEmail(),
                    Snackbar.LENGTH_SHORT).show();
            displayChatMessage();
        }

        //Notification function
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID, "myChannel",
                    NotificationManager.IMPORTANCE_LOW);
        }
        notification = new NotificationCompat.Builder(this, CHANNEL_ID);
        notification.setAutoCancel(true);

    }

    private void displayChatMessage() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .limitToLast(100);

        FirebaseListOptions<ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>()
                .setLayout(R.layout.custom_list_view_chat)
                .setQuery(query, ChatMessage.class)
                .setLifecycleOwner(ChatActivity.this)
                .build();

        ListView listOfMessage = (ListView) findViewById(R.id.chatListView);
        adapter = new FirebaseListAdapter<ChatMessage>(options) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                TextView messageText, messageUser, messageTime;

                messageText = (TextView) v.findViewById(R.id.message_text);
                messageUser = (TextView) v.findViewById(R.id.message_user);
                messageTime = (TextView) v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model
                        .getMessageTime()));
            }
        };
        listOfMessage.setAdapter(adapter);
    }

    //function to build the notification
    /*private void showNotification() {
        //Build the notification
        notification.setSmallIcon(R.drawable.ic_notifications_active);
        notification.setTicker("This is ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        notification.setContentText("Sent new messages");

        //When you open another application, this function below allow you to move directly to
        specific activity, in this
        // case
        //ChatMessage activity
        Intent intent = new Intent(this, ChatActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent
        .FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //Build notification and issues it
        NotificationManager notificationManager = (NotificationManager) getSystemService
        (NOTIFICATION_SERVICE);
        notificationManager.notify(uniqueID, notification.build());
    }*/
}
