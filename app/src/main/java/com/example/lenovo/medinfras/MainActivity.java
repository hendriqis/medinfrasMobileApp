package com.example.lenovo.medinfras;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.lenovo.medinfras.activity.CalendarActivity;
import com.example.lenovo.medinfras.activity.CalendarViewActivity;
import com.example.lenovo.medinfras.activity.ChatActivity;
import com.example.lenovo.medinfras.activity.LoginActivity;
import com.example.lenovo.medinfras.activity.SummaryOfPatientPerFloorActivity;
import com.example.lenovo.medinfras.activity.WeekViewActivity;
import com.example.lenovo.medinfras.model.ChatMessage;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.btnEW6C1)
    CardView btnEW6C1;
    @BindView(R.id.btnEW6C2)
    CardView btnEW6C2;
    @BindView(R.id.btnEW6C3)
    CardView btnEW6C3;
    @BindView(R.id.btnEW6VIP)
    CardView btnEW6VIP;
    @BindView(R.id.outpatientBtn)
    Button outpatientBtn;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    @BindView(R.id.fab_logout)
    FloatingActionButton fabLogout;
    @BindView(R.id.fab_hamburger)
    FloatingActionButton fabHamburger;
    @BindView(R.id.fab_notification)
    FloatingActionButton fabNotification;

    Animation FabOpen, FabClose, FabRotateClockwise, FabRotateAnticlockwise;
    boolean isOpen = false;

    private Context context;

    //notification
    NotificationCompat.Builder notification;
    private static final int uniqueID = 2312;
    private static final String CHANNEL_ID = "my_chn_id";
    NotificationChannel notificationChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Notification function
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID, "myChannel",
                    NotificationManager.IMPORTANCE_LOW);
        }
        notification = new NotificationCompat.Builder(this, CHANNEL_ID);
        notification.setAutoCancel(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab_hamburger = (FloatingActionButton) findViewById(R.id
                .fab_hamburger);
        final FloatingActionButton fab_logout = (FloatingActionButton) findViewById(R.id
                .fab_logout);
        final FloatingActionButton fab_notification = (FloatingActionButton) findViewById(R.id
                .fab_notification);
        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        FabRotateClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim
                .fab_rotate_clockwise);
        FabRotateAnticlockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim
                .fab_rotate_anticlockwise);
        fab_hamburger.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (isOpen) {
                     fab_logout.startAnimation(FabClose);
                     fab_notification.startAnimation(FabClose);
                     fab_hamburger.startAnimation(FabRotateAnticlockwise);
                     fab_logout.setClickable(false);
                     fab_notification.setClickable(false);
                     isOpen = false;
                 } else {
                     fab_logout.startAnimation(FabOpen);
                     fab_notification.startAnimation(FabOpen);
                     fab_hamburger.startAnimation(FabRotateClockwise);
                     fab_logout.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                             builder.setTitle("Log Out")
                                     .setMessage("Do you want to " + "log out?")
                                     .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                         @Override
                                         public void onClick(DialogInterface dialogInterface, int i) {
                                             /*startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                             finish();*/
                                             Intent intent = new Intent(MainActivity.this,
                                                     LoginActivity.class);
                                             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                                     Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                             startActivity(intent);
                                             finish();
                                         }
                                     })
                                     .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                         @Override
                                         public void onClick(DialogInterface dialogInterface, int i) {
                                             dialogInterface.cancel();
                                         }
                                     }).show();
                         }
                     });
                     fab_notification.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             showNotification();
                         }
                     });
                     isOpen = true;
                 }
             }
         }

        );

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string
                .navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_home:
                        getApplicationContext();
                        break;
                    case R.id.tab_chat:
                        Intent toChatActivity = new Intent(MainActivity.this, ChatActivity.class);
                        startActivity(toChatActivity);
                        finish();
                        break;
                    case R.id.tab_calendar:
                        Intent toCalendarActivity = new Intent(MainActivity.this,
                                CalendarActivity.class);
                        startActivity(toCalendarActivity);
                        break;
                    case R.id.tab_friends:
                        Toast.makeText(MainActivity.this, "friends", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(int tabId) {
                switch (tabId) {
                    case R.id.tab_home:
                        getApplicationContext();
                        break;
                    case R.id.tab_chat:
                        Intent toChatActivity = new Intent(MainActivity.this, ChatActivity.class);
                        startActivity(toChatActivity);
                        break;
                    case R.id.tab_calendar:
                        Intent toCalendarActivity = new Intent(MainActivity.this,
                                CalendarActivity.class);
                        startActivity(toCalendarActivity);
                        break;
                    case R.id.tab_friends:
                        Toast.makeText(MainActivity.this, "friends", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuItem1) {
            Toast.makeText(this, "Menu Item 1", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_calendar) {
            startActivity(new Intent(MainActivity.this, CalendarViewActivity.class));

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick({R.id.btnEW6C1, R.id.btnEW6C2, R.id.btnEW6C3, R.id.btnEW6VIP})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnEW6C1:
                Intent intentClass1 = new Intent(this, SummaryOfPatientPerFloorActivity.class);
                intentClass1.putExtra("dynamicTitle", view.getId());
                startActivity(intentClass1);
                break;
            case R.id.btnEW6C2:
                Intent intentClass2 = new Intent(this, SummaryOfPatientPerFloorActivity.class);
                intentClass2.putExtra("dynamicTitle", view.getId());
                startActivity(intentClass2);
                break;
            case R.id.btnEW6C3:
                Intent intentClass3 = new Intent(this, SummaryOfPatientPerFloorActivity.class);
                intentClass3.putExtra("dynamicTitle", view.getId());
                startActivity(intentClass3);
                break;
            case R.id.btnEW6VIP:
                Intent intentClass4 = new Intent(this, SummaryOfPatientPerFloorActivity.class);
                intentClass4.putExtra("dynamicTitle", view.getId());
                startActivity(intentClass4);
                break;
        }
    }

    private void showNotification() {
        //Build the notification
        notification.setSmallIcon(R.drawable.ic_notifications_active);
        notification.setTicker("This is ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("This is title notification");
        notification.setContentText("This is description notification");

        //When you open another application, this function below allow you to move directly to
        // specific activity, in this case
        //ChatMessage activity
        Intent intent = new Intent(this, ChatMessage.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent
                .FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //Build notification and issues it
        NotificationManager notificationManager = (NotificationManager) getSystemService
                (NOTIFICATION_SERVICE);
        notificationManager.notify(uniqueID, notification.build());

    }

    @OnClick(R.id.outpatientBtn)
    public void onViewClicked() {
        Toast.makeText(this, "Outpatient Clicked", Toast.LENGTH_SHORT).show();
    }
}
