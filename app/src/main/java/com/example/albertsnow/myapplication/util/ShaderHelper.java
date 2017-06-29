package com.example.albertsnow.myapplication.util;

import android.util.Log;

import static android.opengl.GLES20.*;

/**
 * Created by albertsnow on 6/23/17.
 */

public class ShaderHelper {
    private static final String TAG = "ShaderHelper";

    public static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }



    private static int compileShader(int type, String shaderCode) {
        final int shaderObjectId = glCreateShader(type);

        if (shaderObjectId == 0) {
            LoggerConfig.e(TAG, "Could not create shader");
            return 0;
        }

        glShaderSource(shaderObjectId, shaderCode);
        glCompileShader(shaderObjectId);

        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);

        if (compileStatus[0] == 0) {
            Log.e(TAG, "Error compile shader, Source:\n" +
                shaderCode + "\n Log: " +
                    glGetShaderInfoLog(shaderObjectId));
            return 0;
        }

        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        final int programObjectId = glCreateProgram();

        if (programObjectId == 0) {
            Log.e(TAG, "Could not create new program");
            return 0;
        }

        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);

        glLinkProgram(programObjectId);

        final int[] status = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, status, 0);

        if (status[0] == 0) {
            Log.e(TAG, "Error link program:\n" +
                    glGetProgramInfoLog(programObjectId));
            glDeleteProgram(programObjectId);
        }

        return programObjectId;
    }

    public static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);

        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);

        Log.i(TAG, "Validate Program Status: " + validateStatus[0] +
            "\nLog: " + glGetProgramInfoLog(programObjectId));

        return validateStatus[0] != 0;
    }

    public static int buildProgram(String vertexShaderSource,
                                   String fragmentShaderSource) {
        int program;

        //Compile the shaders
        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);

        //Link them into a shader program
        program = linkProgram(vertexShader, fragmentShader);

        validateProgram(program);

        return program;
    }


}
