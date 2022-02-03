uniform mat4 uMat;
uniform mat4 uModel;
uniform vec3 uDirectionalLight;

attribute vec4 aPos;
attribute vec2 aTexCoord;
attribute vec3 aNormal;

varying vec2 vTexCoord;

varying vec3 normal;
varying vec3 directionalLight;

void main()
{
    vTexCoord = aTexCoord;
    gl_Position = uMat * aPos;

    normal = (uModel * vec4(aNormal, 1.0)).xyz;
    directionalLight = uDirectionalLight;
}
