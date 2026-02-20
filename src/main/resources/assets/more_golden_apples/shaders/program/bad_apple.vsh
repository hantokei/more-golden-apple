#version 150

in vec4 Position;
out vec2 texCoord;

void main() {
    float x = (Position.x - 0.5) * 2.0;
    float y = (Position.y - 0.5) * 2.0;
    gl_Position = vec4(x, y, 0.0, 1.0);
    texCoord = vec2(Position.x, Position.y);
}