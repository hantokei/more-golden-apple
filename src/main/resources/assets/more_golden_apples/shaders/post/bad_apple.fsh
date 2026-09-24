#version 330

uniform sampler2D InSampler;
uniform sampler2D DepthSampler;

layout(std140) uniform BadAppleConfig {
    float Progress;
};

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 color = texture(InSampler, texCoord);
    float depth = texture(DepthSampler, texCoord).r;
    float brightness = dot(color.rgb, vec3(0.2126, 0.7152, 0.0722));
    vec3 filtered = vec3(depth >= 0.999999 ? 1.0 : step(0.25, brightness));
    fragColor = vec4(mix(color.rgb, filtered, clamp(Progress, 0.0, 1.0)), color.a);
}
