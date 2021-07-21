package com.ebookfrenzy.biometricdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import android.Manifest
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.CancellationSignal
import android.view.View
import android.content.DialogInterface

class MainActivity : AppCompatActivity() {

    private var cancellationSignal: CancellationSignal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkBiometricSupport()
    }

    private fun checkBiometricSupport(): Boolean {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE)
                as KeyguardManager

        if (!keyguardManager.isKeyguardSecure) {
            notifyUser("Lock screen security not enabled in Settings")
            return false
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.USE_BIOMETRIC) !=
            PackageManager.PERMISSION_GRANTED) {
            notifyUser("Fingerprint authentication permission not enabled")
            return false
        }

        return if (packageManager.hasSystemFeature(
                PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true
    }

    private fun notifyUser(message: String) {
        Toast.makeText(this,
            message,
            Toast.LENGTH_LONG).show()
    }

    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int,
                                               errString: CharSequence) {
                notifyUser("Authentication error: $errString")
                super.onAuthenticationError(errorCode, errString)
            }

            override fun onAuthenticationHelp(helpCode: Int,
                                              helpString: CharSequence) {
                super.onAuthenticationHelp(helpCode, helpString)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
            }

            override fun onAuthenticationSucceeded(result:
                                                   BiometricPrompt.AuthenticationResult) {
                notifyUser("Authentication Succeeded")
                super.onAuthenticationSucceeded(result)
            }
        }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("Cancelled via signal")
        }
        return cancellationSignal as CancellationSignal
    }

    fun authenticateUser(view: View) {
        val biometricPrompt = BiometricPrompt.Builder(this)
            .setTitle("Biometric Demo")
            .setSubtitle("Authentication is required to continue")
            .setDescription("This app uses biometric authentication to protect your data.")
            .setNegativeButton("Cancel", this.mainExecutor,
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        notifyUser("Authentication cancelled") }).build()
                    biometricPrompt.authenticate(getCancellationSignal(), mainExecutor,
                authenticationCallback)
    }
}