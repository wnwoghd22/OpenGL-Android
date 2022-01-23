package com.eternal.rolly_roll.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.opengl.GLUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.opengl.GLES20.*;

//make it singleton?
public class ResourceManager {
    private static String TAG = "ResourceManager";

    public static String readTextFileFromResource(Context c, int r_id) {
        StringBuilder body = new StringBuilder();

        try {
            InputStream inputStream = c.getResources().openRawResource(r_id);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String nextLine;

            while ((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not open resource: " + r_id, e);
        } catch (Resources.NotFoundException nfe) {
            throw new RuntimeException("Resource not found: " + r_id, nfe);
        }

        return body.toString();
    }


    public static int loadTexture(Context context, int resourceId) {
        final int[] textureObjectId = new int[1];
        glGenTextures(1, textureObjectId, 0);

        if (textureObjectId[0] == 0) {
            if (LoggerConfig.TEXTURE_LOG) {
                Log.w(TAG, "Could not create a new openGL texture object");
            }
            return 0;
        }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

        if (bitmap == null) {
            if (LoggerConfig.TEXTURE_LOG) {
                Log.w(TAG,  "Resource ID " + resourceId +  " Could not be decoded.");
            }
            return 0;
        }
        glBindTexture(GL_TEXTURE_2D, textureObjectId[0]);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();

        glGenerateMipmap(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D,0);

        if (LoggerConfig.TEXTURE_LOG) {
            Log.w(TAG, "Texture load complete, textureId: " + textureObjectId[0]);
        }

        return textureObjectId[0];
    }

    public Typeface getTypeFace(Context context, String name) {
        return Typeface.createFromAsset(context.getAssets(), name);
    }
}
