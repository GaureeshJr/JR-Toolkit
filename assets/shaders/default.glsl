#type vertex
#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aCol;

out vec3 vertCol;

uniform mat4 camMatrix;
uniform mat4 modelMatrix;

void main()
{
    vertCol = aCol;
    gl_Position = camMatrix * modelMatrix * vec4(aPos, 1.0);
}

#type fragment
#version 330 core

in vec3 vertCol;

out vec4 fragColor;

void main()
{
    fragColor = vec4(vertCol, 1.0);
}