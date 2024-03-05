package com.palash.remoteconfig

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Get the Remote Config singleton object
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 60
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        //Set in-app default parameter values
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        //Fetch and activate values
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val msg = remoteConfig.getString("login_submit_text")
                    val myMsg = findViewById<TextView>(R.id.txt)
                    myMsg.text = msg
                }
                //displayWelcomeMessage()
            }
    }
}