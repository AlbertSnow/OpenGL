package com.example.albertsnow.myapplication.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by albertsnow on 6/9/17.
 */

public class MyGLSurfaceView extends GLSurfaceView {

    private MyGLRenderer mRenderer;
    private float angle;

    public MyGLSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(2);
        mRenderer = new MyGLRenderer();
        setRenderer(mRenderer);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 20;
    private float mPreviousX;
    private float mPreviousY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                if (y > getHeight() / 2) {
                    dx = dx * -1;
                }

                if (x < getWidth() / 2) {
                    dy = dy * -1;
                }

                mRenderer.setAngle(mRenderer.getAngle() +
                        ((dx + dy) * TOUCH_SCALE_FACTOR));
                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;

        return true;
    }





}
