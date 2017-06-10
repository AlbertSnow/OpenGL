package com.example.albertsnow.myapplication;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.albertsnow.myapplication.view.MyGLSurfaceView;

public class MainActivity extends AppCompatActivity {
    private GLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView );

    }


}
