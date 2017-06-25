package com.example.albertsnow.myapplication.view;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.example.albertsnow.myapplication.MyApplication;
import com.example.albertsnow.myapplication.R;
import com.example.albertsnow.myapplication.util.ShaderHelper;
import com.example.albertsnow.myapplication.util.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by albertsnow on 6/22/17.
 */

public class AirHockeyRenderer implements GLSurfaceView.Renderer {
    private static final String U_COLOR = "u_Color";
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int BYTES_PER_FLOAT = 4;

    private int uColorLocation;

    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;

    private int program;
    private final FloatBuffer vertexData;


    public AirHockeyRenderer() {
        float[] tableVertices = {
                0f, 0f,
                0f, 14f,
                9f, 14f,
                9f, 0f
        };
        float[] tableVerticesWithTriangles = {
                // Triangle 1
                -0.5f, -0.5f,
                0.5f, 0.5f,
                -0.5f, 0.5f,

                //Triangle 2
                -0.5f, -0.5f,
                0.5f, -0.5f,
                0.5f, 0.5f,

                //Line 1
                -0.5f, 0f,
                0.5f, 0f,

                // Mallets
                0f, -0.25f,
                0f, 0.25f
        };

        vertexData = ByteBuffer
                .allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(tableVerticesWithTriangles);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        String vertexShaderFromResource = TextResourceReader.readTextFileFromResource(
                MyApplication.getApplication(), R.raw.simple_vertex_shader);

        String fragmentShaderFromResource = TextResourceReader.readTextFileFromResource(
                MyApplication.getApplication(), R.raw.simple_fragment_shader);
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderFromResource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderFromResource);
        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);

        ShaderHelper.validateProgram(program);
        GLES20.glUseProgram(program);

        uColorLocation = GLES20.glGetUniformLocation(program, U_COLOR);
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);

        vertexData.position(0);
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT,
            false, 0, vertexData);
        GLES20.glEnableVertexAttribArray(aPositionLocation);
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        //draw triangle, 0 offset, 6 vertices count
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);


        GLES20.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);

        GLES20.glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);

        GLES20.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);
    }


}
