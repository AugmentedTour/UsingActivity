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

///////////////////////////////////////////////////////////////////////////////////////
                //L building
                double startLLat = 47.586036;
                double lastLLat = 47.585510;
                List<LocationMarker> markersL =
                        new ArrayList<>(10);

                for(double i = startLLat; i > lastLLat; i -= 0.00001){
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

                new SingletonGroup(locationScene, arFragment, markersL, "Group1", 70);

                locationScene.mLocationMarkers.addAll(markersL);

/////////////////////////////////////////////////////////////////////////////
                //R building
                List<LocationMarker> markersR =
                        new ArrayList<>(2);

                double startRLong = -122.14933;
                double lastRLong = -122.15007;
                for(double j = startRLong; j > lastRLong; j-= 0.00001){
                    LocationMarker locationMarkerR =
                        new LocationMarker(
                                j,
                                47.586128,
                                getExampleView());
                    markersR.add(locationMarkerR);
                }


                double startRLat = 47.58625;
                double lastRLat = 47.58555;
                for(double j = startRLat; j > lastRLat; j-= 0.00001){
                    LocationMarker locationMarkerR =
                            new LocationMarker(
                                    -122.149989,
                                    j,
                                    getExampleView());
                    markersR.add(locationMarkerR);
                }


                //corner's markers
                LocationMarker locationMarkerR1 =
                        new LocationMarker(
                                -122.149373,
                                47.586235,
                                getExampleView());
                LocationMarker locationMarkerR2 =
                        new LocationMarker(
                                -122.149331,
                                47.586042,
                                getExampleView());
                LocationMarker locationMarkerR3 =
                        new LocationMarker(
                                -122.150068,
                                47.586255,
                                getExampleView());
                LocationMarker locationMarkerR4 =
                        new LocationMarker(
                                -122.150101,
                                47.585554,
                                getExampleView());
                LocationMarker locationMarkerR5 =
                        new LocationMarker(
                                -122.149890,
                                47.585549,
                                getExampleView());


                markersR.add(locationMarkerR1);
                markersR.add(locationMarkerR2);
                markersR.add(locationMarkerR3);
                markersR.add(locationMarkerR4);
                markersR.add(locationMarkerR5);

                new SingletonGroup(locationScene, arFragment, markersR, "Group2", 70);
                locationScene.mLocationMarkers.addAll(markersR);


////////////////////////////////////////////////////////////////////////////////
//              //B building
                List<LocationMarker> markersB =
                        new ArrayList<>(2);

                double startBLong = -122.14774;
                double lastBLong = -122.14903;
                for(double j = startRLong; j > lastRLong; j-= 0.00001){
                    LocationMarker locationMarkerB =
                            new LocationMarker(
                                    j,
                                    47.58493,
                                    getExampleView());
                    markersB.add(locationMarkerB);
                }

                double startBLat = 47.58542;
                double lastBLat = 47.58447;
                for(double j = startBLat; j > lastBLat; j-= 0.00001){
                    LocationMarker locationMarkerB =
                            new LocationMarker(
                                    -122.14878,
                                    j,
                                    getExampleView());
                    markersB.add(locationMarkerB);
                }


                //corner's markers
                LocationMarker locationMarkerB1 =
                        new LocationMarker(
                                -122.149036,
                                47.585419,
                                getExampleView());
                LocationMarker locationMarkerB2 =
                        new LocationMarker(
                                -122.148518,
                                47.585419,
                                getExampleView());
                LocationMarker locationMarkerB3 =
                        new LocationMarker(
                                -122.147756,
                                47.585060,
                                getExampleView());
                LocationMarker locationMarkerB4 =
                        new LocationMarker(
                                -122.147761,
                                47.584800,
                                getExampleView());
                LocationMarker locationMarkerB5 =
                        new LocationMarker(
                                -122.148851,
                                47.584482,
                                getExampleView());
                LocationMarker locationMarkerB6 =
                        new LocationMarker(
                                -122.149205,
                                47.584488,
                                getExampleView());

                markersB.add(locationMarkerB1);
                markersB.add(locationMarkerB2);
                markersB.add(locationMarkerB3);
                markersB.add(locationMarkerB4);
                markersB.add(locationMarkerB5);
                markersB.add(locationMarkerB6);

                new SingletonGroup(locationScene, arFragment, markersB, "Group3", 70);

                locationScene.mLocationMarkers.addAll(markersB);


////////////////////////////////////////////////////////////////////////////////
//              //S building
                    List<LocationMarker> markersS =
                            new ArrayList<>(10);

                    double startSLat = 47.58509;
                    double lastSLat = 47.58474;
                    for(double j = startSLat; j > lastSLat; j-= 0.00001){
                        LocationMarker locationMarkerS =
                                new LocationMarker(
                                        -122.14726,
                                        j,
                                        getAndy());
                        markersS.add(locationMarkerS);
                    }

                    //corner's markers
                    LocationMarker locationMarkerS1 =
                            new LocationMarker(
                                    -122.147436,
                                    47.585085,
                                    getAndy());
                    LocationMarker locationMarkerS2 =
                            new LocationMarker(
                                    -122.147451,
                                    47.584832,
                                    getAndy());
                    LocationMarker locationMarkerS3 =
                            new LocationMarker(
                                    -122.147281,
                                    47.584746,
                                    getAndy());
                    LocationMarker locationMarkerS4 =
                            new LocationMarker(
                                    -122.147081,
                                    47.584739,
                                    getAndy());
                    LocationMarker locationMarkerS5 =
                            new LocationMarker(
                                    -122.147166,
                                    47.585060,
                                    getAndy());

                    markersS.add(locationMarkerS1);
                    markersS.add(locationMarkerS2);
                    markersS.add(locationMarkerS3);
                    markersS.add(locationMarkerS4);
                    markersS.add(locationMarkerS5);

                    new SingletonGroup(locationScene, arFragment, markersS, "Group4", 70);
                    locationScene.mLocationMarkers.addAll(markersS);

////////////////////////////////////////////////////////////////////////////////
//              //A building
                List<LocationMarker> markersA =
                        new ArrayList<>(10);

                double startALat = 47.58430;
                double lastALat = 47.58318;
                for(double j = startALat; j > lastALat; j-= 0.00001){
                    LocationMarker locationMarkerA =
                            new LocationMarker(
                                    -122.14882,
                                    j,
                                    getAndy());
                    markersA.add(locationMarkerA);
                }

                double start2ALat = 47.58384;
                double last2ALat = 47.58313;
                for(double j = start2ALat; j > last2ALat; j-= 0.00001){
                    LocationMarker locationMarkerA =
                            new LocationMarker(
                                    -122.1484,
                                    j,
                                    getAndy());
                    markersA.add(locationMarkerA);
                }

                double startALong = -122.14877;
                double lastALong = -122.14932;
                for(double j = startALong; j > lastALong; j-= 0.00001){
                    LocationMarker locationMarkerA =
                            new LocationMarker(
                                    j,
                                    47.586128,
                                    getAndy());
                    markersA.add(locationMarkerA);
                }

                //corner's markers
                LocationMarker locationMarkerA1 =
                        new LocationMarker(
                                -122.149190,
                                47.584308,
                                getAndy());
                LocationMarker locationMarkerA2 =
                        new LocationMarker(
                                -122.148858,
                                47.584308,
                                getAndy());
                LocationMarker locationMarkerA3 =
                        new LocationMarker(
                                -122.148616,
                                47.584060,
                                getAndy());
                LocationMarker locationMarkerA4 =
                        new LocationMarker(
                                -122.148398,
                                47.583851,
                                getAndy());
                LocationMarker locationMarkerA5 =
                        new LocationMarker(
                                -122.148303,
                                47.583234,
                                getAndy());
                LocationMarker locationMarkerA6 =
                        new LocationMarker(
                                -122.148463,
                                47.583121,
                                getAndy());
                LocationMarker locationMarkerA7 =
                        new LocationMarker(
                                -122.148631,
                                47.583121,
                                getAndy());
                LocationMarker locationMarkerA8 =
                        new LocationMarker(
                                -122.148853,
                                47.583238,
                                getAndy());
                LocationMarker locationMarkerA9 =
                        new LocationMarker(
                                -122.149150,
                                47.583957,
                                getAndy());

                markersA.add(locationMarkerA1);
                markersA.add(locationMarkerA2);
                markersA.add(locationMarkerA3);
                markersA.add(locationMarkerA4);
                markersA.add(locationMarkerA5);
                markersA.add(locationMarkerA6);
                markersA.add(locationMarkerA7);
                markersA.add(locationMarkerA8);
                markersA.add(locationMarkerA9);

                new SingletonGroup(locationScene, arFragment, markersA, "Group5", 70);
                locationScene.mLocationMarkers.addAll(markersA);


/////////////////////////////////////////////////////////////////////////////
                //D building
                List<LocationMarker> markersD =
                        new ArrayList<>(2);

                double startDLong = -122.14966;
                double lastDLong = -122.15049;
                for(double j = startDLong; j > lastDLong; j-= 0.00001){
                    LocationMarker locationMarkerD =
                            new LocationMarker(
                                    j,
                                    47.58422,
                                    getExampleView());
                    markersD.add(locationMarkerD);
                }

                double startDLat = 47.58433;
                double lastDLat = 47.58316;
                for(double j = startDLat; j > lastDLat; j-= 0.00001){
                    LocationMarker locationMarkerD =
                            new LocationMarker(
                                    -122.15002,
                                    j,
                                    getExampleView());
                    markersD.add(locationMarkerD);
                }


                //corner's markers
                LocationMarker locationMarkerD1 =
                        new LocationMarker(
                                -122.150480,
                                47.584332,
                                getExampleView());
                LocationMarker locationMarkerD2 =
                        new LocationMarker(
                                -122.149663,
                                47.584323,
                                getExampleView());
                LocationMarker locationMarkerD3 =
                        new LocationMarker(
                                -122.149663,
                                47.584240,
                                getExampleView());
                LocationMarker locationMarkerD4 =
                        new LocationMarker(
                                -122.149743,
                                47.584121,
                                getExampleView());
                LocationMarker locationMarkerD5 =
                        new LocationMarker(
                                -122.149838,
                                47.583160,
                                getExampleView());
                LocationMarker locationMarkerD6 =
                        new LocationMarker(
                                -122.150160,
                                47.583158,
                                getExampleView());
                LocationMarker locationMarkerD7 =
                        new LocationMarker(
                                -122.150357,
                                47.583492,
                                getExampleView());
                LocationMarker locationMarkerD8 =
                        new LocationMarker(
                                -122.150352,
                                47.583728,
                                getExampleView());
                LocationMarker locationMarkerD9 =
                        new LocationMarker(
                                -122.150482,
                                47.584099,
                                getExampleView());

                markersD.add(locationMarkerD1);
                markersD.add(locationMarkerD2);
                markersD.add(locationMarkerD3);
                markersD.add(locationMarkerD4);
                markersD.add(locationMarkerD5);
                markersD.add(locationMarkerD6);
                markersD.add(locationMarkerD7);
                markersD.add(locationMarkerD8);
                markersD.add(locationMarkerD9);

                new SingletonGroup(locationScene, arFragment, markersD, "Group6", 70);
                locationScene.mLocationMarkers.addAll(markersD);

/////////////////////////////////////////////////////////////////////////////
                //N building
                List<LocationMarker> markersN =
                        new ArrayList<>(2);

                double startNLong = -122.14919;
                double lastNLong = -122.15046;
                for(double j = startNLong; j > lastNLong; j-= 0.00001){
                    LocationMarker locationMarkerN =
                            new LocationMarker(
                                    j,
                                    47.58205,
                                    getExampleView());
                    markersN.add(locationMarkerN);
                }

                //corner's markers
                LocationMarker locationMarkerN1 =
                        new LocationMarker(
                                -122.150435,
                                47.582186,
                                getExampleView());
                LocationMarker locationMarkerN2 =
                        new LocationMarker(
                                -122.150173,
                                47.582235,
                                getExampleView());
                LocationMarker locationMarkerN3 =
                        new LocationMarker(
                                -122.149873,
                                47.582299,
                                getExampleView());
                LocationMarker locationMarkerN4 =
                        new LocationMarker(
                                -122.149595,
                                47.582187,
                                getExampleView());
                LocationMarker locationMarkerN5 =
                        new LocationMarker(
                                -122.149185,
                                47.582191,
                                getExampleView());
                LocationMarker locationMarkerN6 =
                        new LocationMarker(
                                -122.149210,
                                47.581904,
                                getExampleView());
                LocationMarker locationMarkerN7 =
                        new LocationMarker(
                                -122.150440,
                                47.581941,
                                getExampleView());

                markersD.add(locationMarkerN1);
                markersD.add(locationMarkerN2);
                markersD.add(locationMarkerN3);
                markersD.add(locationMarkerN4);
                markersD.add(locationMarkerN5);
                markersD.add(locationMarkerN6);
                markersD.add(locationMarkerN7);

                new SingletonGroup(locationScene, arFragment, markersN, "Group7", 70);
                locationScene.mLocationMarkers.addAll(markersN);

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
