package com.example.asatkee1.augementedimagetest;

import android.util.Log;
import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.sceneform.ux.ArFragment;

public class CustomArFragment extends ArFragment {
    @Override
    protected Config getSessionConfiguration(Session session) {
        getPlaneDiscoveryController().setInstructionView(null);

        Config config = new Config(session);
        config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
        config.setFocusMode(Config.FocusMode.AUTO);
        session.configure(config);
        this.getArSceneView().setupSession(session);
        if(((MainActivity)getActivity()).setupAugmentedImageDb(config, session)){
            Log.d("Set up Augmented Image DB", "Success");
        }else{
            Log.e("Set up Augmented Image DB", "Failed to set up DB");
        }


        return config;
    }
}
