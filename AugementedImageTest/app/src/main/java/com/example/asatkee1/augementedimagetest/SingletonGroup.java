package com.example.asatkee1.augementedimagetest;

import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.List;

import uk.co.appoly.arcorelocation.LocationMarker;
import uk.co.appoly.arcorelocation.LocationScene;

class SingletonGroup {
    private final List<LocationMarker> markers;
    private LocationMarker currentlyVisibleMarker;
    private LocationScene scene;
    private ArFragment arFragment;
    private String groupName;

    public LocationMarker getCurrentlyShownMarker() {
        return this.currentlyVisibleMarker;
    }

    public void setCurrentlyVisibleMarker(LocationMarker currentlyVisibleMarker) {
        this.currentlyVisibleMarker = currentlyVisibleMarker;

        if (this.currentlyVisibleMarker == null) {
            this.showAll();

        }
        else {
            this.hideAllExcept(currentlyVisibleMarker);
        }
    }

    public SingletonGroup(LocationScene scene, ArFragment arFragment, List<LocationMarker> markers, String groupName) {
        this.scene = scene;
        this.markers = markers;
        this.arFragment = arFragment;

        Integer i = 0;
        for(LocationMarker marker : markers) {
            marker.setRenderEvent(
                new SingletonRenderer(this, marker, groupName + "_" + i.toString()));
            i++;
        }
    }

    private void hideAllExcept(LocationMarker visibleMarker) {
        // hide others
        for (LocationMarker lm : this.markers) {
            if (lm != visibleMarker) {
                hideMarker(lm);
            }
        }
    }

    public boolean isVisible(LocationMarker marker) {
        if (marker.anchorNode == null) {
            return false;
        }

        Vector3 worldPosition = marker.anchorNode.getWorldPosition();
        ArSceneView sceneView = arFragment.getArSceneView();

        // looks like there is bug in worldToScreenPoint with horisontal orientation
        Vector3 screenPoint = sceneView.getScene().getCamera().worldToScreenPoint(worldPosition);

        return (screenPoint.x > 0 &&
                screenPoint.x < sceneView.getWidth() &&
                screenPoint.y > 0 &&
                screenPoint.y < sceneView.getHeight());
    }

    public void showAll() {
        for (LocationMarker lm : this.markers) {
            lm.setOnlyRenderWhenWithin(Integer.MAX_VALUE);
        }

        this.scene.refreshAnchors();
    }

    private void hideMarker(LocationMarker locationMarker) {
        if (locationMarker.anchorNode != null) {
            locationMarker.anchorNode.getAnchor().detach();
            locationMarker.anchorNode.setEnabled(false);
            locationMarker.anchorNode.setAnchor(null);
            locationMarker.anchorNode = null;
        }

        locationMarker.setOnlyRenderWhenWithin(0);

        this.scene.refreshAnchors();
    }
}
