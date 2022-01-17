//uniform mat4 projection;
//niform mat4 view;
//uniform mat4 model;

attribute vec4 aPos;
attribute vec4 aTexCoord;

varying vec4 vTexCoord;

void main()
{
    vTexCoord = aTexCoord;
    gl_Position = aPos;
}
