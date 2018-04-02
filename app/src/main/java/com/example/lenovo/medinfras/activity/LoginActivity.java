package com.example.lenovo.medinfras.activity;

import android.app.KeyguardManager;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.medinfras.MainActivity;
import com.example.lenovo.medinfras.R;
import com.example.lenovo.medinfras.javaclass.FingerprintHandler;
import com.goodiebag.pinview.Pinview;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.pinView)
    Pinview pinView;
    @BindView(R.id.txtPinError)
    TextView txtPinError;
    @BindView(R.id.progressBarId)
    ContentLoadingProgressBar progressBarId;
    @BindView(R.id.txtViewPinLogin)
    TextView txtViewPinLogin;
    @BindView(R.id.txtViewFingerprintAuth)
    TextView txtViewFingerprintAuth;
    @BindView(R.id.imgViewFingerprint)
    ImageView imgViewFingerprint;
    @BindView(R.id.btnPinLogin)
    Button btnPinLogin;
    @BindView(R.id.btnFingerprintAuth)
    Button btnFingerprintAuth;

    private KeyStore keyStore;
    private static final String KEY_NAME = "fingerprint";
    private Cipher cipher;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        btnPinLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtViewPinLogin.setVisibility(View.VISIBLE);
                pinView.setVisibility(View.VISIBLE);
                txtViewFingerprintAuth.setVisibility(View.GONE);
                imgViewFingerprint.setVisibility(View.GONE);
                btnFingerprintAuth.setVisibility(View.VISIBLE);
                btnPinLogin.setVisibility(View.GONE);
            }
        });

        btnFingerprintAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtViewPinLogin.setVisibility(View.GONE);
                pinView.setVisibility(View.GONE);
                txtViewFingerprintAuth.setVisibility(View.VISIBLE);
                imgViewFingerprint.setVisibility(View.VISIBLE);
                btnFingerprintAuth.setVisibility(View.GONE);
                btnPinLogin.setVisibility(View.VISIBLE);
            }
        });

        //Login Using Pin Code
        Pinview pinview = (Pinview) findViewById(R.id.pinView);
        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
                if (pinview.getValue().equals("0000")) {
                    Intent toMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                    txtPinError.setVisibility(View.GONE);
                    progressBarId.setVisibility(View.VISIBLE);
                    startActivity(toMainActivity);
                    Toast.makeText(LoginActivity.this, "Pin Code Authorized", Toast.LENGTH_SHORT)
                            .show();
                    finish();
                } else {
                    txtPinError.setVisibility(View.VISIBLE);
                }
            }
        });

        //Login using fingerprint authentication
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService
                (FINGERPRINT_SERVICE);


        //Check whether the device has fingerprint sensor
        if (!fingerprintManager.isHardwareDetected()) {
            Toast.makeText(this, "Fingerprint authentication not enabled", Toast.LENGTH_SHORT)
                    .show();
        } else {

            //Check if the user has registered at least one fingerprint
            if (!fingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(this, "You haven't registered any fingerprint to our settings",
                        Toast
                                .LENGTH_SHORT)
                        .show();
            } else {

                //Check if the login activity is secure before authentication
                if (!keyguardManager.isKeyguardSecure()) {
                    Toast.makeText(this, "Lock screen security not enabled", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    generateKey();

                    if (cipherInit()) {
                        FingerprintManager.CryptoObject cryptoObject = new FingerprintManager
                                .CryptoObject(cipher);
                        FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
                        fingerprintHandler.startAuthentication(fingerprintManager, cryptoObject);
                    }
                }
            }
        }

    }

    //Initialize cipher
    private boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties
                    .BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (CertificateException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
            return false;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return false;
        } catch (KeyStoreException e) {
            e.printStackTrace();
            return false;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        KeyGenerator keyGenerator = null;

        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,
                    "AndroidKeyStore");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties
                    .PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties
                    .BLOCK_MODE_CBC).setUserAuthenticationRequired(true).setEncryptionPaddings
                    (KeyProperties.ENCRYPTION_PADDING_PKCS7).build());
            keyGenerator.generateKey();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
        Process.killProcess(Process.myPid());
        System.exit(1);
    }
}
