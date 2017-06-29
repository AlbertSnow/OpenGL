package com.example.albertsnow.myapplication.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.example.albertsnow.myapplication.R;

/**
 * Created by albertsnow on 6/29/17.
 */

public class ColorShaderProgram extends ShaderProgram {

    // Uniform locations
    private final int uMatrixLocation;

    // Attribute locations
    private final int aColorLocation;
    private final int aPositionLocation;

    public ColorShaderProgram(Context context) {
        super(context, R.raw.simple_vertex_shader,
                R.raw.simple_fragment_shader);

        // Retrieve uniform location for the shader program
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);

        // Retrieve attribute location for the shader program
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR);
    }

    public void setUniforms(float[] matrix) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
    }

    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }

    public int getColorAttributeLocation() {
        return aColorLocation;
    }

}
