uniform mat4 g_WorldViewProjectionMatrix;

attribute vec4 inPosition;
attribute vec2 inTexCoord;

varying vec2 texCoord;
varying vec2 vPos;
void main(void)
{
    vec2 Pos = sign(gl_Vertex.xy);
    //gl_Position = vec4(Pos.xy, 0.0, 1.0);
    gl_Position = inPosition * 2.0 - 1.0; //vec4(pos, 0.0, 1.0);
    //texCoord.xy = Pos 
    texCoord = inTexCoord;
    vPos = Pos * 2 - 1;    
}