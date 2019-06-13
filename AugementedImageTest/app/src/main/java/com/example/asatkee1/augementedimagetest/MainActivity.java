package com.example.asatkee1.augementedimagetest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.example.asatkee1.augementedimagetest.BuildingPages.ABuilding.ABuilding;
import com.example.asatkee1.augementedimagetest.BuildingPages.BBuilding.BBuilding;
import com.example.asatkee1.augementedimagetest.BuildingPages.CBuilding.CBuilding;
import com.example.asatkee1.augementedimagetest.BuildingPages.DBuilding.DBuilding;
import com.example.asatkee1.augementedimagetest.BuildingPages.EBuilding.EBuilding;
import com.example.asatkee1.augementedimagetest.BuildingPages.FBuilding.FBuilding;
import com.example.asatkee1.augementedimagetest.BuildingPages.GBuilding.GBuilding;
import com.example.asatkee1.augementedimagetest.BuildingPages.KBuilding.KBuilding;
import com.example.asatkee1.augementedimagetest.BuildingPages.LBuilding.LBuilding;
import com.example.asatkee1.augementedimagetest.BuildingPages.NBuilding.NBuilding;
import com.example.asatkee1.augementedimagetest.BuildingPages.RBuilding.RBuilding;
import com.example.asatkee1.augementedimagetest.BuildingPages.SBuilding.SBuilding;
import com.example.asatkee1.augementedimagetest.BuildingPages.TBuilding.TBuilding;
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
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.ScaleController;
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

    private ViewRenderable aBuildingRenderable;
    private ViewRenderable bBuildingRenderable;
    private ViewRenderable cBuildingRenderable;
    private ViewRenderable dBuildingRenderable;
    private ViewRenderable eBuildingRenderable;
    private ViewRenderable fBuildingRenderable;
    private ViewRenderable gBuildingRenderable;
    private ViewRenderable kBuildingRenderable;
    private ViewRenderable lBuildingRenderable;
    private ViewRenderable nBuildingRenderable;
    private ViewRenderable rBuildingRenderable;
    private ViewRenderable sBuildingRenderable;
    private ViewRenderable tBuildingRenderable;

    private ArSceneView arSceneView;
    private boolean installRequested;
    private boolean hasFinishedLoading = false;

    private LocationMarker shown = null;
    private int maxDistance = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //keep it in portrait mode if user wants to turn phone
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Check permissions before launching into AR
        PermissionHandler handler = new PermissionHandler();
        if(!handler.checkPermissions(this)) {
            //if no permissions, return to start page. Start page will ask again.
            Toast.makeText(this, "Needed permissions not granted.", Toast.LENGTH_LONG);
            Intent startLaunch = new Intent(this, LaunchPage.class);
            startActivity(startLaunch);
        }

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


        //Building Objects

        CompletableFuture<ViewRenderable> aBuilding =
                ViewRenderable.builder()
                        .setView(this, R.layout.a_building_object)
                        .build();

        CompletableFuture<ViewRenderable> bBuilding =
                ViewRenderable.builder()
                        .setView(this, R.layout.b_building_object)
                        .build();

        CompletableFuture<ViewRenderable> cBuilding =
                ViewRenderable.builder()
                        .setView(this, R.layout.c_building_object)
                        .build();

        CompletableFuture<ViewRenderable> dBuilding =
                ViewRenderable.builder()
                        .setView(this, R.layout.d_building_object)
                        .build();

        CompletableFuture<ViewRenderable> eBuilding =
                ViewRenderable.builder()
                        .setView(this, R.layout.e_building_object)
                        .build();

        CompletableFuture<ViewRenderable> fBuilding =
                ViewRenderable.builder()
                        .setView(this, R.layout.f_building_object)
                        .build();

        CompletableFuture<ViewRenderable> gBuilding =
                ViewRenderable.builder()
                        .setView(this, R.layout.g_building_object)
                        .build();

        CompletableFuture<ViewRenderable> kBuilding =
                ViewRenderable.builder()
                        .setView(this, R.layout.k_building_object)
                        .build();

        CompletableFuture<ViewRenderable> lBuilding =
                ViewRenderable.builder()
                        .setView(this, R.layout.l_building_object)
                        .build();

        CompletableFuture<ViewRenderable> nBuilding =
                ViewRenderable.builder()
                        .setView(this, R.layout.n_building_object)
                        .build();

        CompletableFuture<ViewRenderable> rBuilding =
                ViewRenderable.builder()
                        .setView(this, R.layout.r_building_object)
                        .build();

        CompletableFuture<ViewRenderable> sBuilding =
                ViewRenderable.builder()
                        .setView(this, R.layout.s_building_object)
                        .build();

        CompletableFuture<ViewRenderable> tBuilding =
                ViewRenderable.builder()
                        .setView(this, R.layout.t_building_object)
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

                                //build buildings
                                aBuildingRenderable = aBuilding.get();
                                bBuildingRenderable = bBuilding.get();
                                cBuildingRenderable = cBuilding.get();
                                dBuildingRenderable = dBuilding.get();
                                eBuildingRenderable = eBuilding.get();
                                fBuildingRenderable = fBuilding.get();
                                gBuildingRenderable = gBuilding.get();
                                kBuildingRenderable = kBuilding.get();
                                lBuildingRenderable = lBuilding.get();
                                nBuildingRenderable = nBuilding.get();
                                rBuildingRenderable = rBuilding.get();
                                sBuildingRenderable = sBuilding.get();
                                tBuildingRenderable = tBuilding.get();

                                //Toast.makeText(this, "I see the marker", Toast.LENGTH_SHORT).show();
                                exampleLayoutRenderable = exampleLayout.get();
                                //Toast.makeText(this, "I see layout marker", Toast.LENGTH_SHORT).show();
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
                                    getBuildingXML(lBuildingRenderable, LBuilding.class));
                    markersL.add(locationMarkerL);
                }

                //corner's markers
                LocationMarker locationMarkerL1 =
                        new LocationMarker(
                                -122.148587,
                                47.586003,
                                getBuildingXML(lBuildingRenderable, LBuilding.class));

                LocationMarker locationMarkerL2 =
                        new LocationMarker(
                                -122.149065,
                                47.586041,
                                getBuildingXML(lBuildingRenderable, LBuilding.class));

                LocationMarker locationMarkerL3 =
                        new LocationMarker(
                                -122.148592,
                                47.585557,
                                getBuildingXML(lBuildingRenderable, LBuilding.class));

                LocationMarker locationMarkerL4 =
                        new LocationMarker(
                                -122.149066,
                                47.585518,
                                getBuildingXML(lBuildingRenderable, LBuilding.class));

                markersL.add(locationMarkerL1);
                markersL.add(locationMarkerL2);
                markersL.add(locationMarkerL3);
                markersL.add(locationMarkerL4);

                new SingletonGroup(locationScene, arFragment, markersL, "Group1", maxDistance);

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
                                getBuildingXML(rBuildingRenderable, RBuilding.class));
                    markersR.add(locationMarkerR);
                }


                double startRLat = 47.58625;
                double lastRLat = 47.58555;
                for(double j = startRLat; j > lastRLat; j-= 0.00001){
                    LocationMarker locationMarkerR =
                            new LocationMarker(
                                    -122.149989,
                                    j,
                                    getBuildingXML(rBuildingRenderable, RBuilding.class));
                    markersR.add(locationMarkerR);
                }


                //corner's markers
                LocationMarker locationMarkerR1 =
                        new LocationMarker(
                                -122.149373,
                                47.586235,
                                getBuildingXML(rBuildingRenderable, RBuilding.class));
                LocationMarker locationMarkerR2 =
                        new LocationMarker(
                                -122.149331,
                                47.586042,
                                getBuildingXML(rBuildingRenderable, RBuilding.class));
                LocationMarker locationMarkerR3 =
                        new LocationMarker(
                                -122.150068,
                                47.586255,
                                getBuildingXML(rBuildingRenderable, RBuilding.class));
                LocationMarker locationMarkerR4 =
                        new LocationMarker(
                                -122.150101,
                                47.585554,
                                getBuildingXML(rBuildingRenderable, RBuilding.class));
                LocationMarker locationMarkerR5 =
                        new LocationMarker(
                                -122.149890,
                                47.585549,
                                getBuildingXML(rBuildingRenderable, RBuilding.class));


                markersR.add(locationMarkerR1);
                markersR.add(locationMarkerR2);
                markersR.add(locationMarkerR3);
                markersR.add(locationMarkerR4);
                markersR.add(locationMarkerR5);

                new SingletonGroup(locationScene, arFragment, markersR, "Group2", maxDistance);
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
                                    getBuildingXML(bBuildingRenderable, BBuilding.class));
                    markersB.add(locationMarkerB);
                }

                double startBLat = 47.58542;
                double lastBLat = 47.58447;
                for(double j = startBLat; j > lastBLat; j-= 0.00001){
                    LocationMarker locationMarkerB =
                            new LocationMarker(
                                    -122.14878,
                                    j,
                                    getBuildingXML(bBuildingRenderable, BBuilding.class));
                    markersB.add(locationMarkerB);
                }


                //corner's markers
                LocationMarker locationMarkerB1 =
                        new LocationMarker(
                                -122.149036,
                                47.585419,
                                getBuildingXML(bBuildingRenderable, BBuilding.class));
                LocationMarker locationMarkerB2 =
                        new LocationMarker(
                                -122.148518,
                                47.585419,
                                getBuildingXML(bBuildingRenderable, BBuilding.class));
                LocationMarker locationMarkerB3 =
                        new LocationMarker(
                                -122.147756,
                                47.585060,
                                getBuildingXML(bBuildingRenderable, BBuilding.class));
                LocationMarker locationMarkerB4 =
                        new LocationMarker(
                                -122.147761,
                                47.584800,
                                getBuildingXML(bBuildingRenderable, BBuilding.class));
                LocationMarker locationMarkerB5 =
                        new LocationMarker(
                                -122.148851,
                                47.584482,
                                getBuildingXML(bBuildingRenderable, BBuilding.class));
                LocationMarker locationMarkerB6 =
                        new LocationMarker(
                                -122.149205,
                                47.584488,
                                getBuildingXML(bBuildingRenderable, BBuilding.class));

                markersB.add(locationMarkerB1);
                markersB.add(locationMarkerB2);
                markersB.add(locationMarkerB3);
                markersB.add(locationMarkerB4);
                markersB.add(locationMarkerB5);
                markersB.add(locationMarkerB6);

                new SingletonGroup(locationScene, arFragment, markersB, "Group3", maxDistance);

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
                                        getBuildingXML(sBuildingRenderable, SBuilding.class));
                        markersS.add(locationMarkerS);
                    }

                    //corner's markers
                    LocationMarker locationMarkerS1 =
                            new LocationMarker(
                                    -122.147436,
                                    47.585085,
                                    getBuildingXML(sBuildingRenderable, SBuilding.class));
                    LocationMarker locationMarkerS2 =
                            new LocationMarker(
                                    -122.147451,
                                    47.584832,
                                    getBuildingXML(sBuildingRenderable, SBuilding.class));
                    LocationMarker locationMarkerS3 =
                            new LocationMarker(
                                    -122.147281,
                                    47.584746,
                                    getBuildingXML(sBuildingRenderable, SBuilding.class));
                    LocationMarker locationMarkerS4 =
                            new LocationMarker(
                                    -122.147081,
                                    47.584739,
                                    getBuildingXML(sBuildingRenderable, SBuilding.class));
                    LocationMarker locationMarkerS5 =
                            new LocationMarker(
                                    -122.147166,
                                    47.585060,
                                    getBuildingXML(sBuildingRenderable, SBuilding.class));

                    markersS.add(locationMarkerS1);
                    markersS.add(locationMarkerS2);
                    markersS.add(locationMarkerS3);
                    markersS.add(locationMarkerS4);
                    markersS.add(locationMarkerS5);

                    new SingletonGroup(locationScene, arFragment, markersS, "Group4", maxDistance);
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
                                    getBuildingXML(aBuildingRenderable, ABuilding.class));
                    markersA.add(locationMarkerA);
                }

                double start2ALat = 47.58384;
                double last2ALat = 47.58313;
                for(double j = start2ALat; j > last2ALat; j-= 0.00001){
                    LocationMarker locationMarkerA =
                            new LocationMarker(
                                    -122.1484,
                                    j,
                                    getBuildingXML(aBuildingRenderable, ABuilding.class));
                    markersA.add(locationMarkerA);
                }

                double startALong = -122.14877;
                double lastALong = -122.14932;
                for(double j = startALong; j > lastALong; j-= 0.00001){
                    LocationMarker locationMarkerA =
                            new LocationMarker(
                                    j,
                                    47.586128,
                                    getBuildingXML(aBuildingRenderable, ABuilding.class));
                    markersA.add(locationMarkerA);
                }

                //corner's markers
                LocationMarker locationMarkerA1 =
                        new LocationMarker(
                                -122.149190,
                                47.584308,
                                getBuildingXML(aBuildingRenderable, ABuilding.class));
                LocationMarker locationMarkerA2 =
                        new LocationMarker(
                                -122.148858,
                                47.584308,
                                getBuildingXML(aBuildingRenderable, ABuilding.class));
                LocationMarker locationMarkerA3 =
                        new LocationMarker(
                                -122.148616,
                                47.584060,
                                getBuildingXML(aBuildingRenderable, ABuilding.class));
                LocationMarker locationMarkerA4 =
                        new LocationMarker(
                                -122.148398,
                                47.583851,
                                getBuildingXML(aBuildingRenderable, ABuilding.class));
                LocationMarker locationMarkerA5 =
                        new LocationMarker(
                                -122.148303,
                                47.583234,
                                getBuildingXML(aBuildingRenderable, ABuilding.class));
                LocationMarker locationMarkerA6 =
                        new LocationMarker(
                                -122.148463,
                                47.583121,
                                getBuildingXML(aBuildingRenderable, ABuilding.class));
                LocationMarker locationMarkerA7 =
                        new LocationMarker(
                                -122.148631,
                                47.583121,
                                getBuildingXML(aBuildingRenderable, ABuilding.class));
                LocationMarker locationMarkerA8 =
                        new LocationMarker(
                                -122.148853,
                                47.583238,
                                getBuildingXML(aBuildingRenderable, ABuilding.class));
                LocationMarker locationMarkerA9 =
                        new LocationMarker(
                                -122.149150,
                                47.583957,
                                getBuildingXML(aBuildingRenderable, ABuilding.class));

                markersA.add(locationMarkerA1);
                markersA.add(locationMarkerA2);
                markersA.add(locationMarkerA3);
                markersA.add(locationMarkerA4);
                markersA.add(locationMarkerA5);
                markersA.add(locationMarkerA6);
                markersA.add(locationMarkerA7);
                markersA.add(locationMarkerA8);
                markersA.add(locationMarkerA9);

                new SingletonGroup(locationScene, arFragment, markersA, "Group5", maxDistance);
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
                                    getBuildingXML(dBuildingRenderable, DBuilding.class));
                    markersD.add(locationMarkerD);
                }

                double startDLat = 47.58433;
                double lastDLat = 47.58316;
                for(double j = startDLat; j > lastDLat; j-= 0.00001){
                    LocationMarker locationMarkerD =
                            new LocationMarker(
                                    -122.15002,
                                    j,
                                    getBuildingXML(dBuildingRenderable, DBuilding.class));
                    markersD.add(locationMarkerD);
                }


                //corner's markers
                LocationMarker locationMarkerD1 =
                        new LocationMarker(
                                -122.150480,
                                47.584332,
                                getBuildingXML(dBuildingRenderable, DBuilding.class));
                LocationMarker locationMarkerD2 =
                        new LocationMarker(
                                -122.149663,
                                47.584323,
                                getBuildingXML(dBuildingRenderable, DBuilding.class));
                LocationMarker locationMarkerD3 =
                        new LocationMarker(
                                -122.149663,
                                47.584240,
                                getBuildingXML(dBuildingRenderable, DBuilding.class));
                LocationMarker locationMarkerD4 =
                        new LocationMarker(
                                -122.149743,
                                47.584121,
                                getBuildingXML(dBuildingRenderable, DBuilding.class));
                LocationMarker locationMarkerD5 =
                        new LocationMarker(
                                -122.149838,
                                47.583160,
                                getBuildingXML(dBuildingRenderable, DBuilding.class));
                LocationMarker locationMarkerD6 =
                        new LocationMarker(
                                -122.150160,
                                47.583158,
                                getBuildingXML(dBuildingRenderable, DBuilding.class));
                LocationMarker locationMarkerD7 =
                        new LocationMarker(
                                -122.150357,
                                47.583492,
                                getBuildingXML(dBuildingRenderable, DBuilding.class));
                LocationMarker locationMarkerD8 =
                        new LocationMarker(
                                -122.150352,
                                47.583728,
                                getBuildingXML(dBuildingRenderable, DBuilding.class));
                LocationMarker locationMarkerD9 =
                        new LocationMarker(
                                -122.150482,
                                47.584099,
                                getBuildingXML(dBuildingRenderable, DBuilding.class));

                markersD.add(locationMarkerD1);
                markersD.add(locationMarkerD2);
                markersD.add(locationMarkerD3);
                markersD.add(locationMarkerD4);
                markersD.add(locationMarkerD5);
                markersD.add(locationMarkerD6);
                markersD.add(locationMarkerD7);
                markersD.add(locationMarkerD8);
                markersD.add(locationMarkerD9);

                new SingletonGroup(locationScene, arFragment, markersD, "Group6", maxDistance);
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
                                    getBuildingXML(nBuildingRenderable, NBuilding.class));
                    markersN.add(locationMarkerN);
                }

                //corner's markers
                LocationMarker locationMarkerN1 =
                        new LocationMarker(
                                -122.150435,
                                47.582186,
                                getBuildingXML(nBuildingRenderable, NBuilding.class));
                LocationMarker locationMarkerN2 =
                        new LocationMarker(
                                -122.150173,
                                47.582235,
                                getBuildingXML(nBuildingRenderable, NBuilding.class));
                LocationMarker locationMarkerN3 =
                        new LocationMarker(
                                -122.149873,
                                47.582299,
                                getBuildingXML(nBuildingRenderable, NBuilding.class));
                LocationMarker locationMarkerN4 =
                        new LocationMarker(
                                -122.149595,
                                47.582187,
                                getBuildingXML(nBuildingRenderable, NBuilding.class));
                LocationMarker locationMarkerN5 =
                        new LocationMarker(
                                -122.149185,
                                47.582191,
                                getBuildingXML(nBuildingRenderable, NBuilding.class));
                LocationMarker locationMarkerN6 =
                        new LocationMarker(
                                -122.149210,
                                47.581904,
                                getBuildingXML(nBuildingRenderable, NBuilding.class));
                LocationMarker locationMarkerN7 =
                        new LocationMarker(
                                -122.150440,
                                47.581941,
                                getBuildingXML(nBuildingRenderable, NBuilding.class));

                markersD.add(locationMarkerN1);
                markersD.add(locationMarkerN2);
                markersD.add(locationMarkerN3);
                markersD.add(locationMarkerN4);
                markersD.add(locationMarkerN5);
                markersD.add(locationMarkerN6);
                markersD.add(locationMarkerN7);

                new SingletonGroup(locationScene, arFragment, markersN, "Group7", maxDistance);
                locationScene.mLocationMarkers.addAll(markersN);


                        ////////////////////////////////////////////////////////////////////////////////
