package com.example.asatkee1.augementedimagetest;

import android.util.Log;

import uk.co.appoly.arcorelocation.LocationMarker;
import uk.co.appoly.arcorelocation.rendering.LocationNode;
import uk.co.appoly.arcorelocation.rendering.LocationNodeRender;

class SingletonRenderer implements LocationNodeRender {
    private final LocationMarker parent;
    private final String name;
    private final SingletonGroup group;

    public SingletonRenderer(
            SingletonGroup group, LocationMarker parent, String name) {
        this.group = group;
        this.parent = parent;
        this.name = name;
    }

    @Override
    public void render(LocationNode locationNode) {
        boolean isVisible = this.group.isVisible(this.parent);

        if (this.group.getCurrentlyShownMarker() == null && isVisible) {

            this.group.setCurrentlyVisibleMarker(this.parent);

            Log.i("Render", "Appeared: " + this.name);
        }

        if (this.group.getCurrentlyShownMarker() == this.parent) {

            if (isVisible) {
                Log.i("Render", "Shown: " + this.name);
            }
            else {
                this.group.setCurrentlyVisibleMarker(null);

                Log.i("Render", "Hidden: " + this.name);
            }
        }
    }
}
