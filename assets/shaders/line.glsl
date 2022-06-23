#type vertex
#version 330 core
layout (location = 0) in vec3 aPos;


uniform mat4 camMatrix;
uniform mat4 modelMatrix;

void main()
{
    gl_Position = camMatrix * modelMatrix * vec4(aPos, 1.0);
}

#type fragment
#version 330 core


out vec4 fragColor;

void main()
{
    fragColor = vec4(1, 1, 1, 1);
}