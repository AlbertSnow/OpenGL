package com.example.albertsnow.myapplication.view;

import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.example.albertsnow.myapplication.MyApplication;
import com.example.albertsnow.myapplication.R;
import com.example.albertsnow.myapplication.objects.ParticleShooter;
import com.example.albertsnow.myapplication.objects.ParticleSystem;
import com.example.albertsnow.myapplication.objects.SkyBox;
import com.example.albertsnow.myapplication.programs.ParticleShaderProgram;
import com.example.albertsnow.myapplication.programs.SkyBoxShaderProgram;
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

    private SkyBoxShaderProgram skyBoxShaderProgram;
    private SkyBox skyBox;
    private int skyboxTexture;

    private long globalStartTime;
    private float angleVarianceInDegree = 5f;
    private float speedVariance = 1f;
    private int particleTexture;

    private float xRotation, yRotation;


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        particleShaderProgram = new ParticleShaderProgram();
        particleSystem = new ParticleSystem(10000);
        globalStartTime = System.nanoTime();
        LoggerConfig.i(TAG, "Time is: " + globalStartTime);

        skyBoxShaderProgram = new SkyBoxShaderProgram();
        skyBox = new SkyBox();
        skyboxTexture = TextureHelper.loadCubeMap(MyApplication.getApplication(),
                new int[] { R.drawable.left, R.drawable.right,
                            R.drawable.bottom, R.drawable.top,
                            R.drawable.front, R.drawable.back});



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

        particleTexture = TextureHelper.loadTexture(R.drawable.particle_texture);
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
        drawSkybox();
        drawParticles();
    }

    private void drawParticles() {
        float currentTime = (System.nanoTime() - globalStartTime) / 1000000000f;

        redParticleShooter.addParticles(particleSystem, currentTime, 5);
        greenParticleShooter.addParticles(particleSystem, currentTime, 5);
        blueParticleShooter.addParticles(particleSystem, currentTime, 5);

        Matrix.setIdentityM(viewMatrix, 0);
        Matrix.rotateM(viewMatrix, 0, -yRotation, 1f, 0f, 0f);
        Matrix.rotateM(viewMatrix, 0, -xRotation, 0f, 1f, 0f);
        Matrix.translateM(viewMatrix, 0, 0f, -1.5f, -5f);
        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE);

        particleShaderProgram.useProgram();
        particleShaderProgram.setUniform(viewProjectionMatrix, currentTime, particleTexture);
        particleSystem.bindData(particleShaderProgram);
        particleSystem.draw();

        GLES20.glDisable(GLES20.GL_BLEND);
    }

    private void drawSkybox() {
        Matrix.setIdentityM(viewMatrix, 0);
        Matrix.rotateM(viewMatrix, 0, -yRotation, 1f, 0f, 0f);
        Matrix.rotateM(viewMatrix, 0, -xRotation, 0f, 1f, 0f);
        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        skyBoxShaderProgram.useProgram();
        skyBoxShaderProgram.setUniforms(viewProjectionMatrix, skyboxTexture);
        skyBox.bindData(skyBoxShaderProgram);
        skyBox.draw();
    }


    public void handleTouchDrag(float deltaX, float deltaY) {
        xRotation += deltaX / 16f;
        yRotation += deltaY / 16f;

        if (yRotation < -99) {
            yRotation = -90;
        } else if (yRotation > 90) {
            yRotation = 90;
        }
    }

}
