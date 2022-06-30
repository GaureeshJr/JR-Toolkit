#type vertex
#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 aTexCoords;

out vec3 fragPos;
out vec2 fTexCoords;

void main()
{
    fragPos = aPos;
    fTexCoords = aTexCoords;
    gl_Position = vec4(aPos, 1.0);
}

#type fragment
#version 330 core

in vec3 fragPos;
in vec2 fTexCoords;

uniform sampler2D quadTex;

out vec4 FragColor;

void main()
{
    float r = texture(quadTex, vec2(fTexCoords.x + fragPos.x * 0.004, fTexCoords.y - fragPos.y * 0.004)).r;
    float b = texture(quadTex, vec2(fTexCoords.x - fragPos.x * 0.004, fTexCoords.y + fragPos.y * 0.004)).b;
    float g = texture(quadTex, vec2(fTexCoords.x, fTexCoords.y)).g;

    FragColor = vec4(r, g, b, 1.0);
}
