package com.example.albertsnow.myapplication.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import com.example.albertsnow.myapplication.MyApplication;

/**
 * Created by albertsnow on 6/28/17.
 */

public class TextureHelper {

    private static final String TAG = "TextureHelper";

    public static int loadTexture(int resourceId) {
        Context context = MyApplication.getApplication();
        final int[] textureObjectIds = new int[1];
        GLES20.glGenTextures(1, textureObjectIds, 0);

        if (textureObjectIds[0] == 0) {
            LoggerConfig.e(TAG, "load texture error, can't generate a new OpenGL texture object.");
            return 0;
        }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        final Bitmap bitmap = BitmapFactory.decodeResource(
                context.getResources(), resourceId, options);

        if (bitmap == null) {
            Log.e(TAG, "Resource ID " + resourceId + " could be decoded");

            GLES20.glDeleteTextures(1, textureObjectIds, 0);
            return 0;
        }

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureObjectIds[0]); //create texture object

        //choose mipmap trilinear filter for minification
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
        //choose mipmap bilinear filter for magnification
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        //load image into OpenGL
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();

        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);  //generate multi texture level
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0); //unbind

        return textureObjectIds[0];
    }

}
