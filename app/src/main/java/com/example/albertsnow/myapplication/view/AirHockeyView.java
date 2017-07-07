package com.example.albertsnow.myapplication.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by albertsnow on 6/23/17.
 */

public class AirHockeyView extends GLSurfaceView {

    private final AirHockeyRenderer mRenderer;

    public AirHockeyView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        mRenderer = new AirHockeyRenderer();
        setRenderer(mRenderer);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event != null) {
                    final float normalizedX =
                            (event.getX() / (float) v.getWidth()) * 2 - 1;
                    final float normalizedY =
                            - (event.getY() / (float) v.getHeight()) * 2 - 1;

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                mRenderer.handleTouchPress(
                                        normalizedX, normalizedY
                                );
                            }
                        });
                    } else if (event.getAction() == MotionEvent.ACTION_MOVE){
                        queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                mRenderer.handleTouchDrag(
                                        normalizedX, normalizedY
                                );
                            }
                        });
                    }

                    return true;
                } else {
                    return false;
                }
            }
        });
    }




}
