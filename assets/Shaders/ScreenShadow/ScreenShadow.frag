uniform sampler2D m_Texture;
uniform sampler2D m_NoiseTile;
uniform sampler2D m_Map;
uniform vec4 m_MapPos;
uniform float g_Time;
varying vec2 texCoord;

void main(void)
{
    vec4 image = texture2D(m_Texture, texCoord);
    vec2 mapCoord= vec2(m_MapPos.x + texCoord.x * m_MapPos.z,m_MapPos.y + texCoord.y * m_MapPos.w);
    vec4 mapV = texture2D(m_Map,mapCoord);
//    vec2 resolution = vec2(120,120);
    gl_FragColor = vec4(image.xyz * (0.1+mapV.r * 0.9),1);

}