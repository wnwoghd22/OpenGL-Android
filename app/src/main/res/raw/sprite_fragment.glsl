precision mediump float;

varying vec2 vTexCoord;

uniform vec4 uColor;
uniform sampler2D uTextureUnit;

varying vec3 normal;
varying vec3 directionalLight;

void main()
{
    float diffuse = 0.7;
    float directional = dot(normal, directionalLight) * (1.0 - diffuse);
    float result = directional + diffuse;

    gl_FragColor = texture2D(uTextureUnit, vTexCoord) * uColor * result;
}