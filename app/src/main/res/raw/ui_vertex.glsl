uniform mat4 uMat;

attribute vec4 aPos;
attribute vec2 aTexCoord;

varying vec2 vTexCoord;

void main()
{
    vTexCoord = vec2(aTexCoord.x, 1.0 - aTexCoord.y);
    gl_Position = uMat * aPos;
}