//              //F building
                        List<LocationMarker> markersF =
                                new ArrayList<>(10);

                        double startFLat = 47.58532;
                        double lastFLat = 47.58515;
                        for(double j = startFLat; j > lastFLat; j-= 0.00001){
                            LocationMarker locationMarkerF =
                                    new LocationMarker(
                                            -122.150496,
                                            j,
                                            getBuildingXML(fBuildingRenderable, FBuilding.class));
                            markersF.add(locationMarkerF);
                        }

                        //corner's markers
                        LocationMarker locationMarkerF1 =
                                new LocationMarker(
                                        -122.150572,
                                        47.585311,
                                        getBuildingXML(fBuildingRenderable, FBuilding.class));
                        LocationMarker locationMarkerF2 =
                                new LocationMarker(
                                        -122.150411,
                                        47.585311,
                                        getBuildingXML(fBuildingRenderable, FBuilding.class));
                        LocationMarker locationMarkerF3 =
                                new LocationMarker(
                                        -122.150407,
                                        47.585156,
                                        getBuildingXML(fBuildingRenderable, FBuilding.class));
                        LocationMarker locationMarkerF4 =
                                new LocationMarker(
                                        -122.150580,
                                        47.585156,
                                        getBuildingXML(fBuildingRenderable, FBuilding.class));

                        markersF.add(locationMarkerF1);
                        markersF.add(locationMarkerF2);
                        markersF.add(locationMarkerF3);
                        markersF.add(locationMarkerF4);

                        new SingletonGroup(locationScene, arFragment, markersF, "Group8", maxDistance);
                        locationScene.mLocationMarkers.addAll(markersF);

