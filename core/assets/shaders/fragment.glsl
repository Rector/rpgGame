#version 120

varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform float time;
uniform float px;
uniform float py;

void main() {
  gl_FragColor = texture2D(u_texture, v_texCoords);
//  if(v_texCoords.y < 0.2) {
//     vec2 point = v_texCoords;
//     point.y = 0.2 + (0.2 - point.y);
//     point.x += sin(time * 1.0 + point.y * 25.0) * 0.02;
//     gl_FragColor = texture2D(u_texture, point);
//     gl_FragColor.b += 0.4;
//  }
//  gl_FragColor.r += v_texCoords.x / 2.0;
  float xx = px + v_texCoords.x;
  float yy = py + v_texCoords.y;
//  gl_FragColor.rgb += 0.06 * sin(v_texCoords.x * 3 + time / 2 + v_texCoords.y * 8) + 0.06 * sin(-v_texCoords.x * 12 - time / 2 + v_texCoords.y * 7);
   gl_FragColor.rgb += 0.08 * sin(xx * 3 + time / 2 + yy * 2) + 0.08 * sin(-xx * 4 - time / 2 + yy * 5);

}
