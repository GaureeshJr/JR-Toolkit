#type vertex
#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aNormals;

out vec3 Normal;
out vec3 FragPos;


uniform mat4 camMatrix;
uniform mat4 modelMatrix;

void main()
{
    FragPos = vec3(modelMatrix * vec4(aPos, 1.0));
    Normal = mat3(transpose(inverse(modelMatrix))) * aNormals;
    gl_Position = camMatrix * modelMatrix * vec4(aPos, 1.0);
}

#type fragment
#version 330 core

in vec3 Normal;
in vec3 FragPos;


uniform vec3 PointLight;

out vec4 fragColor;

void main()
{
    vec3 lightDir = normalize(PointLight - FragPos);

    float diffuse = max((dot(normalize(Normal), lightDir)/(distance(PointLight, FragPos) * 2)) - 0.2, 0);

    vec3 Color = diffuse * vec3(1, 1, 1);

    fragColor = vec4(Color, 1.0);
}