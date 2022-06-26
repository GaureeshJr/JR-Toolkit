#type vertex
#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aNormals;

out vec3 Normal;

uniform mat4 camMatrix;
uniform mat4 modelMatrix;

void main()
{
    Normal = mat3(transpose(inverse(modelMatrix))) * aNormals;
    gl_Position = camMatrix * modelMatrix * vec4(aPos, 1.0);
}

#type fragment
#version 330 core

in vec3 Normal;

uniform vec3 lightDirection;

out vec4 fragColor;

void main()
{
    float diffuse = dot(Normal, normalize(lightDirection));

    vec3 Color = diffuse * vec3(1, 1, 1);

    fragColor = vec4(Color, 1.0);
}