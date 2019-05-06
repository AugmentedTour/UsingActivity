package com.example.asatkee1.augementedimagetest;

import android.location.Location;

import com.google.ar.sceneform.ArSceneView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import uk.co.appoly.arcorelocation.LocationMarker;
import uk.co.appoly.arcorelocation.LocationScene;
import uk.co.appoly.arcorelocation.sensor.DeviceLocation;

public class BuildingManager {

    private String letter;
    private LocationScene sceneView;
    private ArrayList<LocationMarker> mLocationMarkers = new ArrayList<>();
    private int currentlyActive = 0;


    public BuildingManager (String letter, LocationScene sceneView) {
        this.letter = letter;
        this.sceneView = sceneView;
    }

    public void add (LocationMarker marker) {
        mLocationMarkers.add(marker);
        marker.setOnlyRenderWhenWithin(0);

    }

    public void findClosest () {
        //first, sort array
        mLocationMarkers = sortArray(mLocationMarkers);

        //set up variables
        DeviceLocation deviceLocation = sceneView.deviceLocation;
        double locationLongitude = deviceLocation.currentBestLocation.getLongitude();

        //now we'll go through the array and check each entry in the array.
        //there will be ways to speed this up; for instance, "find the differently weighted cube with as few weights as possible" method.
        //TODO: absolute values
        double bestLong = mLocationMarkers.get(0).longitude - locationLongitude;
        int bestNode = 0;
        for (int i = 1; i < mLocationMarkers.size(); ++i) {
            double holder = mLocationMarkers.get(i).longitude - locationLongitude;
            if (holder >= bestLong) {
                //the sizes are starting to get bigger again, we found our node so turn it on
                mLocationMarkers.get(bestNode).setOnlyRenderWhenWithin(Integer.MAX_VALUE);
                return;
            } else { //holder < bestLong
                bestLong = holder;
                bestNode = i;
            }
        }
        //if we exited the loop, the last node in mLocationMarkers is our closest. Activate it.
        mLocationMarkers.get(mLocationMarkers.size()-1).setOnlyRenderWhenWithin(Integer.MAX_VALUE);

    }

    //This is what we sort the array on, helper method.
    public static Comparator<LocationMarker> LongComparer = new Comparator<LocationMarker>() {
        @Override
        public int compare(LocationMarker location1, LocationMarker location2) {
            return (location1.longitude < location2.longitude ? -1 :
                    (location1.longitude == location2.longitude ? 0 : 1));
        }
    };

    //Sort the array
    public ArrayList<LocationMarker> sortArray (ArrayList<LocationMarker> array) {
        Collections.sort(mLocationMarkers, this.LongComparer);
        return array;
    }


    //TODO absolute values
    public void updateClosest () {
        //grab current location
        DeviceLocation deviceLocation = sceneView.deviceLocation;
        double locationLongitude = deviceLocation.currentBestLocation.getLongitude();

        //check if currently active is closest
        LocationMarker lastActivatedNode = mLocationMarkers.get(currentlyActive);
        if (currentlyActive-1 > 0) {
            LocationMarker nMinusOne = mLocationMarkers.get(currentlyActive - 1);
            //update with better trigonometry later
            if (lastActivatedNode.longitude-locationLongitude > nMinusOne.longitude-locationLongitude) {
                lastActivatedNode.setOnlyRenderWhenWithin(0);
                nMinusOne.setOnlyRenderWhenWithin(Integer.MAX_VALUE);
                currentlyActive = currentlyActive-1;
                return;
            }
        }
        if (currentlyActive+1 < mLocationMarkers.size()) {
            LocationMarker nPlusOne = mLocationMarkers.get(currentlyActive + 1);
            //update with better trigonometry later
            if (lastActivatedNode.longitude-locationLongitude > nPlusOne.longitude-locationLongitude) {
                lastActivatedNode.setOnlyRenderWhenWithin(0);
                nPlusOne.setOnlyRenderWhenWithin(Integer.MAX_VALUE);
                currentlyActive = currentlyActive+1;
                return;
            }
        }

    }
}
