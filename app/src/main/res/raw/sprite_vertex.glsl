uniform mat4 uMat;

attribute vec4 aPos;
attribute vec2 aTexCoord;
attribute vec3 aNormal;

varying vec2 vTexCoord;

void main()
{
    vTexCoord = aTexCoord;
    gl_Position = uMat * aPos;
}
