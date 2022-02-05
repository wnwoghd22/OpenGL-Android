precision mediump float;

varying vec2 vTexCoord;

uniform vec4 uColor;
uniform sampler2D uTextureUnit;

void main()
{
    gl_FragColor = texture2D(uTextureUnit, vTexCoord) * uColor;
}