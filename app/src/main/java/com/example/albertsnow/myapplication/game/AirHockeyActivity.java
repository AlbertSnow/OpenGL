package com.example.albertsnow.myapplication.game;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.example.albertsnow.myapplication.view.AirHockeyView;

/**
 * Created by albertsnow on 6/22/17.
 */

public class AirHockeyActivity extends Activity {

    private GLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mGLView = new AirHockeyView(this);
        setContentView(mGLView );
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume();
    }
}
