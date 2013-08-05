uniform float m_DistortionFreq;
uniform float m_DistortionScale;
uniform float m_DistortionRoll;
uniform float m_Interference;
uniform float m_FrameLimit;
uniform float m_FrameShape;
uniform float m_FrameSharpness;
uniform float m_Brightness;

uniform sampler2D m_Texture;
uniform sampler3D m_Noise;
uniform sampler3D m_Rand;

uniform float g_Time;
varying vec2 texCoord;
varying vec2 vPos;

void main(void)
{

    float time_0_X = g_Time;
    float sin_time_0_X = sin(g_Time);
   // Define a frame shape
   float f = (1.0 - vPos.x * vPos.x) * (1.0 - vPos.y * vPos.y);
   float frame = clamp(m_FrameSharpness * (pow(f, m_FrameShape) - m_FrameLimit), 0.0, 1.0);
   
   // Interference ... just a texture filled with rand()
   float rand = float(texture3D(m_Rand, vec3(1.5 * vPos, time_0_X))) - 0.2;
   
   // Some signed noise for the distortion effect
   float noisy = float(texture3D(m_Noise, vec3(0.0, 0.5 * vPos.y, 0.1 * time_0_X))) - 0.5;
   
   // Repeat a 1 - x^2 (0 < x < 1) curve and roll it with sinus.
   float dst = fract(vPos.y * m_DistortionFreq + m_DistortionRoll * sin_time_0_X);
   dst *= (1.0 - dst);
   
   // Make sure distortion is highest in the center of the image
   dst /= 1.0 + m_DistortionScale * abs(vPos.y);
   
   // ... and finally distort
   texCoord.x += m_DistortionScale * noisy * dst;
   vec4 image = texture2D(m_Texture, texCoord);
      // Combine frame, distorted image and m_Interference


      // Zoom , tint , interlace

    vec2 resolution = vec2(120,120);
    vec2 q = texCoord.xy / resolution.xy;
    vec2 uv = 0.5 + (q-0.5)*(0.9 + 0.1*sin(0.2*g_Time));
/*
    vec3 oricol = texture2D(m_Texture,vec2(q.x,1.0-q.y)).xyz;
    vec3 col;

    col.r = texture2D(m_Texture,vec2(uv.x+0.003,-uv.y)).x;
    col.g = texture2D(m_Texture,vec2(uv.x+0.000,-uv.y)).y;
    col.b = texture2D(m_Texture,vec2(uv.x-0.003,-uv.y)).z;

    col = clamp(col*0.5+0.5*col*col*1.2,0.0,1.0);

    col *= 0.5 + 0.5*16.0*uv.x*uv.y*(1.0-uv.x)*(1.0-uv.y);

    col *= vec3(0.8,1.0,0.7);

    col *= 0.9+0.1*sin(10.0*g_Time+uv.y*1000.0);

    col *= 0.97+0.03*sin(110.0*g_Time);

    float comp = smoothstep( 0.2, 0.7, sin(g_Time) );
    col = mix( col, oricol, clamp(-2.0+2.0*q.x+3.0*comp,0.0,1.0) );

   gl_FragColor = frame * (m_Interference * rand + vec4(col,1.0));
*/
    vec3 col = image.xyz;
    float p=0.0001;
    float y1=texCoord.y;
    y1=y1-p*floor(y1/p);

    if (y1<p*0.5){
        col *= vec3(0.8,1.2,0.7);
    } else {
        float v = m_Brightness ;
        col *= vec3(v,v,v);
    }
    col *= 0.8 * m_Brightness+0.2*sin(10.0*g_Time+uv.y*1000.0);
    gl_FragColor = frame * (m_Interference * rand + vec4(col,1.0));

}