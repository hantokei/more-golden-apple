#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D DiffuseDepthSampler;
in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 color = texture(DiffuseSampler, texCoord);
    float depth = texture(DiffuseDepthSampler, texCoord).r;
    if (depth >= 0.999999) {
        fragColor = color;
    } else {
        float gray = dot(color.rgb, vec3(0.2126, 0.7152, 0.0722));
        fragColor = vec4(vec3(gray), color.a);
    }
}