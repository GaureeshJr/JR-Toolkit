#type vertex
#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aNormals;
layout (location = 2) in vec2 aTexCoords;

out vec3 Normal;
out vec2 TexCoords;
out vec3 FragPos;


uniform mat4 camMatrix;
uniform mat4 transform;

void main()
{
    TexCoords = aTexCoords;
    FragPos = vec3(transform * vec4(aPos, 1.0));
    Normal = mat3(transpose(inverse(transform))) * aNormals;
    gl_Position = camMatrix * transform * vec4(aPos, 1.0);
}

#type fragment
#version 330 core

in vec3 Normal;
in vec2 TexCoords;
in vec3 FragPos;


uniform sampler2D albedo;
uniform vec3 cameraPos;
uniform vec3 fogColor;
uniform float fogThickness;

out vec4 fragColor;

void main()
{
    vec3 lightDir = normalize(vec3(0, 1, 1));

    float diffuse = max((dot(normalize(Normal), lightDir)), 0.2f);


    vec3 Color = (diffuse * vec3(1, 1, 1f));
    fragColor = mix(
                vec4(Color, 1.0) + vec4(fogColor, 1.0) * 0.1 * mix(vec4(1, 1, 1, 1), texture(albedo, TexCoords), 0.1f),

                vec4(fogColor.xyz, 1.0),

                min(distance(cameraPos, FragPos) * fogThickness, 1)
    );
}