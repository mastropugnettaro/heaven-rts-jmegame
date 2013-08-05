uniform mat4 g_WorldViewProjectionMatrix;

attribute vec4 inPosition;
attribute vec2 inTexCoord;

varying vec2 texCoord;
void main(void)
{
    gl_Position = inPosition * 2.0 - 1.0; 
    //texCoord.xy = Pos 
    texCoord = inTexCoord;
}