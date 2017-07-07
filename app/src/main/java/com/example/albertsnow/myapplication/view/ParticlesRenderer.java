package com.example.albertsnow.myapplication.view;

import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.example.albertsnow.myapplication.R;
import com.example.albertsnow.myapplication.objects.ParticleShooter;
import com.example.albertsnow.myapplication.objects.ParticleSystem;
import com.example.albertsnow.myapplication.programs.ParticleShaderProgram;
import com.example.albertsnow.myapplication.util.Geometry;
import com.example.albertsnow.myapplication.util.LoggerConfig;
import com.example.albertsnow.myapplication.util.MatrixHelper;
import com.example.albertsnow.myapplication.util.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by albertsnow on 7/5/17.
 */

public class ParticlesRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "ParticleRendererTag";
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];

    private ParticleShaderProgram particleShaderProgram;
    private ParticleSystem particleSystem;
    private ParticleShooter redParticleShooter;
    private ParticleShooter greenParticleShooter;
    private ParticleShooter blueParticleShooter;

    private long globalStartTime;
    private float angleVarianceInDegree = 5f;
    private float speedVariance = 1f;
    private int texture;


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        particleShaderProgram = new ParticleShaderProgram();
        particleSystem = new ParticleSystem(10000);
        globalStartTime = System.nanoTime();
        LoggerConfig.i(TAG, "Time is: " + globalStartTime);


        final Geometry.Vector particleDirection = new Geometry.Vector(0f, 0.5f, 0f);

        redParticleShooter = new ParticleShooter(
                new Geometry.Point(-1f, 0f, 0f),
                particleDirection,
                Color.rgb(255, 50, 5),
                angleVarianceInDegree,
                speedVariance
        );

        greenParticleShooter = new ParticleShooter(
                new Geometry.Point(0f, 0f, 0f),
                particleDirection,
                Color.rgb(25, 255, 25),
                angleVarianceInDegree,
                speedVariance
        );

        blueParticleShooter = new ParticleShooter(
                new Geometry.Point(1f, 0f, 0f),
                particleDirection,
                Color.rgb(5, 50, 255),
                angleVarianceInDegree,
                speedVariance
        );

        texture = TextureHelper.loadTexture(R.drawable.particle_texture);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);

        MatrixHelper.perspectiveM(projectionMatrix, 45, (float)width / (float)height,
                1f, 10f);
        Matrix.setIdentityM(viewMatrix, 0);
        Matrix.translateM(viewMatrix, 0, 0f, -1.5f, -5f);
        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        float currentTime = (System.nanoTime() - globalStartTime) / 1000000000f;

        redParticleShooter.addParticles(particleSystem, currentTime, 5);
        greenParticleShooter.addParticles(particleSystem, currentTime, 5);
        blueParticleShooter.addParticles(particleSystem, currentTime, 5);

        particleShaderProgram.useProgram();
        particleShaderProgram.setUniform(viewProjectionMatrix, currentTime, texture);
        particleSystem.bindData(particleShaderProgram);
        particleSystem.draw();
    }


}