////////////////////////////////////////////////////////////////////////////////
//                     //E building
                        List<LocationMarker> markersE =
                                new ArrayList<>(10);

                        double startELat = 47.58354;
                        double lastELat = 47.58295;
                        for(double j = startELat; j > lastELat; j-= 0.00001){
                            LocationMarker locationMarkerE =
                                    new LocationMarker(
                                            -122.14944,
                                            j,
                                            getBuildingXML(eBuildingRenderable, EBuilding.class));
                            markersE.add(locationMarkerE);
                        }

                        //corner's markers
                        LocationMarker locationMarkerE1 =
                                new LocationMarker(
                                        -122.149320,
                                        47.583493,
                                        getBuildingXML(eBuildingRenderable, EBuilding.class));
                        LocationMarker locationMarkerE2 =
                                new LocationMarker(
                                        -122.149198,
                                        47.583257,
                                        getBuildingXML(eBuildingRenderable, EBuilding.class));
                        LocationMarker locationMarkerE3 =
                                new LocationMarker(
                                        -122.149041,
                                        47.583250,
                                        getBuildingXML(eBuildingRenderable, EBuilding.class));
                        LocationMarker locationMarkerE4 =
                                new LocationMarker(
                                        -122.148991,
                                        47.583071,
                                        getBuildingXML(eBuildingRenderable, EBuilding.class));
                        LocationMarker locationMarkerE5 =
                                new LocationMarker(
                                        -122.149106,
                                        47.582944,
                                        getBuildingXML(eBuildingRenderable, EBuilding.class));
                        LocationMarker locationMarkerE6 =
                                new LocationMarker(
                                        -122.149635,
                                        47.583154,
                                        getBuildingXML(eBuildingRenderable, EBuilding.class));
                        LocationMarker locationMarkerE7 =
                                new LocationMarker(
                                        -122.149654,
                                        47.583389,
                                        getBuildingXML(eBuildingRenderable, EBuilding.class));

                        markersE.add(locationMarkerE1);
                        markersE.add(locationMarkerE2);
                        markersE.add(locationMarkerE3);
                        markersE.add(locationMarkerE4);
                        markersE.add(locationMarkerE5);
                        markersE.add(locationMarkerE6);
                        markersE.add(locationMarkerE7);

                        new SingletonGroup(locationScene, arFragment, markersE, "Group9", maxDistance);
                        locationScene.mLocationMarkers.addAll(markersE);

                        ////////////////////////////////////////////////////////////////////////////////
