package com.example.app_dev.healthcare.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.app_dev.healthcare.R;
import com.example.app_dev.healthcare.adapter.DeviceAddAdapter;
import com.example.app_dev.healthcare.bluetooth.BleManager;
import com.example.app_dev.healthcare.utils.SharedPreferencesUtil;

import java.util.ArrayList;

public class DeviceAddActivity extends AppCompatActivity {

    private static final String TAG = "DeviceAddActivity";

    private ListView mBluetoothDeviceListView;

    // blaetooth
    private ActivityHandler mActivityHandler;
    private BluetoothAdapter mBluetoothAdapter;

    // Stops scanning after a pre-defined scan period.
    public static final long SCAN_PERIOD = 8 * 1000;
    private static final int REQUEST_ENABLE_BT = 1;

    private BleManager mBleManager;

    private DeviceAddAdapter mDeviceAddAdapter;
    private BluetoothManager mBluetoothManager;

    private ArrayList<BluetoothAdapter> mDevices = new ArrayList<BluetoothAdapter>();

    private Handler mHandler;
    private boolean mScanning;
    private ListView ble_lv;

    public static Context mContext;

    ImageButton mScanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContext = this;

        // 2017.01.26 set Bluetooth
        mHandler = new Handler();
        setBluetooth();

        mBluetoothDeviceListView = (ListView) findViewById(R.id.device_scan_lv);
        mBluetoothDeviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mScanning == true) {
                    Log.i("healthmax_test" , "mScanning == true");
                    scanLeDevice(false);
                }

                final BluetoothDevice item = mDeviceAddAdapter.getDevice(position);

                SharedPreferencesUtil.put(DeviceAddActivity.this, SharedPreferencesUtil.LAST_DEVICE_ADRESS, item.getAddress());
                // 2017.02.01 start bind service (context = main)
                ((HealthCareMainActivity) HealthCareMainActivity.mContext).startBindService(item.getAddress());
//                finish();
            }
        });
        mScanButton = (ImageButton) findViewById(R.id.device_scan_ib);
        mScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                doDiscovery();

                if (mDeviceAddAdapter != null) {
                    mDeviceAddAdapter.clear();
                    mDeviceAddAdapter.notifyDataSetChanged();
                }
                mDevices.clear();
                scanLeDevice(true);
            }
        });

        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
        // Initializes list view adapter.
        mDeviceAddAdapter = new DeviceAddAdapter(DeviceAddActivity.this);
        mBluetoothDeviceListView.setAdapter(mDeviceAddAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setBluetooth() {
        mBluetoothManager = (BluetoothManager) getSystemService(this.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        invalidateOptionsMenu();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanLeDevice(false);
        mDeviceAddAdapter.clear();
    }

    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery() {
        if (true) Log.d(TAG, "doDiscovery()");

        // Indicate scanning in the title
        setProgressBarIndeterminateVisibility(true);
        setTitle("scanning..");

        // Turn on sub-title for new devices
//        findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

        // Empty cache
        mDevices.clear();

        // If we're already discovering, stop it
        if (mBleManager.getState() == BleManager.STATE_SCANNING) {
            mBleManager.scanLeDevice(false);
        }

        // Request discover from BluetoothAdapter
        mBleManager.scanLeDevice(true);

        // Stops scanning after a pre-defined scan period.
        mActivityHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopDiscovery();
            }
        }, SCAN_PERIOD);
    }

    /**
     * Stop device discover
     */
    private void stopDiscovery() {
        // Indicate scanning in the title
        setProgressBarIndeterminateVisibility(false);
        setTitle("Bluetooth");
        // Show scan button
        mScanButton.setVisibility(View.VISIBLE);
        mBleManager.scanLeDevice(false);
    }


    /**
     * BLE scan callback
     */
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                                if (!checkDuplicated(device)) {
                                    if (device.getName() != null && device.getName().equals("HMSoft")) {
                                        mDeviceAddAdapter.addDevice(device);
                                        mDeviceAddAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                    });
                }
            };


    public class ActivityHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            }
            super.handleMessage(msg);
        }
    }

    /**
     * Check if it's already cached
     */
    private boolean checkDuplicated(BluetoothDevice device) {
        for (BluetoothAdapter dvc : mDevices) {
            if (device.getAddress().equalsIgnoreCase(dvc.getAddress())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
