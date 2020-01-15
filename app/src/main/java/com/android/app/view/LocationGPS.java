package com.android.app.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.android.app.utils.PermissionUtils;

public class LocationGPS implements LocationListener {
    public static final String TAG = LocationGPS.class.getSimpleName();

    private Activity activity;
    private Context context;
    private LocationManager locationManager;
    private Location currentLocation;

    private Actions actions;

    private String provider = LocationManager.GPS_PROVIDER;

    private static final long MIN_DISTANCE_FOR_UPDATE = 1;
    private static final long MIN_TIME_FOR_UPDATE = 1000;

    public LocationGPS(Activity activity, Context context, Actions actions) {
        this.activity = activity;
        this.context = context;
        this.actions = actions;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void solicitarAcessoGPS() {
        if (PermissionUtils.INSTANCE.requestPermissionLocation(activity)) {
            iniciarLocationManager();
        }
//        if (PermissionUtils.INSTANCE.checkPermissionLocation(context)) {//Verificar sem tem permissoes de uso do GPS
//            iniciarLocationManager();
//        } else {//Solicitar permissoes do uso do GPS
//            PermissionUtils.INSTANCE.requestPermissionLocation(activity);//Solicita as permissoes ao usuario
//        }
    }

    public void iniciarLocationManager() {
        if (verificarGPSLigado()) {
            requestLocationUpdate();
        } else {
            solicitarLigarGPS();
        }
    }

    @SuppressLint("MissingPermission")
    public void requestLocationUpdate() {
        locationManager.requestLocationUpdates(provider, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
        Location lastLocation = locationManager.getLastKnownLocation(provider);
        if (lastLocation != null) {
            currentLocation = lastLocation;
            actions.onLocalizacaoDefinida();
        }
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void solicitarLigarGPS() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Ligue o GPS");
        alertDialog.setMessage("Seu GPS está desligado. Por favor ligue o GPS nas configurações.");
        alertDialog.setPositiveButton("Configurações GPS", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                actions.onCanceladoLigarGPS();
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!verificarGPSLigado()) {
                    actions.onCanceladoLigarGPS();
                }
            }
        });
        alert.show();
    }

    public void pararLocationManager() {
        if (PermissionUtils.INSTANCE.checkPermissionLocation(context)) {
            if (locationManager != null) {
                //locationManager.removeUpdates(this);
            } else {
                actions.onCanceladoLigarGPS();
            }
        }
//        if (PermissoesUtils.resquestLocationPermission(context)) {
//            if (locationManager != null) {
//                locationManager.removeUpdates(this);
//            }
//            actions.onCanceladoLigarGPS();
//        }
    }

    public boolean verificarGPSLigado() {//Verificar se o GPS do dispositivo esta ligado
        //LocationManager service = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(provider);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "onLocationChanged");
        currentLocation = location;
        actions.onLocationChanged(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i(TAG, "onStatusChanged status " + status + " " + provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(TAG, "onProviderEnabled");
        actions.onLocalizacaoDefinida();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(TAG, "onProviderDisabled");
        actions.onLocalizacaoRemovida();
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public interface Actions {
        void onLocationChanged(Location location);
        void onLocalizacaoDefinida();
        void onLocalizacaoRemovida();
        void onCanceladoLigarGPS();
    }
}
