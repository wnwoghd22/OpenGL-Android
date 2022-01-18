uniform mat4 uMat;

attribute vec4 aPos;
attribute vec4 aTexCoord;

varying vec4 vTexCoord;

void main()
{
    vTexCoord = aTexCoord;
    gl_Position = uMat * aPos;

    //gl_Position = aPos;
}
