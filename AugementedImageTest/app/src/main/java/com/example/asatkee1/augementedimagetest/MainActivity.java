package com.example.asatkee1.augementedimagetest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.FrameTime;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    private CustomArFragment arFragment;
    private boolean shouldAddModel = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
        arFragment.getPlaneDiscoveryController().hide();

        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame);
    }

    private void onUpdateFrame(FrameTime frameTime) {
        Frame frame = arFragment.getArSceneView().getArFrame();

        Collection<AugmentedImage> augmentedImages = frame.getUpdatedTrackables(AugmentedImage.class);

        for (AugmentedImage augmentedImage : augmentedImages){

            if (augmentedImage.getTrackingState() == TrackingState.TRACKING){

                if ((augmentedImage.getName().equals("H_letter")|| augmentedImage.getName().equals("H_letter_1") ||
                        augmentedImage.getName().equals("Housing"))){

                    //start the transperent activity
//                    Intent myIntent = new Intent(MainActivity.this, RBuilding.class);
//                    MainActivity.this.startActivity(myIntent);

                } else if ((augmentedImage.getName().equals("E_Building"))) {

                    //start the transperent activity
                    Intent myIntent = new Intent(MainActivity.this, EBuilding.class);
                    MainActivity.this.startActivity(myIntent);

                }else if ((augmentedImage.getName().equals("R_letter"))) {

                    //start the transperent activity
                    Intent myIntent = new Intent(MainActivity.this, RBuilding.class);
                    MainActivity.this.startActivity(myIntent);

                }else if ((augmentedImage.getName().equals("L_letter"))){

                        //start the transperent activity
                        Intent myIntent = new Intent(MainActivity.this, L_building.class);
                        MainActivity.this.startActivity(myIntent);

                }else if ((augmentedImage.getName().equals("F_Building"))){

                    //start the transperent activity
                    Intent myIntent = new Intent(MainActivity.this, FBuilding.class);
                    MainActivity.this.startActivity(myIntent);

                }else if ((augmentedImage.getName().equals("G_Building"))){

                    //start the transperent activity
                    Intent myIntent = new Intent(MainActivity.this, GBuilding.class);
                    MainActivity.this.startActivity(myIntent);

                }else if ((augmentedImage.getName().equals("K_Building"))){

                    //start the transperent activity
                    Intent myIntent = new Intent(MainActivity.this, KBuilding.class);
                    MainActivity.this.startActivity(myIntent);

                }else if ((augmentedImage.getName().equals("Q_Building"))){

                    //start the transperent activity
                    Intent myIntent = new Intent(MainActivity.this, QBuilding.class);
                    MainActivity.this.startActivity(myIntent);

                }else if ((augmentedImage.getName().equals("U_Building"))){

                   //start the transperent activity
                    Intent myIntent = new Intent(MainActivity.this, UBuilding.class);
                    MainActivity.this.startActivity(myIntent);

                }
            }
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
        augmentedImageDatabase.addImage("R_letter", bitmap.get(1));
        augmentedImageDatabase.addImage("L_letter", bitmap.get(2));
        augmentedImageDatabase.addImage("Housing", bitmap.get(3));
        augmentedImageDatabase.addImage("E_Building", bitmap.get(4));
        augmentedImageDatabase.addImage("F_Building", bitmap.get(5));
        augmentedImageDatabase.addImage("G_Building", bitmap.get(6));
        augmentedImageDatabase.addImage("K_Building", bitmap.get(7));
        augmentedImageDatabase.addImage("Q_Building", bitmap.get(8));
        augmentedImageDatabase.addImage("U_Building", bitmap.get(9));


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
        try (InputStream is = getAssets().open("R_letter.jpg")){
            bitmaps.add(BitmapFactory.decodeStream(is));
        }
        catch (IOException e){
            Log.e("ImageLoad", "IO Exception while loading", e);
        }
        try (InputStream is = getAssets().open("L_letter.jpg")){
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

        try (InputStream is = getAssets().open("E_Building.jpg")){
            bitmaps.add(BitmapFactory.decodeStream(is));
        }
        catch (IOException e){
            Log.e("ImageLoad", "IO Exception while loading", e);
        }
        try (InputStream is = getAssets().open("F_Building.jpg")){
            bitmaps.add(BitmapFactory.decodeStream(is));
        }
        catch (IOException e){
            Log.e("ImageLoad", "IO Exception while loading", e);
        }
        try (InputStream is = getAssets().open("G_Building.jpg")){
            bitmaps.add(BitmapFactory.decodeStream(is));
        }
        catch (IOException e){
            Log.e("ImageLoad", "IO Exception while loading", e);
        }
        try (InputStream is = getAssets().open("K_Building.jpg")){
            bitmaps.add(BitmapFactory.decodeStream(is));
        }
        catch (IOException e){
            Log.e("ImageLoad", "IO Exception while loading", e);
        }
        try (InputStream is = getAssets().open("Q_Building.jpg")){
            bitmaps.add(BitmapFactory.decodeStream(is));
        }
        catch (IOException e){
            Log.e("ImageLoad", "IO Exception while loading", e);
        }
        try (InputStream is = getAssets().open("U_Building.jpg")){
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