//                     //C building
                        List<LocationMarker> markersC =
                                new ArrayList<>(10);

                        double startCLat = 47.58540;
                        double lastCLat = 47.584483;
                        for(double j = startELat; j > lastCLat; j-= 0.00001){
                            LocationMarker locationMarkerC =
                                    new LocationMarker(
                                            -122.14980,
                                            j,
                                            getBuildingXML(cBuildingRenderable, CBuilding.class));
                            markersC.add(locationMarkerC);
                        }

                        //corner's markers
                        LocationMarker locationMarkerC1 =
                                new LocationMarker(
                                        -122.149424,
                                        47.585398,
                                        getBuildingXML(cBuildingRenderable, CBuilding.class));
                        LocationMarker locationMarkerC2 =
                                new LocationMarker(
                                        -122.149430,
                                        47.585257,
                                        getBuildingXML(cBuildingRenderable, CBuilding.class));
                        LocationMarker locationMarkerC3 =
                                new LocationMarker(
                                        -122.149424,
                                        47.585083,
                                        getBuildingXML(cBuildingRenderable, CBuilding.class));
                        LocationMarker locationMarkerC4 =
                                new LocationMarker(
                                        -122.149639,
                                        47.585073,
                                        getBuildingXML(cBuildingRenderable, CBuilding.class));
                        LocationMarker locationMarkerC5 =
                                new LocationMarker(
                                        -122.149553,
                                        47.584490,
                                        getBuildingXML(cBuildingRenderable, CBuilding.class));
                        LocationMarker locationMarkerC6 =
                                new LocationMarker(
                                        -122.150267,
                                        47.584486,
                                        getBuildingXML(cBuildingRenderable, CBuilding.class));
                        LocationMarker locationMarkerC7 =
                                new LocationMarker(
                                        -122.150331,
                                        47.584808,
                                        getBuildingXML(cBuildingRenderable, CBuilding.class));
                        LocationMarker locationMarkerC8 =
                                new LocationMarker(
                                        -122.150154,
                                        47.585272,
                                        getBuildingXML(cBuildingRenderable, CBuilding.class));

                        markersC.add(locationMarkerC1);
                        markersC.add(locationMarkerC2);
                        markersC.add(locationMarkerC3);
                        markersC.add(locationMarkerC4);
                        markersC.add(locationMarkerC5);
                        markersC.add(locationMarkerC6);
                        markersC.add(locationMarkerC7);
                        markersC.add(locationMarkerC8);

                        new SingletonGroup(locationScene, arFragment, markersC, "Group10", maxDistance);
                        locationScene.mLocationMarkers.addAll(markersC);

