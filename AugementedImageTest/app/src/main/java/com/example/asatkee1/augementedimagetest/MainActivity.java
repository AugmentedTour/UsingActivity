package com.example.asatkee1.augementedimagetest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.ArCoreApk;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
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
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import uk.co.appoly.arcorelocation.LocationMarker;
import uk.co.appoly.arcorelocation.LocationScene;
import uk.co.appoly.arcorelocation.rendering.LocationNode;
import uk.co.appoly.arcorelocation.rendering.LocationNodeRender;
import uk.co.appoly.arcorelocation.sensor.DeviceLocation;
import uk.co.appoly.arcorelocation.utils.ARLocationPermissionHelper;

import static com.google.ar.core.ArCoreApk.InstallStatus.INSTALLED;
import static com.google.ar.core.ArCoreApk.InstallStatus.INSTALL_REQUESTED;

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
                locationScene = new LocationScene(this, this, arFragment.getArSceneView());

                //////////////
                LocationMarker locationMarker1 =
                        new LocationMarker(
                                -122.133328,
                                47.571712,
                                getAndy());
                LocationMarker locationMarker2 =
                        new LocationMarker(
                                -122.148870,
                                47.585989,
                                getAndy());
                //////////////

                List<LocationMarker> markers =
                        new ArrayList<>(2);

                markers.add(locationMarker1);
                markers.add(locationMarker2);

                locationMarker1.setRenderEvent(
                        new SingletonRenderer(locationScene, markers, locationMarker1, "1")
                );

                locationMarker2.setRenderEvent(
                        new SingletonRenderer(locationScene, markers, locationMarker2, "2")
                );

                locationScene.mLocationMarkers.add(
                        locationMarker1);
                locationScene.mLocationMarkers.add(
                        locationMarker2);

                LocationMarker layoutLocationMarkerL1 = new LocationMarker(
                        -122.148871,
                        47.585908,
                        getExampleView()
                );
                layoutLocationMarkerL1.setRenderEvent(new LocationNodeRender() {
                    @Override
                    public void render(LocationNode node) {
                        View eView = exampleLayoutRenderable.getView();
                        TextView distanceTextView = eView.findViewById(R.id.textView2);
                        distanceTextView.setText(node.getDistance() + "M");
                    }
                });

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

    class SingletonRenderer implements LocationNodeRender
    {
        private final List<LocationMarker> all;
        private final LocationMarker parent;
        private final LocationScene scene;
        private final String name;

        public SingletonRenderer(
                LocationScene scene, List<LocationMarker> all, LocationMarker parent, String name) {
            this.scene = scene;
            this.all = all;
            this.parent = parent;
            this.name = name;
        }

        @Override
        public void render(LocationNode locationNode) {
            boolean isVisible = isVisible(this.parent, arFragment);

            if (shown == null && isVisible) {
                // hide other
                shown = this.parent;

                for(LocationMarker lm : this.all) {
                    if (lm != this.parent) {
                        hideMarker(lm);
                    }
                }

                Log.i("Render", "Appeared: " + this.name);
            }

            if (shown == this.parent && !isVisible) {
                shown = null;

                for(LocationMarker lm : this.all) {
                    lm.setOnlyRenderWhenWithin(Integer.MAX_VALUE);
                }

                this.scene.refreshAnchors();

                Log.i("Render", "Hidden: " + this.name);
            }


            if (shown == this.parent) {
                Log.i("Render", "Shown: " + this.name);
            }
        }
    }

    private boolean isVisible(LocationMarker locationMarker, ArFragment arFragment) {
        Vector3 worldPosition = locationMarker.anchorNode.getWorldPosition();
        ArSceneView sceneView = arFragment.getArSceneView();

        // looks like there is bug in worldToScreenPoint with horisontal orientation
        Vector3 screenPoint = sceneView.getScene().getCamera().worldToScreenPoint(worldPosition);

        return (screenPoint.x > 0 &&
                screenPoint.x < sceneView.getWidth() &&
                screenPoint.y > 0 &&
                screenPoint.y < sceneView.getHeight());
    }


    private void hideMarker(LocationMarker locationMarker) {
        locationMarker.anchorNode.getAnchor().detach();
        locationMarker.anchorNode.setEnabled(false);

        locationMarker.setOnlyRenderWhenWithin(0);
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
            Log.d("Camra", "unable to get cam");
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

        /*
        Collection<AugmentedImage> augmentedImages = frame.getUpdatedTrackables(AugmentedImage.class);
        Log.d("size", "size is" + augmentedImages.size());


        for (AugmentedImage augmentedImage : augmentedImages){
            Log.d("Debugging method update frame", "Update frame was called!");

            if (augmentedImage.getTrackingState() == TrackingState.TRACKING){
                Log.d("trucking state matched picture", "picture was founded!");

                if ((augmentedImage.getName().equals("H_letter")|| augmentedImage.getName().equals("H_letter_1") ||
                        augmentedImage.getName().equals("Housing"))){

                    Log.d("image name was matched", "model was put on picture!");

                    //start the transperent activity
                    Intent myIntent = new Intent(MainActivity.this, Initial_Page_Activity.class);
                    MainActivity.this.startActivity(myIntent);
                }
            }
        }
        */
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
