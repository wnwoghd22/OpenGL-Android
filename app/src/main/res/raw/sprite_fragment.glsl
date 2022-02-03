precision mediump float;

varying vec2 vTexCoord;

uniform vec4 uColor;
uniform sampler2D uTextureUnit;

varying vec3 normal;
varying vec3 directionalLight;

void main()
{
    float diffuse = max(dot(normal, directionalLight), 0.0);

    gl_FragColor = texture2D(uTextureUnit, vTexCoord) * uColor * diffuse;
    //gl_FragColor = uColor;
}