////////////////////////////////////////////////////////////////////////////////
//                     //K building
                        List<LocationMarker> markersK =
                                new ArrayList<>(10);

                        double startKLat = 47.58711;
                        double lastKLat = 47.58657;
                        for(double j = startKLat; j > lastKLat; j-= 0.00001){
                            LocationMarker locationMarkerK =
                                    new LocationMarker(
                                            -122.15021,
                                            j,
                                            getBuildingXML(kBuildingRenderable, KBuilding.class));
                            markersE.add(locationMarkerK);
                        }

                        //corner's markers
                        LocationMarker locationMarkerK1 =
                                new LocationMarker(
                                        -122.150373,
                                        47.587108,
                                        getBuildingXML(kBuildingRenderable, KBuilding.class));
                        LocationMarker locationMarkerK2 =
                                new LocationMarker(
                                        -122.150056,
                                        47.587101,
                                        getBuildingXML(kBuildingRenderable, KBuilding.class));
                        LocationMarker locationMarkerK3 =
                                new LocationMarker(
                                        -122.150040,
                                        47.586580,
                                        getBuildingXML(kBuildingRenderable, KBuilding.class));
                        LocationMarker locationMarkerK4 =
                                new LocationMarker(
                                        -122.150357,
                                        47.586580,
                                        getBuildingXML(kBuildingRenderable, KBuilding.class));

                        markersK.add(locationMarkerK1);
                        markersK.add(locationMarkerK2);
                        markersK.add(locationMarkerK3);
                        markersK.add(locationMarkerK4);

                        new SingletonGroup(locationScene, arFragment, markersK, "Group11", maxDistance);
                        locationScene.mLocationMarkers.addAll(markersK);


