package com.example.asatkee1.augementedimagetest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.ArCoreApk;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Plane;
import com.google.ar.core.Pose;
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
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
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

public class MainActivity extends AppActivityBuilderMethods {

    private ArFragment arFragment;
    private boolean shouldAddModel = true;
    private LocationScene locationScene;
    private ModelRenderable andyRenderable;
    private ArSceneView arSceneView;
    private boolean installRequested;
    private boolean hasFinishedLoading = false;
    private static Session session = null;


    TextView mainInfo;
    TextView officeHours;

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
                                hasFinishedLoading = true;

                            } catch (InterruptedException | ExecutionException ex) {

                            }
                            return null;
                        });

        arSceneView.getScene().addOnUpdateListener(
                frameTime -> {
                    if (!hasFinishedLoading) {
                        return;
                    }
                    Frame frame = arFragment.getArSceneView().getArFrame();
            if (locationScene == null) {
                locationScene = new LocationScene(this, arSceneView);

                LocationMarker hello = new LocationMarker(-122.1490833,
                        47.5858055,
                        getAndy());

                locationScene.mLocationMarkers.add(hello);

                //hello.node.setEnabled();



//                locationScene.mLocationMarkers.add(
//                        new LocationMarker(
//                                -122.1492074519847,
//                                47.58584620689264,
//                                 getAndy()));
                /*
                locationScene.mLocationMarkers.add(
                        new LocationMarker(
                        -122.148936,
                        47.585708,
                        getAndy()));

                locationScene.mLocationMarkers.add(
                        new LocationMarker(
                                -122.149160,
                                47.585941,
                                getAndy()));

                locationScene.mLocationMarkers.add(
                        new LocationMarker(
                                -122.149200,
                                47.585949,
                                getAndy()));
                locationScene.mLocationMarkers.add(
                        new LocationMarker(
                                -122.149190,
                                47.585942,
                                getAndy()));

                //change lat and long
                locationScene.mLocationMarkers.add(
                        new LocationMarker(
                                -122.148936,
                                47.585708,
                                getAndy()));
                                */

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
        //Session session = null;
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

    private void onUpdateFrame(FrameTime frameTime) {
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
            addFloatingAnchor();
        });
        return base;
    }

    private void addFloatingAnchor() {
        Frame frame = arSceneView.getArFrame();

        Session session = arFragment.getArSceneView().getSession();
        Anchor myAnchor = session.createAnchor(frame.getCamera().getDisplayOrientedPose()
                .compose(Pose.makeTranslation(0, 0, -1f))
                .extractTranslation());
        AnchorNode anchorNode = new AnchorNode(myAnchor);
        anchorNode.setRenderable(andyRenderable);

        placeXML(arFragment, myAnchor, R.layout.hello_instructions);

        //anchorNode.setParent(arFragment.getArSceneView().getScene());

    }




    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeXML(ArFragment arFragment, Anchor anchor, int file) {
        ViewGroup group = (ViewGroup) View.inflate(this, R.layout.activity_main2, null);
        LinearLayout topLayout = group.findViewById(R.id.topLayout);
        LinearLayout bodyLayout = group.findViewById(R.id.bodyLayout);
        phoneBuilder("HR", "564-2274(425)", bodyLayout);

        titleBuilder("R Building", topLayout);
        hasAllGendersBathroom(topLayout);
        hasComputers(topLayout);

        mainInfo = textViewBuilder("Loading...", bodyLayout);
        officeHours = textViewBuilder("Loading...", bodyLayout);
        officeHours.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                anchor.detach();
            }
        });

        textViewBuilder("Human Resources (HR): Location R130(425) | Fax 564-3173", bodyLayout);

        //-------test button------
//            Button button = new Button(this);
//            button.setText("Kill");
//            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150));
//            button.setPadding(5,5,5,5);
//            button.setAllCaps(false);
//            button.setBackgroundResource(R.drawable.important);
//
//            button.setTextColor(Color.WHITE);
//
//            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) button.getLayoutParams();
//            params.topMargin = 10;
//            params.bottomMargin = 15;
//
//            button.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    removeNodeFromScene();
//                }
//            });
//
//            bodyLayout.addView(button);
        //---------------------------

        // --- Async task ---
        //new ParseWebpageTask().execute(THIS_ONES_URL);


        ViewRenderable.builder()
                .setView(this, group)
                .setVerticalAlignment(ViewRenderable.VerticalAlignment.BOTTOM)
                .setHorizontalAlignment(ViewRenderable.HorizontalAlignment.LEFT)
                .build()
                .thenAccept(renderable -> addNodeToScene(arFragment, anchor, renderable));

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
