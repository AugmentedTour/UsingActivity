package com.example.asatkee1.augementedimagetest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class LaunchPage extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_page);
    }


    public void start(View view) {
        PermissionHandler handler = new PermissionHandler();

        boolean hasPermission = handler.checkPermissions(this);
        Log.d("DEBUG", "Has Permission? " + hasPermission);

        if(hasPermission) {
            //start AR activity
            Intent startMain = new Intent(this, MainActivity.class);
            startActivity(startMain);
        } else if (!hasPermission) {
            handler.askPermissions(this, this);

            //retry permissions
            hasPermission = handler.checkPermissions(this);
            if(hasPermission) {
                //start AR activity
                Intent startMain = new Intent(this, MainActivity.class);
                startActivity(startMain);
            }
        }
    }


}
