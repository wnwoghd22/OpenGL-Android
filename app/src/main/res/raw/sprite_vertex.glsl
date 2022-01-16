//uniform mat4 projection;
//niform mat4 view;
//uniform mat4 model;

attribute vec4 aPos;
//attribute vec4 aColor;

//varying vec4 vColor;

void main()
{
    //vColor = aColor;
    gl_Position = aPos;
}