////////////////////////////////////////////////////////////////////////////////
//                     //G building

                        List<LocationMarker> markersG =
                                new ArrayList<>(10);

                        double startGLat = 47.58710;
                        double lastGLat = 47.58641;
                        for(double j = startGLat; j > lastGLat; j-= 0.00001){
                            LocationMarker locationMarkerG =
                                    new LocationMarker(
                                            -122.14934,
                                            j,
                                            getBuildingXML(gBuildingRenderable, GBuilding.class));
                            markersE.add(locationMarkerG);
                        }

                        //corner's markers
                        LocationMarker locationMarkerG1 =
                                new LocationMarker(
                                        -122.149788,
                                        47.586956,
                                        getBuildingXML(gBuildingRenderable, GBuilding.class));
                        LocationMarker locationMarkerG2 =
                                new LocationMarker(
                                        -122.148849,
                                        47.587097,
                                        getBuildingXML(gBuildingRenderable, GBuilding.class));
                        LocationMarker locationMarkerG3 =
                                new LocationMarker(
                                        -122.148790,
                                        47.586663,
                                        getBuildingXML(gBuildingRenderable, GBuilding.class));
                        LocationMarker locationMarkerG4 =
                                new LocationMarker(
                                        -122.149777,
                                        47.586435,
                                        getBuildingXML(gBuildingRenderable, GBuilding.class));
                        LocationMarker locationMarkerG5 =
                                new LocationMarker(
                                        -122.149772,
                                        47.586960,
                                        getBuildingXML(gBuildingRenderable, GBuilding.class));

                        markersG.add(locationMarkerG1);
                        markersG.add(locationMarkerG2);
                        markersG.add(locationMarkerG3);
                        markersG.add(locationMarkerG4);
                        markersG.add(locationMarkerG5);

                        new SingletonGroup(locationScene, arFragment, markersG, "Group11", maxDistance);
                        locationScene.mLocationMarkers.addAll(markersG);

