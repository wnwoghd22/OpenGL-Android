package com.eternal.rolly_roll.game.view.shader;

import android.content.Context;
import android.util.Log;

import com.eternal.rolly_roll.util.LoggerConfig;
import com.eternal.rolly_roll.util.ResourceManager;

import static android.opengl.GLES20.*;

public class ShaderProgram {
    private static final String TAG = "shader program";

    public final int ID; // assign program location

    protected ShaderProgram(Context context, int vertexResourceId, int fragmentResourceId) {
        String vertSource = ResourceManager.readTextFileFromResource(context, vertexResourceId);
        String fragSource = ResourceManager.readTextFileFromResource(context, fragmentResourceId);

        ID = buildProgram(vertSource, fragSource);
    }

    public void use() {
        glUseProgram(ID);
    }

    private static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }
    private static int compileFragmentShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int compileShader(int type, String shaderCode) {
        final int shaderObjectId = glCreateShader(type);

        if (shaderObjectId == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Could not create new shader.");
            }
            return 0;
        }
        glShaderSource(shaderObjectId, shaderCode);

        glCompileShader(shaderObjectId);

        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);

        if (LoggerConfig.ON) {
            Log.v(TAG, "Results of compiling source: " + "\n" + shaderCode + "\n: "
                    + glGetShaderInfoLog(shaderObjectId));
        }

        if (compileStatus[0] == 0) {
            glDeleteShader(shaderObjectId);

            if (LoggerConfig.ON) {
                Log.v(TAG, "Compilation of shader failed.");
            }

            return 0;
        }

        return shaderObjectId;
    }

    private static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        final int programObjectId = glCreateProgram();

        if (programObjectId == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Could not create new program");
            }
            return 0;
        }

        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);

        glLinkProgram(programObjectId);

        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);
        if (LoggerConfig.ON) {
            Log.v(TAG, "Result of linking program: \n" +
                    glGetShaderInfoLog(programObjectId));
        }

        if (linkStatus[0] == 0) {
            glDeleteProgram(programObjectId);
            if (LoggerConfig.ON) {
                Log.v(TAG, "Linking of program failed.");
            }
            return 0;
        }

        return programObjectId;
    }

    private static boolean validateProgram(int i) {
        glValidateProgram(i);

        final int[] validateStatus = new int[1];
        glGetProgramiv(i, GL_VALIDATE_STATUS, validateStatus, 0);
        Log.v(TAG, "Results of validating program: " + validateStatus[0]
                + "\nLog: " + glGetProgramInfoLog(i));

        return validateStatus[0] != 0;
    }

    private int buildProgram(String vertexShaderSource, String fragmentShaderSource) {
        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);

        final int program = linkProgram(vertexShader, fragmentShader);

        if (LoggerConfig.ON) {
            validateProgram(program);
        }

        return program;
    }
}
