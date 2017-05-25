package com.example.app_dev.healthcare.activity;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_dev.healthcare.R;
import com.example.app_dev.healthcare.adapter.HealthCareMainAdapter;
import com.example.app_dev.healthcare.bluetooth.BluetoothLeService;
import com.example.app_dev.healthcare.bluetooth.GattAttributes;
import com.example.app_dev.healthcare.dialog.ColorPickerDialog;
import com.example.app_dev.healthcare.item.CardViewItem;
import com.example.app_dev.healthcare.utils.HttpPostSend;
import com.example.app_dev.healthcare.utils.JSONUtil;
import com.example.app_dev.healthcare.utils.SharedPreferencesUtil;
import com.example.app_dev.healthcare.utils.Util;
import com.flask.colorpicker.OnColorSelectedListener;
import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;
import com.triggertrap.seekarc.SeekArc;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HealthCareMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager mViewPager;
    private HealthCareMainAdapter mainAdapter;
    private BluetoothLeService mBluetoothLeService;

    //    private BluetoothGattCharacteristic mNoti;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

    private BluetoothGattCharacteristic mWriteCharacteristic = null;

    private String mConnDeviceAddress;
    private ColorPickerDialog mColorPicker_Dialog;
    private HoloCircleSeekBar button_a_hcs;
    private HoloCircleSeekBar button_b_hcs;
    private HoloCircleSeekBar button_c_hcs;
    private ImageButton button_colorpicker;
    private ImageButton button_musicplay;
    private ImageButton controller_button_power;
    private Rect rect;
    private boolean isMusicPlaying = false;
    private MediaPlayer musicPlayer;

    public static Context mContext;
    public static boolean isBleConntection = false;

    private String lamp_off = "color 0,0,0/";
    private String mSelectedColor = null;
    private String mDefaultColor = "color 255,255,255/";
    private boolean isLamp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_care_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // NavigationView
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mContext = this;

        // 17.01.17 make Viewpager
        makeCardView();

        TextView tv = (TextView) findViewById(R.id.music_title_tv);
        tv.setSelected(true);

        ////////////////////////////////////////////////////////////////////////////////
        // init button & imageview
        ////////////////////////////////////////////////////////////////////////////////
        button_a_hcs = (HoloCircleSeekBar) findViewById(R.id.controller_button_a_seek);
        button_b_hcs = (HoloCircleSeekBar) findViewById(R.id.controller_button_b_seek);
        button_c_hcs = (HoloCircleSeekBar) findViewById(R.id.controller_button_c_seek);
        button_colorpicker = (ImageButton) findViewById(R.id.color_picker_ib);
        button_musicplay = (ImageButton) findViewById(R.id.music_play_ib);
        controller_button_power = (ImageButton) findViewById(R.id.controller_button_power);
        ImageButton button_music_menu = (ImageButton) findViewById(R.id.music_menu_ib);
        final SeekArc button_intensity_sa = (SeekArc) findViewById(R.id.controller_button_intensity_seek);

        // button event
        button_a_hcs.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int X = (int) event.getRawX();
                final int Y = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                        break;
                    case MotionEvent.ACTION_UP:
                        if (rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())) {
//                            Toast.makeText(HealthCareMainActivity.this, "Image Button A EVENT !!", Toast.LENGTH_SHORT).show();
//                            sendMessageToDevice("stop/");
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            sendMessageToDevice("start 2/");
                            button_a_hcs.setBackgroundResource(R.drawable.img_main_a_btn_sel);

                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
        button_b_hcs.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int X = (int) event.getRawX();
                final int Y = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                        break;
                    case MotionEvent.ACTION_UP:
                        if (rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())) {
//                            Toast.makeText(HealthCareMainActivity.this, "Image Button B EVENT !!", Toast.LENGTH_SHORT).show();
//                            sendMessageToDevice("stop/");
//                            try{ Thread.sleep(1000); }catch(Exception e){}
                            button_b_hcs.setBackgroundResource(R.drawable.img_main_b_btn_sel);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
        button_c_hcs.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int X = (int) event.getRawX();
                final int Y = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                        break;
                    case MotionEvent.ACTION_UP:
                        if (rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())) {
//                            sendMessageToDevice("stop/");
//                            try{ Thread.sleep(1000); }catch(Exception e){}
                            button_c_hcs.setBackgroundResource(R.drawable.img_main_c_btn_sel);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });

        // 2017.02.15
        button_colorpicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectionCheck()) {
                    if (isLamp) {
                        sendMessageToDevice(lamp_off);
                        button_colorpicker.setBackgroundResource(R.drawable.img_color_btn_nor);
                        isLamp = false;
                    } else {
                        if (mSelectedColor == null) {
                            sendMessageToDevice(mDefaultColor);
                            button_colorpicker.setBackgroundResource(R.drawable.img_color_btn_sel);
                        } else {
                            sendMessageToDevice(mSelectedColor);
                            button_colorpicker.setBackgroundResource(R.drawable.img_color_btn_sel);
                        }
                        isLamp = true;
                    }
                }
            }
        });
        // 17.02.14 long press event
        button_colorpicker.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showColorPicker_dialog();
                return true;
            }
        });

        button_musicplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMusicPlaying) {
                    musicStop();
                } else {
                    if (musicPlayer == null) {
                        musicPlayer = new MediaPlayer();
                    }
                    try {
                        musicStart();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        button_music_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HealthCareMainActivity.this, "isBoundService :: " + Util.isRunningService(mContext, BluetoothLeService.class), Toast.LENGTH_SHORT).show();
                new TEST_SERVER().execute("21");
//                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                int value = button_intensity_sa.getProgress();
//                Toast.makeText(HealthCareMainActivity.this, "progress value :: " + value, Toast.LENGTH_SHORT).show();
                Log.i("healthmax_test", "TEST EVENT!!!!!");
            }
        });

        //17.02.14 power button event
        controller_button_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectionCheck()) {
                    unbindService(mServiceConn);
                    updateMainUI(false);
                } else {
//                } else if (!isBleContection && !Util.isRunningService(mContext, BluetoothLeService.class)) {
                    if (SharedPreferencesUtil.getValue(mContext, SharedPreferencesUtil.LAST_DEVICE_ADRESS, "").length() > 0) {
                        startBindService(SharedPreferencesUtil.getValue(mContext, SharedPreferencesUtil.LAST_DEVICE_ADRESS, ""));
                        Toast.makeText(HealthCareMainActivity.this, "start servcie", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // 2017.02.15 intensity progress event
        button_intensity_sa.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int i, boolean b) {
            }
            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {
            }
            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {
                Toast.makeText(HealthCareMainActivity.this, "progress value :: " + seekArc.getProgress(), Toast.LENGTH_SHORT).show();
            }
        });

        // 2017.02.06 control button seekbar set
        setCircleSeekBar(61f, 35f, 88f);

        // 2017.02.02 register BroadcastReceiver
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
    }

    private void musicStop() {
        musicPlayer.stop();
        isMusicPlaying = false;
    }

    private void musicStart() throws IOException {
        musicPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        musicPlayer.setDataSource("http://www.cady.kr/healthcare/wellbingIndex/mp3/fatigue/mf0105.mp3");
        musicPlayer.prepare();
        musicPlayer.start();
        isMusicPlaying = true;
    }

    private void setCircleSeekBar(float value_a, float value_b, float value_c) {
        button_a_hcs.setValue(value_a);
        button_b_hcs.setValue(value_b);
        button_c_hcs.setValue(value_c);

        button_a_hcs.setAlpha(0.8f);
        button_b_hcs.setAlpha(0.8f);
        button_c_hcs.setAlpha(0.8f);
    }


    @Override
    protected void onResume() {
        super.onResume();
//        Util.isRunningService(mContext);
    }

    public void startBindService() {
        Intent gattServiceIntent = new Intent(HealthCareMainActivity.this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
    }

    public void startBindService(String deviceadress) {
        mConnDeviceAddress = deviceadress;
        Intent gattServiceIntent = new Intent(HealthCareMainActivity.this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.i("healthmax_test", "!mBluetoothLeService initialize()");
                finish();
            } else {
                Log.i("healthmax_test", "mBluetoothLeService connect");
                mBluetoothLeService.connect(mConnDeviceAddress);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("healthmax_test", "onServiceDisconnected");
            mBluetoothLeService = null;
        }
    };

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.i("healthmax_test", "onReceive !!!!!" + action);
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                Log.i("healthmax_test", "ACTION_GATT_CONNECTED !!!!!");

                if (DeviceAddActivity.mContext != null) {
                    ((DeviceAddActivity) DeviceAddActivity.mContext).finish();
                }
                updateMainUI(true);
//                mConnected = true;
//                updateConnectionState(R.string.connected);
//                invalidateOptionsMenu();
                controller_button_power.setBackgroundResource(R.drawable.img_main_pw_btn_sel);
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                Log.i("healthmax_test", "ACTION_GATT_DISCONNECTED !!!!!");
                if (Util.isRunningService(mContext, BluetoothLeService.class)) {
                    Log.i("healthmax_test", "unBindService!!!!!");
                    unbindService(mServiceConn);
                    updateMainUI(false);
                }
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                Log.i("healthmax_test", "ACTION_DATA_AVAILABLE !!!!!");
//                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };

    private boolean isConnectionCheck() {
        return isBleConntection && Util.isRunningService(mContext, BluetoothLeService.class);
    }

    // 2017.02.05 update UI : state true is connection , false is disconnection
    private void updateMainUI(boolean state) {
        if (state) {
            isBleConntection = true;
            isLamp = true;
            button_colorpicker.setBackgroundResource(R.drawable.img_color_btn_sel);
        } else {
            isBleConntection = false;
            isLamp = false;
            controller_button_power.setBackgroundResource(R.drawable.img_main_pw_btn_nor);
            button_colorpicker.setBackgroundResource(R.drawable.img_color_btn_nor);

            button_a_hcs.setBackgroundResource(R.drawable.img_mainbase_btn_nor);
            button_b_hcs.setBackgroundResource(R.drawable.img_mainbase_btn_nor);
            button_c_hcs.setBackgroundResource(R.drawable.img_mainbase_btn_nor);
        }
        // 17.02.15 bluetooth icon change
        invalidateOptionsMenu();
    }

    // Demonstrates how to iterate through the supported GATT Services/Characteristics.
    // In this sample, we populate the data structure that is bound to the ExpandableListView
    // on the UI.
    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        String unknownServiceString = "unknown service";
        String unknownCharaString = "unknown characteristic";
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            currentServiceData.put(
                    "NAME", GattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put("UUID", uuid);
            gattServiceData.add(currentServiceData);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
                    new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics =
                    gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas =
                    new ArrayList<BluetoothGattCharacteristic>();

            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                currentCharaData.put(
                        "NAME", GattAttributes.lookup(uuid, unknownCharaString));
                currentCharaData.put("UUID", uuid);
                gattCharacteristicGroupData.add(currentCharaData);

                // 17.02.02
                if (isWritableCharacteristic(gattCharacteristic)) {
                    mWriteCharacteristic = gattCharacteristic;
                }

            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }
    }

    private boolean isWritableCharacteristic(BluetoothGattCharacteristic chr) {
        if (chr == null) return false;

        final int charaProp = chr.getProperties();
        if (((charaProp & BluetoothGattCharacteristic.PROPERTY_WRITE) |
                (charaProp & BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)) > 0) {
            return true;
        } else {
            return false;
        }
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    private void sendMessageToDevice(String str) {
        byte[] strByte = str.getBytes();

        if (mWriteCharacteristic != null) {
            // 17.02.02 send message to bleDevice
            mWriteCharacteristic.setValue(strByte);
            mWriteCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
            mBluetoothLeService.writeRemoteCharacteristic(mWriteCharacteristic);
        }
    }

    // 17.01.17 cardView
    private void makeCardView() {
//        CardView cardView = (CardView) ll_card.findViewById(R.id.statue_cv);
//        LinearLayout ll_card = (LinearLayout) findViewById(R.id.cardview_root);
        mainAdapter = new HealthCareMainAdapter(getApplicationContext());

        // 2017.02.15 cardview save button event set
        mainAdapter.setStateSvaeClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "save !", Toast.LENGTH_SHORT).show();
            }
        });
        mainAdapter.setCardViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.getTag(R.id.statue_cv);
                Toast.makeText(getApplicationContext(), "CardView" + v.getTag(R.id.statue_cv), Toast.LENGTH_SHORT).show();
            }
        });

        // 2017.01.26 add list
        ArrayList<CardViewItem> al = new ArrayList<>();
        al.add(new CardViewItem("스트레스", "Stress", "Bad", "하늘은 스스로 돕는자를 돕는다.", "-기분 안좋을 때 스트레스 감소에 좋음-"));
        al.add(new CardViewItem("수면상태", "Sleep", "Good", "하늘은 스스로 돕는자를 돕는다.", "-기분 안좋을 때 피로감 감소에 좋음-"));
        al.add(new CardViewItem("우울감", "Melancholy", "Nomal", "하늘은 스스로 돕는자를 돕는다.", "-기분 안좋을 때 우울감 감소에 좋음-"));
        al.add(new CardViewItem("피로감", "Fatigue", "Bad", "가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하", "-기분 안좋을 때 스트레스 감소에 좋음-"));

        mainAdapter.setArrayList(al);
        mainAdapter.notifyDataSetChanged();

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mainAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 2017.02.02 unregister broadcastReceiver
        unregisterReceiver(mGattUpdateReceiver);
        // 2017.02.02 safely unbindService
        if (Util.isRunningService(mContext, BluetoothLeService.class)) {
            unbindService(mServiceConn);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main_menu, menu);
