shader_type canvas_item;
render_mode unshaded;
uniform sampler2D rom_palette : filter_nearest, repeat_disable;

void fragment() {
	vec4 indexed = texture(TEXTURE, UV);
	float palette_index = floor(indexed.r * 255.0 + 0.5);
	vec4 ink = texture(rom_palette, vec2((palette_index + 0.5) / 256.0, 0.5));
	COLOR = vec4(ink.rgb, indexed.a);
}
