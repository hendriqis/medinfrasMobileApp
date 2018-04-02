package com.example.lenovo.medinfras.javaclass;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.example.lenovo.medinfras.MainActivity;

/**
 * Created by Lenovo on 3/28/2018.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback{

    private static Context context;

    public FingerprintHandler(Context context) {
        this.context = context;
    }

    //this method is responsible for starting the fingerprint authentication process
    public void startAuthentication(FingerprintManager fingerprintManager,
                                    FingerprintManager.CryptoObject cryptoObject) {

        //CancellationSignal is used whenever your app no longer process user input. It could
        // prevent fingerprint authentication failed in device lock screen / any other apps that
        // using fingerprint authentication
        CancellationSignal cancellationSignal = new CancellationSignal();
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED)
            return;
        fingerprintManager.authenticate(cryptoObject, cancellationSignal,0, this, null);
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        Toast.makeText(context, "Authentication Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        context.startActivity(new Intent(context, MainActivity.class));
        Toast.makeText(context, "Fingerprint Approved. Welcome to Medinfras", Toast
                .LENGTH_SHORT).show();
    }
}