//        MenuItem mi = menu.findItem(R.id.menu_bluetooth);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem button_bt = menu.findItem(R.id.menu_bluetooth);

        if (isConnectionCheck()) {
            button_bt.setIcon(getResources().getDrawable(R.drawable.img_blt_btn_act));
        } else {
            button_bt.setIcon(getResources().getDrawable(R.drawable.img_blt_btn_dis));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.menu_bluetooth) {
            Intent intent = new Intent(HealthCareMainActivity.this, DeviceAddActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
        } else if (id == R.id.nav_basic) {
            Intent intent = new Intent(HealthCareMainActivity.this, BasicSurvey1.class);
            startActivity(intent);
        } else if (id == R.id.nav_stress) {
            Intent intent = new Intent(HealthCareMainActivity.this, SurveyStress.class);
            startActivity(intent);
        } else if (id == R.id.nav_melancholy) {
            Intent intent = new Intent(HealthCareMainActivity.this, SurveyDepression.class);
            startActivity(intent);
        } else if (id == R.id.nav_grinch) {
            Intent intent = new Intent(HealthCareMainActivity.this, SurveyFatigue.class);
            startActivity(intent);
        } else if (id == R.id.nav_sleep) {
            //Intent intent = new Intent(HealthCareMainActivity.this, SurveySleep.class);
            //startActivity(intent);
        } else if (id == R.id.nav_comprehensive_evaluation) {
            Intent intent = new Intent(HealthCareMainActivity.this, SurveyTotalEval.class);
            startActivity(intent);
        } else if (id == R.id.nav_add_device) {
            Intent intent = new Intent(HealthCareMainActivity.this, DeviceAddActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // 2017.02.08 set ColorPicker Dialog (corner dialog)
    private void showColorPicker_dialog() {
        if (mSelectedColor != null) {
            mSelectedColor = null;
        }
        mColorPicker_Dialog = new ColorPickerDialog(HealthCareMainActivity.this, mColorPickerListener, mConfirmListener, mCancelListener);
        mColorPicker_Dialog.show();
    }

    private OnColorSelectedListener mColorPickerListener = new OnColorSelectedListener() {
        @Override
        public void onColorSelected(int i) {
            mSelectedColor = Integer.toHexString(i);
        }
    };
    private View.OnClickListener mCancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mColorPicker_Dialog.dismiss();
        }
    };
    private View.OnClickListener mConfirmListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mSelectedColor != null) {
                int color = (int) Long.parseLong(mSelectedColor, 16);
                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = (color >> 0) & 0xFF;

                String message = "color " + String.valueOf(r) + "," + String.valueOf(g) + "," + String.valueOf(b) + "/";
                if (isConnectionCheck()) {
//                    Toast.makeText(getApplicationContext(), "send color R: " + r + ", G: " + g + ", B: " + b, Toast.LENGTH_LONG).show();
                    sendMessageToDevice(message);
                    button_colorpicker.setBackgroundResource(R.drawable.img_color_btn_sel);
                    mSelectedColor = message;

                } else {
                    Toast.makeText(getApplicationContext(), "isBleContection is False", Toast.LENGTH_LONG).show();
                }
            } else {
//                Toast.makeText(getApplicationContext(), "mSelectedColor is NULL", Toast.LENGTH_LONG).show();
            }
            mColorPicker_Dialog.dismiss();
        }
    };

    private class TEST_SERVER extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = HttpPostSend.executeTest(params[0]);



            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                JSONObject json = JSONUtil.getJsObject(result);
                String jsonArray = JSONUtil.getJsString(json,"rsMapProfile");


                Log.d("errr","14 "+jsonArray);
                Toast.makeText(mContext,""+jsonArray,Toast.LENGTH_SHORT).show();
                Log.i("healthmax_test", json.toString());
                Log.i("healthmax_test", "S !!!");
//                try {
//                    String loginResult = json.getString("s_type");
//                    if (loginResult.equals("S")) {
//                        Log.i("HealthUp", "get main info success");
//                    } else {
//                        Log.i("HealthUp", "send main info fail");
//                        Toast.makeText(getBaseContext(), "get main info fail", Toast.LENGTH_LONG).show();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            } else {
                Log.e("healthmax_test", "F !!!");
            }
        }
    }
}
