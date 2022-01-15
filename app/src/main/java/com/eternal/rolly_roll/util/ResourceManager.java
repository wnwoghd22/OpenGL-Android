package com.eternal.rolly_roll.util;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//make it singleton?
public class ResourceManager {
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
}