/////////////////////////////////////////////////////////////////////////////
//                      //T building
                        List<LocationMarker> markersT =
                                new ArrayList<>(2);

                        double startTLong = -122.14704;
                        double lastTLong = -122.14792;
                        for(double j = startTLong; j > lastTLong; j-= 0.00001){
                            LocationMarker locationMarkerT =
                                    new LocationMarker(
                                            j,
                                            47.58416,
                                            getBuildingXML(tBuildingRenderable, TBuilding.class));
                            markersR.add(locationMarkerT);
                        }

                        double startTLat = 47.58426;
                        double lastTLat = 47.583692;
                        for(double j = startTLat; j > lastTLat; j-= 0.00001){
                            LocationMarker locationMarkerT =
                                    new LocationMarker(
                                            -122.14764,
                                            j,
                                            getBuildingXML(tBuildingRenderable, TBuilding.class));
                            markersR.add(locationMarkerT);
                        }


                        //corner's markers
                        LocationMarker locationMarkerT1 =
                                new LocationMarker(
                                        -122.147910,
                                        47.584264,
                                        getBuildingXML(tBuildingRenderable, TBuilding.class));
                        LocationMarker locationMarkerT2 =
                                new LocationMarker(
                                        -122.147052,
                                        47.584249,
                                        getBuildingXML(tBuildingRenderable, TBuilding.class));
                        LocationMarker locationMarkerT3 =
                                new LocationMarker(
                                        -122.147057,
                                        47.584090,
                                        getBuildingXML(tBuildingRenderable, TBuilding.class));
                        LocationMarker locationMarkerT4 =
                                new LocationMarker(
                                        -122.147470,
                                        47.583692,
                                        getBuildingXML(tBuildingRenderable, TBuilding.class));
                        LocationMarker locationMarkerT5 =
                                new LocationMarker(
                                        -122.147749,
                                        47.583699,
                                        getBuildingXML(tBuildingRenderable, TBuilding.class));
                        LocationMarker locationMarkerT6 =
                                new LocationMarker(
                                        -122.147921,
                                        47.584177,
                                        getBuildingXML(tBuildingRenderable, TBuilding.class));


                        markersR.add(locationMarkerT1);
                        markersR.add(locationMarkerT2);
                        markersR.add(locationMarkerT3);
                        markersR.add(locationMarkerT4);
                        markersR.add(locationMarkerT5);

                        new SingletonGroup(locationScene, arFragment, markersT, "Group12", maxDistance);
                        locationScene.mLocationMarkers.addAll(markersT);

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
                                //Toast.makeText(this, "I see the marker", Toast.LENGTH_SHORT).show();

                            } catch (InterruptedException | ExecutionException ex) {

                            }
                            return null;
                        });
    }
    private Node getAndy() {
        Node base = new Node();
        base.setRenderable(andyRenderable);
        Context c = this;
        //Toast.makeText(c, "In get Andy method", Toast.LENGTH_LONG).show();
        base.setOnTapListener((v, event) -> {
            Toast.makeText(
                    c, "Location marker is touched.", Toast.LENGTH_LONG)
                    .show();
            //Intent myIntent = new Intent(MainActivity.this, LaunchPage.class);
            //MainActivity.this.startActivity(myIntent);
        });
        return base;
    }

    //A generic class that will build a node based on a renderable that when tapped will open the sent activity.
    private Node getBuildingXML(Renderable renderable, Class goTo) {
        Node base = new Node();
        base.setRenderable(renderable);
        Context c = this;
        base.setOnTapListener((v, event) -> {
            //Toast.makeText(
             //       c, "Location marker is touched.", Toast.LENGTH_LONG)
             //       .show();
            Intent myIntent = new Intent(MainActivity.this, goTo);
            MainActivity.this.startActivity(myIntent);
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

    private Node getExampleView(Renderable renderable) {
        Node base = new Node();
        base.setRenderable(renderable);

        return base;
    }

//    private void placeObject(ArFragment fragment, Anchor anchor, Uri model){
//        ModelRenderable.builder()
//                .setSource(fragment.getContext(), model)
//                .build()
//                .thenAccept(renderable -> addNodeToScene(fragment, anchor, renderable))
//                .exceptionally((throwable -> {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setMessage(throwable.getMessage())
//                            .setTitle("Error!");
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                    return null;
//                }));
//    }
//
//
//    private void addNodeToScene(ArFragment arFragment, Anchor anchor, Renderable renderable) {
//        AnchorNode anchorNode = new AnchorNode(anchor);
//        TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
//
//        //rotate node
//        node.setLocalRotation(Quaternion.axisAngle(new Vector3(1f, 0, 0), 270f));
//        //node.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 1f, 0), 90f));
//
//        //set scale
//        ScaleController scaler = node.getScaleController();
//        scaler.setMinScale(0.2f);
//        scaler.setMaxScale(0.3f);
//
//
//        node.setLocalPosition(new Vector3(-.2f, 0, .2f));
//
//        node.setRenderable(renderable);
//        node.setParent(anchorNode);
//        arFragment.getArSceneView().getScene().addChild(anchorNode);
//        node.select();
//
//    }

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
