#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D DiffuseDepthSampler;
uniform float Progress;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 color = texture(DiffuseSampler, texCoord);
    float depth = texture(DiffuseDepthSampler, texCoord).r;

    vec4 filterColor;
    if (depth >= 0.999999) {
        // 天空留白
        filterColor = vec4(1.0, 1.0, 1.0, 1.0);
    } else {
        // 计算亮度
        float brightness = dot(color.rgb, vec3(0.2126, 0.7152, 0.0722));

        // --- 纯粹二值化逻辑 ---
        // 删除了所有 edge 采样和判定
        // 亮度低于 0.25 的变黑（实现你想要的实体尽量黑，地面尽量白）
        if (brightness < 0.25) {
            filterColor = vec4(0.0, 0.0, 0.0, 1.0);
        } else {
            filterColor = vec4(1.0, 1.0, 1.0, 1.0);
        }
    }

    // 混合逻辑
    float p = (Progress <= 0.001 && Progress >= -0.001) ? 1.0 : Progress;
    fragColor = vec4(mix(color.rgb, filterColor.rgb, p), color.a);
}