package com.example.app_dev.healthcare.adapter;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.app_dev.healthcare.R;
import com.example.app_dev.healthcare.item.BluetoothDeviceInfo;
import com.example.app_dev.healthcare.utils.Logs;

import java.util.ArrayList;

/**
 * Created by app_dev on 2017-01-20.
 */
public class DeviceAddAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<BluetoothDevice> mArrayList = new ArrayList<BluetoothDevice>();

    public DeviceAddAdapter(Context context) {
        mContext = context;
    }

//    public void setArrayList(ArrayList<BluetoothDeviceInfo> mArrayList) {
//        this.mArrayList = mArrayList;
//    }

    public void setArrayList(BluetoothDevice device) {
        mArrayList.add(device);
    }

    public void addDevice(BluetoothDevice device) {
        if (!mArrayList.contains(device)) {
            mArrayList.add(device);
        }
    }

    public BluetoothDevice getDevice(int pos){
        return mArrayList.get(pos);
    }

    public void clear() {
        mArrayList.clear();
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Holder holer = null;

        if (view == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            view = inflater.inflate(R.layout.item_bluetooth_device, parent, false);

            holer = new Holder();
            holer.contentTextView = (TextView) view.findViewById(R.id.item_bluetooth_device_name_tv);

            view.setTag(holer);
        } else {
            holer = (Holder) view.getTag();
        }

//        BluetoothDeviceInfo mBleDeviceInfo_item = mArrayList.get(position);
        BluetoothDevice mBleDeviceInfo_item = mArrayList.get(position);

        if (mBleDeviceInfo_item.getName() != null && mBleDeviceInfo_item.getName().length() > 0) {
                holer.contentTextView.setText(mBleDeviceInfo_item.getName() + "  " + mBleDeviceInfo_item.getAddress());
        }

        return view;
    }



    static class Holder {
        TextView contentTextView;
    }
}
