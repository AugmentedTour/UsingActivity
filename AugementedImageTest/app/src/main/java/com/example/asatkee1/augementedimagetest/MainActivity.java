package com.example.asatkee1.augementedimagetest;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.ArCoreApk;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import uk.co.appoly.arcorelocation.LocationMarker;
import uk.co.appoly.arcorelocation.LocationScene;
import uk.co.appoly.arcorelocation.rendering.LocationNode;
import uk.co.appoly.arcorelocation.rendering.LocationNodeRender;
import uk.co.appoly.arcorelocation.utils.ARLocationPermissionHelper;

public class MainActivity extends AppCompatActivity {

    private ArFragment arFragment;
    private boolean shouldAddModel = true;
    private LocationScene locationScene;
    private Renderable andyRenderable;
    private ViewRenderable exampleLayoutRenderable;
    private ArSceneView arSceneView;
    private boolean installRequested;
    private boolean hasFinishedLoading = false;

    private LocationMarker shown = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //keep it in portrait mode if user wants to turn phone
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
        arSceneView = arFragment.getArSceneView();
        arFragment.getPlaneDiscoveryController().hide();
        arFragment.getPlaneDiscoveryController().setInstructionView(null);

        CompletableFuture<ModelRenderable> andy = ModelRenderable.builder()
                .setSource(this, Uri.parse("Airplane.sfb"))
                .build();

        CompletableFuture<ViewRenderable> exampleLayout =
                ViewRenderable.builder()
                        .setView(this, R.layout.example_layout)
                        .build();

        CompletableFuture.allOf(andy, exampleLayout )
                .handle(
                        (notUsed, throwable) ->
                        {
                            if (throwable != null) {
                                return null;
                            }
                            try {
                                andyRenderable = andy.get();
                                Toast.makeText(this, "I see the marker", Toast.LENGTH_SHORT).show();
                                exampleLayoutRenderable = exampleLayout.get();
                                Toast.makeText(this, "I see layout marker", Toast.LENGTH_SHORT).show();
                                hasFinishedLoading = true;

                            } catch (InterruptedException | ExecutionException ex) {

                            }
                            return null;
                        });


