package com.example.armando.mibluetooth;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int CODIGO_SOLICITUD_PERMISO = 1;
    private static final int CODIGO_SOLICITUD_HABILITAR_BLUETOOTH = 0;
    private Context context;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context= getApplicationContext();
        activity = this;
    }

    public void onHabilitarBluetooth(View v)
    {
        solicitarPermiso();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null)
        {
            Toast.makeText(context,"Tu dispositivo no tiene Bluetooth", Toast.LENGTH_LONG).show();

        }
        if(!mBluetoothAdapter.isEnabled())
        {
            Intent intentHabilitarBlue= new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intentHabilitarBlue,CODIGO_SOLICITUD_HABILITAR_BLUETOOTH);
        }

    }

    public boolean VerificarEstadoPermiso(){
        int resultado = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH);
        if( resultado == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        else {
            return false;
        }
    }

    public void solicitarPermiso()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.BLUETOOTH))
        {
            Toast.makeText(getApplicationContext(),"El permiso fue otorgado, si deseas desactivarlo ve a los ajustes de la aplicación",Toast.LENGTH_LONG).show();
        }
        else
        {
            ActivityCompat.requestPermissions(activity,new String[] {Manifest.permission.BLUETOOTH},CODIGO_SOLICITUD_PERMISO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case CODIGO_SOLICITUD_PERMISO:
                if(VerificarEstadoPermiso())
                {
                    Toast.makeText(getApplicationContext(),"Ya está activo el permiso",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"El permiso no está activo ",Toast.LENGTH_LONG).show();
                }

                break;


        }
    }
}
