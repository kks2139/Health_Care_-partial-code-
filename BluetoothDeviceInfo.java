package com.example.app_dev.healthcare.item;

/**
 * Created by app_dev on 2017-01-20.
 */

public class BluetoothDeviceInfo {
    private String mBluetoothDeviceName;
    private String mBluetoothDeviceAdress;

    public BluetoothDeviceInfo(){
    }

    public BluetoothDeviceInfo(String name, String adress){
        mBluetoothDeviceName = name;
        mBluetoothDeviceAdress = adress;
    }

    public String getmBluetoothDeviceAdress() {
        return mBluetoothDeviceAdress;
    }

    public void setmBluetoothDeviceAdress(String mBluetoothDeviceAdress) {
        this.mBluetoothDeviceAdress = mBluetoothDeviceAdress;
    }

    public String getmBluetoothDeviceName() {
        return mBluetoothDeviceName;
    }

    public void setmBluetoothDeviceName(String mBluetoothDeviceName) {
        this.mBluetoothDeviceName = mBluetoothDeviceName;
    }

}
