package com.example.albertsnow.myapplication.view;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by albertsnow on 6/23/17.
 */

public class AirHockeyView extends GLSurfaceView {

    private final GLSurfaceView.Renderer mRenderer;

    public AirHockeyView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        mRenderer = new AirHockeyRenderer();
        setRenderer(mRenderer);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }




}
