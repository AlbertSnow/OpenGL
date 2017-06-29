package com.example.albertsnow.myapplication.view;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.example.albertsnow.myapplication.MyApplication;
import com.example.albertsnow.myapplication.R;
import com.example.albertsnow.myapplication.programs.ColorShaderProgram;
import com.example.albertsnow.myapplication.programs.TextureShaderProgram;
import com.example.albertsnow.myapplication.shape.Mallet;
import com.example.albertsnow.myapplication.shape.Table;
import com.example.albertsnow.myapplication.util.MatrixHelper;
import com.example.albertsnow.myapplication.util.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by albertsnow on 6/22/17.
 */

public class AirHockeyRenderer implements GLSurfaceView.Renderer {
    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];

    private TextureShaderProgram textureShaderProgram;
    private ColorShaderProgram colorShaderProgram;

    private Table table;
    private Mallet mallet;

    private int texture;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        table = new Table();
        mallet = new Mallet();

        textureShaderProgram = new TextureShaderProgram(MyApplication.getApplication());
        colorShaderProgram = new ColorShaderProgram(MyApplication.getApplication());

        texture = TextureHelper.loadTexture(R.drawable.air_hockey_surface);
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);

        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width / (float) height,
                1f, 10f);
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -3f);
        Matrix.rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);
        final float[] temp = new float[16];
        Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        textureShaderProgram.useProgram();
        textureShaderProgram.setUniforms(projectionMatrix, texture);

        table.bindData(textureShaderProgram);
        table.draw();

        // Draw the mallets
        colorShaderProgram.useProgram();
        colorShaderProgram.setUniforms(projectionMatrix);

        mallet.bindData(colorShaderProgram);
        mallet.draw();
    }


}
