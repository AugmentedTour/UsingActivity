package com.example.asatkee1.augementedimagetest;

import android.app.Activity;
import android.content.Context;

import uk.co.appoly.arcorelocation.LocationMarker;
import uk.co.appoly.arcorelocation.sensor.DeviceOrientation;
import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.Pose;
import com.google.ar.core.Session;
import com.google.ar.sceneform.ArSceneView;

import java.util.HashMap;
import java.util.Map;

import uk.co.appoly.arcorelocation.LocationScene;
import uk.co.appoly.arcorelocation.utils.LocationUtils;

public class CustomLocationScene extends LocationScene {

    public static Context mContext;
    public static Activity mActivity;
    private ArSceneView mSession;

    public static HashMap<String, Integer> lettersPlaced;

    public CustomLocationScene(Context mContext, Activity mActivity, ArSceneView mSession) {
        super(mContext, mActivity, mSession);

        lettersPlaced = new HashMap<>();
        lettersPlaced.put("L", 0);
        lettersPlaced.put("R", 0);
        lettersPlaced.put("H", 0);
    }



}