        arSceneView.getScene().addOnUpdateListener(
                frameTime -> {
                    if(!hasFinishedLoading){
                        return;
                    }
                    Frame frame = arFragment.getArSceneView().getArFrame();
                    if (locationScene == null) {
                        locationScene = new LocationScene(this, arFragment.getArSceneView());
                        locationScene.setAnchorRefreshInterval(500);

                        //////////////
//                LocationMarker locationMarkerL1 =
//                        new LocationMarker(
//                                -122.148925,
//                                47.586036,
//                                getAndy());
//                LocationMarker locationMarkerL2 =
//                        new LocationMarker(
//                                -122.148925,
//                                47.586015,
//                                getAndy());
//                LocationMarker locationMarkerL3 =
//                        new LocationMarker(
//                                -122.148925,
//                                47.586005,
//                                getAndy());
//
//                LocationMarker locationMarkerL4 =
//                        new LocationMarker(
//                                -122.148925,
//                                47.585995,
//                                getAndy());
//                List<LocationMarker> markersL =
//                        new ArrayList<>(4);
//                markersL.add(locationMarkerL1);
//                markersL.add(locationMarkerL2);
//                markersL.add(locationMarkerL3);
//                markersL.add(locationMarkerL4);



                        double startLLat = 47.586036;
                        double lastLLat = 47.585510;
                        List<LocationMarker> markersL =
                                new ArrayList<>(4);

                        for(double i = 47.586036; i > 47.585510; i -= 0.00001){
                            LocationMarker locationMarkerL =
                                    new LocationMarker(

                                            -122.148925,
                                            i,
                                            getAndy());
                            markersL.add(locationMarkerL);
                        }

                        //corner's markers
                        LocationMarker locationMarkerL1 =
                                new LocationMarker(
                                        -122.148587,
                                        47.586003,
                                        getAndy());

                        LocationMarker locationMarkerL2 =
                                new LocationMarker(
                                        -122.149065,
                                        47.586041,
                                        getAndy());

                        LocationMarker locationMarkerL3 =
                                new LocationMarker(
                                        -122.148592,
                                        47.585557,
                                        getAndy());

                        LocationMarker locationMarkerL4 =
                                new LocationMarker(
                                        -122.149066,
                                        47.585518,
                                        getAndy());

                        markersL.add(locationMarkerL1);
                        markersL.add(locationMarkerL2);
                        markersL.add(locationMarkerL3);
                        markersL.add(locationMarkerL4);

                        new SingletonGroup(locationScene, arFragment, markersL, "Group1");

                        locationScene.mLocationMarkers.addAll(markersL);

                        //////////////
                        LocationMarker locationMarkerR1 =
                                new LocationMarker(
                                        -122.14933,
                                        47.586128,
                                        getExampleView());
                        LocationMarker locationMarkerR2 =
                                new LocationMarker(
                                        -122.14937,
                                        47.586128,
                                        getExampleView());
                        LocationMarker locationMarkerR3 =
                                new LocationMarker(
                                        -122.1494,
                                        47.586128,
                                        getExampleView());
                        //////////////

                        List<LocationMarker> markersR =
                                new ArrayList<>(2);
//
//                double startRLong = -122.14933;
//                double lastRLong = -122.15007;
//                for(double j = -122.14933; j > lastRLong; j-= 0.00001){
//                    LocationMarker locationMarkerR =
//                        new LocationMarker(
//                                j,
//                                47.586128,
//                                getExampleView());
//                    markersR.add(locationMarkerR);
//                }

                        markersR.add(locationMarkerR1);
                        markersR.add(locationMarkerR2);
                        markersR.add(locationMarkerR3);

                        new SingletonGroup(locationScene, arFragment, markersR, "Group2");

                        locationScene.mLocationMarkers.addAll(markersR);

//                LocationMarker layoutLocationMarkerL1 = new LocationMarker(
//                        -122.148871,
//                        47.585908,
//                        getExampleView()
//                );
//                layoutLocationMarkerL1.setRenderEvent(new LocationNodeRender() {
//                    @Override
//                    public void render(LocationNode node) {
//                        View eView = exampleLayoutRenderable.getView();
//                        TextView distanceTextView = eView.findViewById(R.id.textView2);
//                        distanceTextView.setText(node.getDistance() + "M");
//                    }
//                });

//
//                locationScene.mLocationMarkers.add(
//                        new LocationMarker(
//                                -122.149160,
//                                47.585941,
//                                getAndy()));
//
//                locationScene.mLocationMarkers.add(
//                        new LocationMarker(
//                                -122.149200,
//                                47.585949,
//                                getAndy()));
//                locationScene.mLocationMarkers.add(
//                        new LocationMarker(
//                                -122.149190,
//                                47.585942,
//                                getAndy()));
//
//                //change lat and long
//                locationScene.mLocationMarkers.add(
//                        new LocationMarker(
//                                -122.148936,
//                                47.585708,
//                                getAndy()));
                        //locationScene.mLocationMarkers.add(layoutLocationMarkerL1);

                    }



                    if (locationScene != null) {
                        locationScene.processFrame(frame);

                    }

                });
        ARLocationPermissionHelper.requestPermission(this);

    }

    /**
     * Make sure we call locationScene.resume();
     */
    @Override
    protected void onResume() {
        super.onResume();

        if (locationScene != null) {
            locationScene.resume();
        }

        if (arSceneView.getSession() == null) {
            // If the session wasn't created yet, don't resume rendering.
            // This can happen if ARCore needs to be updated or permissions are not granted yet.
            try {
                Session session = createArSession(this, installRequested);
                if (session == null) {
                    installRequested = ARLocationPermissionHelper.hasPermission(this);
                    return;
                } else {
                    arSceneView.setupSession(session);
                }
            } catch (UnavailableException e) {
                handleSessionException(this, e);
            }
        }

        try {
            arSceneView.resume();
        } catch (CameraNotAvailableException ex) {
            Log.d("Camera", "unable to get cam");
            finish();
            return;
        }

        if (arSceneView.getSession() != null) {

        }
    }

    public static void handleSessionException(
            Activity activity, UnavailableException sessionException) {

        String message;
        if (sessionException instanceof UnavailableArcoreNotInstalledException) {
            message = "Please install ARCore";
        } else if (sessionException instanceof UnavailableApkTooOldException) {
            message = "Please update ARCore";
        } else if (sessionException instanceof UnavailableSdkTooOldException) {
            message = "Please update this app";
        } else if (sessionException instanceof UnavailableDeviceNotCompatibleException) {
            message = "This device does not support AR";
        } else {
            message = "Failed to create AR session";
            Log.e("this" ,"Exception: " + sessionException);
        }
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    public static Session createArSession(Activity activity, boolean installRequested)
            throws UnavailableException {
        Session session = null;
        // if we have the camera permission, create the session
        if (ARLocationPermissionHelper.hasPermission(activity)) {
            switch (ArCoreApk.getInstance().requestInstall(activity, !installRequested)) {
                case INSTALL_REQUESTED:
                    return null;
                case INSTALLED:
                    break;
            }
            session = new Session(activity);
            // IMPORTANT!!!  ArSceneView needs to use the non-blocking update mode.
            Config config = new Config(session);
            config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
            session.configure(config);
        }
        return session;
    }


    /**
     * Make sure we call locationScene.pause();
     */

    private void onUpdateFrame(FrameTime frameTim) {
        Frame frame = arFragment.getArSceneView().getArFrame();

        CompletableFuture<ModelRenderable> andy = ModelRenderable.builder()
                .setSource(this, Uri.parse("Airplane.sfb"))
                .build();
        CompletableFuture.allOf(andy)
                .handle(
                        (notUsed, throwable) ->
                        {
                            if (throwable != null) {
                                return null;
                            }
                            try {
                                andyRenderable = andy.get();
                                Toast.makeText(this, "I see the marker", Toast.LENGTH_SHORT).show();

                            } catch (InterruptedException | ExecutionException ex) {

                            }
                            return null;
                        });
    }
    private Node getAndy() {
        Node base = new Node();
        base.setRenderable(andyRenderable);
        Context c = this;
        Toast.makeText(c, "In get Andy method", Toast.LENGTH_LONG).show();
        base.setOnTapListener((v, event) -> {
            Toast.makeText(
                    c, "Location marker is touched.", Toast.LENGTH_LONG)
                    .show();
        });
        return base;
    }

    private Node getExampleView() {
        Node base = new Node();
        base.setRenderable(exampleLayoutRenderable);
        Context c = this;
        // Add  listeners etc here
        View eView = exampleLayoutRenderable.getView();
        eView.setOnTouchListener((v, event) -> {
            Toast.makeText(
                    c, "Location marker touched.", Toast.LENGTH_LONG)
                    .show();
            return false;
        });

        return base;
    }

    private void placeObject(ArFragment fragment, Anchor anchor, Uri model){
        ModelRenderable.builder()
                .setSource(fragment.getContext(), model)
                .build()
                .thenAccept(renderable -> addNodeToScene(fragment, anchor, renderable))
                .exceptionally((throwable -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(throwable.getMessage())
                            .setTitle("Error!");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return null;
                }));
    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] results) {
        if (!ARLocationPermissionHelper.hasPermission(this)) {
            if (!ARLocationPermissionHelper.shouldShowRequestPermissionRationale(this)) {
                // Permission denied with checking "Do not ask again".
                ARLocationPermissionHelper.launchPermissionSettings(this);
            } else {
                Toast.makeText(
                        this, "Camera permission is needed to run this application", Toast.LENGTH_LONG)
                        .show();
            }
            finish();
        }
    }



    private void addNodeToScene(ArFragment arFragment, Anchor anchor, Renderable renderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());



        node.setRenderable(renderable);
        node.setParent(anchorNode);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        node.select();
    }


    public boolean setupAugmentedImageDb(Config config, Session session){
        AugmentedImageDatabase augmentedImageDatabase;

        ArrayList <Bitmap> bitmap = new ArrayList<Bitmap>();

        for(int i = 0; i < loadAugmentedImage().size(); i++){
            bitmap.add(loadAugmentedImage().get(i));
        }

        if(bitmap.isEmpty()){
            return  false;
        }

        augmentedImageDatabase = new AugmentedImageDatabase(session);
        augmentedImageDatabase.addImage("H_letter", bitmap.get(0));
        augmentedImageDatabase.addImage("H_letter_1", bitmap.get(1));
        // augmentedImageDatabase.addImage("Housing", bitmap.get(2));
        config.setAugmentedImageDatabase(augmentedImageDatabase);
        return true;
    }


    private ArrayList<Bitmap> loadAugmentedImage(){
        ArrayList <Bitmap> bitmaps = new ArrayList<Bitmap>();
        try (InputStream is = getAssets().open("H_letter.jpg")){
            bitmaps.add(BitmapFactory.decodeStream(is));
        }
        catch (IOException e){
            Log.e("ImageLoad", "IO Exception while loading", e);
        }
        try (InputStream is = getAssets().open("H_letter_1.jpg")){
            bitmaps.add(BitmapFactory.decodeStream(is));
        }
        catch (IOException e){
            Log.e("ImageLoad", "IO Exception while loading", e);
        }
        try (InputStream is = getAssets().open("Housing.jpg")){
            bitmaps.add(BitmapFactory.decodeStream(is));
        }
        catch (IOException e){
            Log.e("ImageLoad", "IO Exception while loading", e);
        }
        if(!bitmaps.isEmpty()){
            return bitmaps;
        }
        return null;
    }
}
