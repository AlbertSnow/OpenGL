package com.example.albertsnow.myapplication.programs;

import android.opengl.GLES20;

import com.example.albertsnow.myapplication.MyApplication;
import com.example.albertsnow.myapplication.R;

/**
 * Created by albertsnow on 7/7/17.
 */

public class SkyBoxShaderProgram extends ShaderProgram {

    private final int uMatrixLocation;
    private final int uTextureUnitLocation;
    private final int aPositionLocation;


    public SkyBoxShaderProgram() {
        super(MyApplication.getApplication()
                , R.raw.skybox_vertex_shader, R.raw.skybox_fragment_shader);

        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
        uTextureUnitLocation = GLES20.glGetUniformLocation(program, U_TEXTURE_UNIT);
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
    }

    public void setUniforms(float[] matrix, int textureId) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, textureId);
        GLES20.glUniform1i(uTextureUnitLocation, 0);
    }

    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }
}
